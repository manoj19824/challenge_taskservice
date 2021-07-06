package com.celonis.challenge.services;

import com.celonis.challenge.exceptions.InternalException;
import com.celonis.challenge.exceptions.NotFoundException;
import com.celonis.challenge.model.ProjectGenerationTask;
import com.celonis.challenge.model.ProjectGenerationTaskRepository;
import com.celonis.challenge.model.TaskProgressResult;
import com.celonis.challenge.model.TaskStatus;
import com.celonis.challenge.services.async.AsyncTaskExecutor;
import com.celonis.challenge.util.TaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskContainerService taskContainerService;
    private final FileService fileService;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final ProjectGenerationTaskRepository projectGenerationTaskRepository;

    private final ReentrantLock lock = new ReentrantLock();

    public TaskService(TaskContainerService taskContainerService, FileService fileService, AsyncTaskExecutor asyncTaskExecutor, ProjectGenerationTaskRepository projectGenerationTaskRepository) {
        this.taskContainerService = taskContainerService;
        this.fileService = fileService;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
    }


    public List<ProjectGenerationTask> listTasks() {
        return projectGenerationTaskRepository.findAll();
    }

    public ProjectGenerationTask createTask(ProjectGenerationTask projectGenerationTask) {
        projectGenerationTask.setId(null);
        projectGenerationTask.setStatus(TaskStatus.CREATED);
        projectGenerationTask.setCreationDate(new Date());
        return projectGenerationTaskRepository.save(projectGenerationTask);
    }

    public ProjectGenerationTask getTask(String taskId) {
        return get(taskId);
    }

    public ProjectGenerationTask update(String taskId, ProjectGenerationTask projectGenerationTask) {
        ProjectGenerationTask existing = get(taskId);
        existing.setCreationDate(projectGenerationTask.getCreationDate());
        existing.setName(projectGenerationTask.getName());
        return projectGenerationTaskRepository.save(existing);
    }

    public void delete(String taskId) {
        projectGenerationTaskRepository.deleteById(taskId);
    }


    @Transactional
    public void deleteBasedOnIds(List<String> taskIds) {
        projectGenerationTaskRepository.deleteTasksWithIds(taskIds);
    }


    private ProjectGenerationTask get(String taskId) {
        Optional<ProjectGenerationTask> projectGenerationTask = projectGenerationTaskRepository.findById(taskId);
        return projectGenerationTask.orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void cancelTask(String taskId) {
        Map<String, Future<Boolean>> taskContainer = taskContainerService.getTaskContainer();
        try {
            lock.lock();
            if(taskContainer.containsKey(taskId)){
                logger.info("cancel request started for the task id {} ",taskId);
                // cancel the request, send the notify signal to kill the running thread
                taskContainer.get(taskId).cancel(true);
            }
        }finally {
            lock.unlock();
        }

    }
    public TaskProgressResult getProgress(String taskId) {
        logger.info("Getting progress of the running task");
        Map<String, TaskProgressResult> taskProgressResultMap = taskContainerService.getTaskProgressContainer();
        logger.info("progress container is {}",taskProgressResultMap);
        return taskProgressResultMap.getOrDefault(taskId,new TaskProgressResult(taskId,"No progress found for the given task"));
    }

    public void executeTask(String taskId, String noOfSeconds) {
        logger.info("Current thread is {}",Thread.currentThread().getName());
        ProjectGenerationTask task = getTask(taskId);
        int end = Integer.parseInt(noOfSeconds);
        AtomicBoolean isCanceled = new AtomicBoolean(false);
        if(task != null){
            taskContainerService.getTaskContainer().computeIfAbsent(taskId,k-> asyncTaskExecutor.execute(() -> {
                logger.info("Task execution started for the task id for seconds {} - {}",taskId,noOfSeconds);
                int start = 0;
                URL url = Thread.currentThread().getContextClassLoader().getResource("challenge.zip");
                logger.info("Url is {}",url);
                if (url == null) {
                    throw new InternalException("Zip file not found");
                }
                Map<String, TaskProgressResult> taskProgressContainer = taskContainerService.getTaskProgressContainer();
                try {
                    taskProgressContainer.put(taskId,new TaskProgressResult(taskId,TaskStatus.EXECUTING, TaskUtil.getCompletedPercentage(start,end)));
                    while(start < end){
                        // sleeping for 1 seconds and incrementing count
                        logger.info("Inside the loop for {}",taskId);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e) {
                            logger.error("interrupted and thread killed");
                            isCanceled.set(true);
                            break;
                        }
                        start ++;
                        taskProgressContainer.put(taskId,new TaskProgressResult(taskId,TaskStatus.EXECUTING, TaskUtil.getCompletedPercentage(start,end)));
                    }
                    if(isCanceled.get()){
                        taskProgressContainer.put(taskId,new TaskProgressResult(taskId,TaskStatus.CANCELED, TaskUtil.getCompletedPercentage(start,end)));
                        updateTaskStatus(TaskStatus.CANCELED,taskId);
                        logger.info("removing the task from the container state {} ",taskId);
                        // remove the state from the taskContainer
                        taskContainerService.getTaskContainer().remove(taskId);
                        return;
                    }
                    logger.info("Storing the result now.....");
                    fileService.storeResult(taskId,url,task);
                    taskProgressContainer.put(taskId,new TaskProgressResult(taskId,TaskStatus.EXECUTED, TaskUtil.getCompletedPercentage(start,end)));
                } catch (Exception ex) {
                    logger.error("Exception occurred during execution");
                    updateTaskStatus(TaskStatus.FAILED,taskId);
                    taskProgressContainer.put(taskId,new TaskProgressResult(taskId,TaskStatus.FAILED, TaskUtil.getCompletedPercentage(start,end)));
                }
            }));
        }
    }
    private void updateTaskStatus(TaskStatus status,String taskId){
        ProjectGenerationTask projectGenerationTask = getTask(taskId);
        projectGenerationTask.setStatus(status);
        projectGenerationTaskRepository.save(projectGenerationTask);
    }

    public ResponseEntity<FileSystemResource> getTaskResult(String taskId) {
        ProjectGenerationTask task = getTask(taskId);
        return fileService.getTaskResult(task.getStorageLocation());
    }
}
