package com.celonis.challenge.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ProjectGenerationTaskRepositoryTest {

    @Autowired
    private ProjectGenerationTaskRepository projectGenerationTaskRepository;

    @Test
    public void testDeleteTaskWithIds(){
        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setName("Task1");
        ProjectGenerationTask savedTask = projectGenerationTaskRepository.save(task);
        List<String> idsToBeDelete = new ArrayList<String>();
        idsToBeDelete.add(savedTask.getId());
        assertFalse(projectGenerationTaskRepository.findAll().isEmpty());
        projectGenerationTaskRepository.deleteTasksWithIds(idsToBeDelete);
        assertTrue(projectGenerationTaskRepository.findAll().isEmpty());
    }
}
