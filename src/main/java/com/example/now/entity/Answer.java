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
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "ANSWER")
public class Answer implements Serializable {
    public Answer() {
    }

    public Answer(int workerId, int taskId, Timestamp answer_time, String answer) {
        this.workerId = workerId;
        this.taskId = taskId;
        this.answer_time = answer_time;
        this.answer = answer;
    }

    public void setAll(int workerId, int taskId, Timestamp answer_time, String answer) {
        this.workerId = workerId;
        this.taskId = taskId;
        this.answer_time = answer_time;
        this.answer = answer;
    }

    @Id
    @GeneratedValue
    @Column(name = "ANSWER_ID")
    private int id;

    @Column(nullable = false, name = "worker_id")
    private int workerId;

    @Column(nullable = false, name = "task_id")
    private int taskId;

    @Column(name = "answer_time")
    private Timestamp answer_time;

    @Column (name="ANSWER")
    private String answer;
}
