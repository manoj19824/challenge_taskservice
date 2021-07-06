package com.celonis.challenge.controller;

import com.celonis.challenge.model.ProjectGenerationTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAndListTask() throws Exception {
        String endPoint =  new URL("http://localhost:" + port + "/api/tasks/").toString();
        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setName("Task-1");
        HttpEntity<ProjectGenerationTask> request = new HttpEntity<>(task);
        String postTask = restTemplate.postForObject(endPoint, request, String.class);
        System.out.println(postTask);
        ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
