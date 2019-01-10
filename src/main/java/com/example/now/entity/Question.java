package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int id;
    @Column
    private String content;
    @Column
    private int resource_loading;
    @Column
    private int type;
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "compulsory")
    private int compulsory;

    public void setId(int id) {
        this.id = id;
    }

    public void setResource_loading(int resource_loading) {
        this.resource_loading = resource_loading;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCompulsory() {
        return compulsory;
    }

    public void setCompulsory(int compulsory) {
        this.compulsory = compulsory;
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

    public Integer getResource_loading() {
        return resource_loading;
    }

    public void setResource_loading(Integer resource_loading) {
        this.resource_loading = resource_loading;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Question(String content, Integer resource_loading, Integer type, Integer task_id,int compulsory) {
        this.content = content;
        this.resource_loading = resource_loading;
        this.type = type;
        this.taskId = task_id;
        this.compulsory=compulsory;
    }
}