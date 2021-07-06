package com.celonis.challenge.model;

public class TaskProgressResult {

    private String taskId;
    private String message;
    private TaskStatus status;
    private String percentageCompleted;

    public TaskProgressResult(String taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }

    public TaskProgressResult(String taskId, TaskStatus status, String percentageCompleted) {
        this.taskId = taskId;
        this.status = status;
        this.percentageCompleted = percentageCompleted;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(String percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
