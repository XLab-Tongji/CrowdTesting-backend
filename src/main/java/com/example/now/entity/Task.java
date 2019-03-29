package com.example.now.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
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
    private Float reward;
    @Column
    private String status;
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
    @Column(name = "resource_link")
    private String resource_link;

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
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

    public Float getReward() {
        return reward;
    }

    public void setReward(Float reward) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequesterid() {
        return requesterid;
    }

    public void setRequesterid(Integer requesterid) {
        this.requesterid = requesterid;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Float getTime_limitation() {
        return time_limitation;
    }

    public void setTime_limitation(Float time_limitation) {
        this.time_limitation = time_limitation;
    }

    public Float getPay_time() {
        return pay_time;
    }

    public void setPay_time(Float pay_time) {
        this.pay_time = pay_time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Task() {

    }

    public Task(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age) {
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
    }

    public void setAll(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age) {
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
    }

    public String getResource_link() {
        return resource_link;
    }

    public void setResource_link(String resource_link) {
        this.resource_link = resource_link;
    }
}
