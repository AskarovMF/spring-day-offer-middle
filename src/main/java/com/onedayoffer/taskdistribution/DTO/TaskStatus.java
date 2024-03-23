package com.onedayoffer.taskdistribution.DTO;

public enum TaskStatus {
    APPOINTED("1"),
    IN_PROGRESS("2"),
    DONE("13");

    String id;

    TaskStatus(String id) {
        this.id = id;
    }
}
