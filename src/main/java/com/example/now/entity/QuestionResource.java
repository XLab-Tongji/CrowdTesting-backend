package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "question_resource")
public class QuestionResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "resource_id")
    private Integer resourceId;

    public Integer getId() {
        return id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public QuestionResource(){

    }

    public QuestionResource(Integer questionId, Integer resourceId) {
        this.questionId = questionId;
        this.resourceId = resourceId;
    }
}
