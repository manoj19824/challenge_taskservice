package com.celonis.challenge.services;

import com.celonis.challenge.util.DateUtil;
import com.celonis.challenge.model.ProjectGenerationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollingService {

    private final TaskService taskService;
    private final Logger logger = LoggerFactory.getLogger(PollingService.class);

    public PollingService(TaskService taskService) {
        this.taskService = taskService;
    }

    // runs every 1 minutes and cleanup the tasks which are older than 20 days
    // we can also configure this in the application properties and configurable.
    @Scheduled(fixedDelay = 60_000)
    public void cleanUpOldTasks(){
        logger.info("clean up for old tasks started");
        List<ProjectGenerationTask> taskList = taskService.listTasks();
        List<String> taskIds = taskList.stream()
                .filter(it-> DateUtil.isOlderThan(20,it.getCreationDate()))
                .map(it-> it.getId())
                .collect(Collectors.toList());
        taskService.deleteBasedOnIds(taskIds);
        logger.info("{} task-ids deleted successfully",taskIds);
    }
}
