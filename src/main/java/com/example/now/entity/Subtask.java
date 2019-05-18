package com.example.now.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;


/**
 * Subtask entity class
 *
 * @author jjc
 * @date 2019/05/17
 */
@Entity
@Data
@Table(name="subtask")
public class Subtask implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "subtask_id")
    private int id;

    @Column(nullable = false, name = "begin")
    private int begin;

    @Column(nullable = false, name = "end")
    private int end;

    @Column(nullable = false, name = "created_time")
    private Timestamp createdTime;

    @Column(nullable = false, name = "deadline")
    private Timestamp deadline;

    @Column(nullable = false, name = "updated_time")
    private Timestamp updatedTime;

    /**
     * 判断子任务是否完成，0 为未完成，1 为已完成
     */
    @Column(nullable = false, name = "isFinished")
    private int isFinished;

    /**
     * 子任务的类型，0 为普通任务，1 为审核任务
     */
    @Column(nullable = false, name = "type")
    private int type;

    @Column(nullable = false, name = "worker_id")
    private int workerId;

    @Column(nullable = false, name = "task_id")
    private int taskId;

    /**
     * 标志这个子任务属于第几份答案，便于答案合并,从 0 开始
     */
    @Column(nullable = false, name = "number_of_task")
    private int numberOfTask;

    /**
     * 标志这个子任务应从哪道题开始做，now_begin=end+1，end 为已回答的最后一道题数
     */
    @Column(nullable = false, name = "now_begin")
    private int nowBegin;

    @Transient
    private String username;

    @Transient
    private String title;

    @Transient
    private String taskType;

    public Subtask(){

    }

    public Subtask(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, int nowBegin) {
        this.begin = begin;
        this.end = end;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.updatedTime = updatedTime;
        this.isFinished = isFinished;
        this.type = type;
        this.workerId = workerId;
        this.taskId = taskId;
        this.numberOfTask = numberOfTask;
        this.nowBegin = nowBegin;
    }

    public void setAll(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, int nowBegin) {
        this.begin = begin;
        this.end = end;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.updatedTime = updatedTime;
        this.isFinished = isFinished;
        this.type = type;
        this.workerId = workerId;
        this.taskId = taskId;
        this.numberOfTask = numberOfTask;
        this.nowBegin = nowBegin;
    }
}