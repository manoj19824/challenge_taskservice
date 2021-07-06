package com.celonis.challenge.model;

public enum TaskStatus {

    CREATED(0, "Task newly created"),
    EXECUTING(-1, "Task newly created"),
    EXECUTED(1, "Task executed successfully"),
    CANCELED(2, "Task execution canceled"),
    FAILED(-2, "Task execution failed");

    private int state;
    private String stateInfo;

    TaskStatus(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
