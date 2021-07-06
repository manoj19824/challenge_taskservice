package com.celonis.challenge.services;

import com.celonis.challenge.model.TaskProgressResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component
public class TaskContainerService {

    private Map<String, Future<Boolean>> taskContainer = new ConcurrentHashMap<>();

    private Map<String, TaskProgressResult> taskProgressContainer = new ConcurrentHashMap<>();

    public Map<String, Future<Boolean>> getTaskContainer() {
        return taskContainer;
    }

    public Map<String, TaskProgressResult> getTaskProgressContainer() {
        return taskProgressContainer;
    }
}
