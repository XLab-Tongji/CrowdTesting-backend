package com.example.now.entity;
import javax.persistence.*;
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer reward;
    @Column
    private String status;
    @Column(name = "requester_id")
    private Integer requesterid;
    @Column
    private String type;
    @Column
    private String restrictions;
    @Column
    private String requests;
    public Integer getTask_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRequester_id() {
        return requesterid;
    }

    public void setRequester_id(Integer requester_id) {
        this.requesterid = requester_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public Task(){

    }

    public Task(String name,String description,int requester_id){
        this.name=name;
        this.description=description;
        this.requesterid=requester_id;
    }
}
