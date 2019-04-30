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
@Table(name="subtask")
public class SubTask implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "subtask_id")
    private int id;

    @Column(nullable = false, name = "begin")
    private int begin;

    @Column(nullable = false, name = "end")
    private int end;

    @Column(nullable = false, name = "created_time")
    private Timestamp created_time;

    @Column(nullable = false, name = "deadline")
    private Timestamp deadline;

    @Column(nullable = false, name = "updated_time")
    private Timestamp updated_time;

    @Column(nullable = false, name = "is_finished")
    private int is_finished;

    @Column(nullable = false, name = "type")
    private int type;

    @Column(nullable = false, name = "worker_id")
    private int workerId;

    @Column(nullable = false, name = "task_id")
    private int taskId;

    @Column(nullable = false, name = "number_of_task")
    private int number_of_task;

    public SubTask(){

    }

    public SubTask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task) {
        this.begin = begin;
        this.end = end;
        this.created_time = created_time;
        this.deadline = deadline;
        this.updated_time = updated_time;
        this.is_finished = is_finished;
        this.type = type;
        this.workerId = workerId;
        this.taskId = taskId;
        this.number_of_task = number_of_task;
    }

    public void setAll(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task) {
        this.begin = begin;
        this.end = end;
        this.created_time = created_time;
        this.deadline = deadline;
        this.updated_time = updated_time;
        this.is_finished = is_finished;
        this.type = type;
        this.workerId = workerId;
        this.taskId = taskId;
        this.number_of_task = number_of_task;
    }
}
