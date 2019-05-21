package com.example.now.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Transaction information entity class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Entity
@Data
@Table(name = "transaction_information")
public class TransactionInformation {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(nullable = false, name = "requester_id")
    private int requesterId;

    @Column(nullable = false, name = "worker_id")
    private int workerId;

    @Column(nullable = false, name = "task_id")
    private int taskId;

    @Column(name = "time")
    private Timestamp time;

    @Column (name="value")
    private float value;

    public TransactionInformation() {
    }

    public TransactionInformation(int requesterId, int workerId, int taskId, Timestamp time, float value) {
        this.requesterId = requesterId;
        this.workerId = workerId;
        this.taskId = taskId;
        this.time = time;
        this.value = value;
    }

    public void setAll(int requesterId, int workerId, int taskId, Timestamp time, float value) {
        this.requesterId = requesterId;
        this.workerId = workerId;
        this.taskId = taskId;
        this.time = time;
        this.value = value;
    }
}
