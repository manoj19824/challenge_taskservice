package com.celonis.challenge.service;

import com.celonis.challenge.exceptions.NotFoundException;
import com.celonis.challenge.model.ProjectGenerationTask;
import com.celonis.challenge.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TaskServiceTest
{
    @Autowired
    private TaskService taskService;

    @Test
    void testCreateTask() {
        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setName("Task-1");
        assertNotNull(taskService.createTask(task));
        assertFalse(taskService.listTasks().isEmpty());
    }

    @Test
    void testGetProgress() {
        assertEquals("No progress found for the given task",taskService.getProgress("1224xayyy").getMessage());
    }

    @Test
    void testExecuteTask() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.executeTask("1224xayyy","60");
        });
    }

}
