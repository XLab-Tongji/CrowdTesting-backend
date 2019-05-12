package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONObject;
import org.json.JSONString;

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

    @Column(nullable = false, name = "task_id")
    private int taskId;

    @Column(name = "time")
    private Timestamp time;

    @Column (name="value")
    private float value;

    public TransactionInformation() {
    }

    public TransactionInformation(int requesterId, int taskId, Timestamp time, float value) {
        this.requesterId = requesterId;
        this.taskId = taskId;
        this.time = time;
        this.value = value;
    }

    public void setAll(int requesterId, int taskId, Timestamp time, float value) {
        this.requesterId = requesterId;
        this.taskId = taskId;
        this.time = time;
        this.value = value;
    }
}
