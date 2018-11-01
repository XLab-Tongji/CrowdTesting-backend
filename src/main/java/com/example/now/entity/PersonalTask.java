package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSONAL_TASK")
@IdClass(PersonalTask.class)
public class PersonalTask extends PersonalTaskKey {
    @EmbeddedId
    private PersonalTaskKey id;

    public PersonalTask() {

    }

    public PersonalTask(int workerId,int taskId) {
        this.id.workerId = workerId;
        this.id.taskId = taskId;
    }
}
