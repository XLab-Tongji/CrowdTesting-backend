package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PersonalTaskKey implements Serializable {
    @Column(name = "WORKER_ID")
    public int workerId;

    @Column(name = "TASK_ID")
    public int taskId;
}
