package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;
    @Column
    private String content;
    @Column
    private Integer resource_loading;
    @Column
    private String type;
    @Column(name = "task_id")
    private int taskId;

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

    public Integer getResource_loading() {
        return resource_loading;
    }

    public void setResource_loading(Integer resource_loading) {
        this.resource_loading = resource_loading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTask_id() {
        return taskId;
    }

    public void setTask_id(int task_id) {
        this.taskId = task_id;
    }

    public Question(){

    }

    public Question(String content, Integer resource_loading, String type, int task_id) {
        this.content = content;
        this.resource_loading = resource_loading;
        this.type = type;
        this.taskId = task_id;
    }
}
