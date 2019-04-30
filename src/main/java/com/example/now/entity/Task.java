package com.example.now.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Data;
@Entity
@Data
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
    private Float reward;
    @Column
    private int status;
    @Column(name = "requester_id")
    private Integer requesterid;
    @Column
    private String type;
    @Column
    private String restrictions;
    @Column(name = "start_time")
    private Timestamp start_time;
    @Column(name = "end_time")
    private Timestamp end_time;
    @Column(name = "population")
    private int population;
    @Column(name = "level")
    private int level;
    @Column(name = "time_limitation")
    private Float time_limitation;
    @Column(name = "pay_time")
    private Float pay_time;
    @Column(name = "area")
    private String area;
    @Column(name = "application")
    private String usage;
    @Column(name = "min_age")
    private int min_age;
    @Column(name = "max_age")
    private int max_age;

    @Column(name = "reviewed")
    private int reviewed;

    @Column(name = "resource_link")
    private String resource_link;

    @Column(name = "rest_of_question")
    private String rest_of_question;

    @Column(name = "number_of_questions")
    private int number_of_questions;

    @Transient
    private String institution_name;

    public Task() {

    }

    public Task(String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age,int reviewed) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
        this.requesterid = requesterid;
        this.type = type;
        this.restrictions = restrictions;
        this.start_time = start_time;
        this.end_time = end_time;
        this.population = 0;
        this.level = level;
        this.time_limitation = time_limitation;
        this.pay_time = pay_time;
        this.area=area;
        this.usage=usage;
        this.min_age=min_age;
        this.max_age=max_age;
        this.reviewed=reviewed;
    }

    public void setAll(String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age,int reviewed) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
        this.requesterid = requesterid;
        this.type = type;
        this.restrictions = restrictions;
        this.start_time = start_time;
        this.end_time = end_time;
        this.level = level;
        this.time_limitation = time_limitation;
        this.pay_time = pay_time;
        this.area=area;
        this.usage=usage;
        this.min_age=min_age;
        this.max_age=max_age;
        this.reviewed=reviewed;
    }

    public String getResource_link() {
        return resource_link;
    }

    public void setResource_link(String resource_link) {
        this.resource_link = resource_link;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }
}
