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

    //答案对应的子任务的id
    @Column(name = "subtask_id")
    private Integer subtaskId;
    //答案的开始题数
    @Column (name = "begin_at")
    private Integer beginAt;
    //答案目前的末尾题数
    @Column (name = "end_at")
    private Integer endAt;
    //该子任务应做题数
    @Column (name="number")
    private Integer number;

    public String getAnswer() {
        return answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(Integer subtaskId) {
        this.subtaskId = subtaskId;
    }

    public Integer getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Integer beginAt) {
        this.beginAt = beginAt;
    }

    public Integer getEndAt() {
        return endAt;
    }

    public void setEndAt(Integer endAt) {
        this.endAt = endAt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
