package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "question_id")
    private int questionId;
    @Column(name = "open_answer_permission")
    private int openAnswerPermission;
    @Column(name = "option_number")
    private int optionNumber;

    public Integer getopenAnswerPermission () {
        return openAnswerPermission;
    }

    public void setopenAnswerPermission (Integer openAnswerPermission) {
        this.openAnswerPermission = openAnswerPermission;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Integer optionNumber) {
        this.optionNumber = optionNumber;
    }

    public int getId() {
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

    public Option(){

    }

    public Option(String content, Integer questionId, Integer openAnswerPermission , Integer optionNumber) {
        this.content = content;
        this.questionId = questionId;
        this.openAnswerPermission = openAnswerPermission ;
        this.optionNumber = optionNumber;
    }
}
