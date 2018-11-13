package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSONAL_TASK")
public class PersonalTask {
    @EmbeddedId
    private PersonalTaskKey id;

    public PersonalTask() {

    }

    public int getWorkerId(){
        return id.getWorkerId();
    }

    public int getTaskId(){
        return id.getTaskId();
    }

    public PersonalTask(int workerId, int taskId) {
        this.id = new PersonalTaskKey(workerId, taskId);
    }


    public void setId(int workerId, int taskId) {
        this.id = new PersonalTaskKey(workerId, taskId);
    }
}
