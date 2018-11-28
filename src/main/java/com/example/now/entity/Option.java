package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "option")
public class    Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer id;
    @Column
    private String content;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "open_answer_permission")
    private Integer openAnswerPermittion;
    @Column(name = "option_number")
    private Integer optionNumber;

    public Integer getOpenAnswerPermittion() {
        return openAnswerPermittion;
    }

    public void setOpenAnswerPermittion(Integer openAnswerPermittion) {
        this.openAnswerPermittion = openAnswerPermittion;
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

    public Option(String content, Integer questionId, Integer openAnswerPermittion, Integer optionNumber) {
        this.content = content;
        this.questionId = questionId;
        this.openAnswerPermittion = openAnswerPermittion;
        this.optionNumber = optionNumber;
    }
}
