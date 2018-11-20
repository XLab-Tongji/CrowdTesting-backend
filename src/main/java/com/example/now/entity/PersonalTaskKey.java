package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PersonalTaskKey implements Serializable {
    @Column(name = "WORKER_ID")
    public Integer workerId;

    @Column(name = "TASK_ID")
    public Integer taskId;

    @Override
    public String toString() {
        return "PersonalTaskKey[workerId=" + workerId.toString() + ",taskId=" + taskId.toString() + "]";
    }

    public PersonalTaskKey() {

    }

    public Integer getWorkerId() {
        return workerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public PersonalTaskKey(int workerId, int taskId) {
        this.workerId = workerId;
        this.taskId = taskId;
    }
}
