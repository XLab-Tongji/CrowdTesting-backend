package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "open_answer")
public class OpenAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "open_answer_id")
    private Integer id;
    @Column
    private String content;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "worker_id")
    private Integer workerId;
    @Column
    private Timestamp time;
    @Column(name = "option_id")
    private Integer optionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public OpenAnswer() {
    }

    public OpenAnswer(String content, Integer questionId, Integer workerId) {
        this.content = content;
        this.questionId = questionId;
        this.workerId = workerId;
        this.time = new Timestamp(new Date().getTime());
    }

    public OpenAnswer(String content, Integer questionId, Integer workerId, Integer optionId) {
        this.content = content;
        this.questionId = questionId;
        this.workerId = workerId;
        this.optionId = optionId;
        this.time = new Timestamp(new Date().getTime());
    }
}
