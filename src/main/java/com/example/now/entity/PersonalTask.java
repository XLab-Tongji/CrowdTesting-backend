package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSONAL_TASK")
public class  PersonalTask {
    @EmbeddedId
    private PersonalTaskKey id;
    @Column(name = "finished")
    private int finished;
    public PersonalTask() {

    }

    public int getWorkerId() {
        return id.getWorkerId();
    }

    public int getTaskId() {
        return id.getTaskId();
    }

    public PersonalTask(int workerId, int taskId) {
        this.finished=0;
        this.id = new PersonalTaskKey(workerId, taskId);
    }

    public PersonalTaskKey getId() {
        return id;
    }

    public void setId(PersonalTaskKey id) {
        this.id = id;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public void setId(int workerId, int taskId) {
        this.id = new PersonalTaskKey(workerId, taskId);
    }
}
