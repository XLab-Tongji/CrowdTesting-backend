package com.example.now.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Task entity class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Entity
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "task")
public class Task implements Serializable {
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
    private Integer requesterId;

    @Column
    private String type;

    @Column
    private String restrictions;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    /**
     * 需要完整答案的套数
     */
    @Column(name = "population")
    private int population;

    @Column(name = "level")
    private int level;

    @Column(name = "time_limitation")
    private Float timeLimitation;

    @Column(name = "pay_time")
    private Float payTime;

    @Column(name = "area")
    private String area;

    @Column(name = "application")
    private String usage;

    @Column(name = "min_age")
    private int minAge;

    @Column(name = "max_age")
    private int maxAge;

    @Column(name = "reviewed")
    private int reviewed;

    @Column(name = "resource_link")
    private String resourceLink;

    @Column(name = "rest_of_question")
    private String restOfQuestion;
  
    @Transient
    private String institutionName;

    /**
     * 表示该任务是否被分配完成，0 为未完成，1 为普通任务分配完成，2 为所有（普通与审核）任务分配完成
     * 默认为0
     */
    @Column(name= "is_distributed")
    private Integer isDistributed;

    /**
     * 表示该任务是否完成，-1 为过期，0 为未完成，1 为普通任务已完成，2 为所有（普通与审核）任务已完成
     * 默认为0
     */
    @Column(name = "isFinished")
    private Integer isFinished;

    /**
     * 该任务已被分发的数量
     * 默认为0
     */
    @Column(name = "distributed_number")
    private Integer distributedNumber;

    /**
     * 该任务需要分发的总数量
     * 默认为0
     */
    @Column(name = "all_number")
    private Integer allNumber;

    /**
     * 存放该任务最后整合好的答案
     * 在生成 Task 的同时生成空答案
     */
    @Column(name = "answer")
    private String answer;

    /**
     * 该任务的题目总数
     */
    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    /**
     * 已被判断正误的题数（只针对图像标记类任务），当 judgedNumber*population=allNumber 时，判断正误完成
     */
    @Column(name = "judged_number")
    private Integer judgedNumber;

    public Task() {

    }

    public Task(String name, String description, Float reward, int status, Integer requesterId, String type, String restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge, int reviewed, Integer allNumber) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
        this.requesterId = requesterId;
        this.type = type;
        this.restrictions = restrictions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.population = population;
        this.level = level;
        this.timeLimitation = timeLimitation;
        this.payTime = payTime;
        this.area = area;
        this.usage = usage;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.allNumber = allNumber;
        this.reviewed = reviewed;
    }

    public void setAll(String name, String description, Float reward, int status, Integer requesterId, String type, String restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge, int reviewed, Integer allNumber) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
        this.requesterId = requesterId;
        this.type = type;
        this.restrictions = restrictions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.population = population;
        this.level = level;
        this.timeLimitation = timeLimitation;
        this.payTime = payTime;
        this.area = area;
        this.usage = usage;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.allNumber = allNumber;
        this.reviewed = reviewed;
    }
}
