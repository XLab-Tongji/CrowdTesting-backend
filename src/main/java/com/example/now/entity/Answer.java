package com.example.now.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Answer entity class
 *
 * @author jjc
 * @date 2019/05/17
 */
@Entity
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "answer")
public class Answer implements Serializable {
    public Answer() {
    }

    public Answer(int workerId, int taskId, Timestamp answerTime, String answer) {
        this.workerId = workerId;
        this.taskId = taskId;
        this.answerTime = answerTime;
        this.answer = answer;
    }

    public void setAll(int workerId, int taskId, Timestamp answerTime, String answer) {
        this.workerId = workerId;
        this.taskId = taskId;
        this.answerTime = answerTime;
        this.answer = answer;
    }

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private int id;

    @Column(nullable = false, name = "worker_id")
    private int workerId;

    @Column(nullable = false, name = "task_id")
    private int taskId;

    @Column(name = "answer_time")
    private Timestamp answerTime;

    @Column (name="answer")
    private String answer;

    /**
     * 答案对应的子任务的id
     */
    @Column(name = "subtask_id")
    private Integer subtaskId;

    /**
     * 答案的开始题数
     */
    @Column (name = "begin_at")
    private Integer beginAt;

    /**
     * 答案目前的末尾题数
     */
    @Column (name = "end_at")
    private Integer endAt;

    /**
     * 该子任务应做题数
     */
    @Column (name="number")
    private Integer number;
}
