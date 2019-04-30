package com.example.now.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//子任务表
@Entity
@Table(name = "subtask")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt","updatedAt"},allowGetters=true)
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "subtask_id")
    private Integer subtaskId;
    @Column(nullable = false,name = "task_id")
    private Integer taskId;

    //领取该子任务的 worker 的 Id
    @Column(nullable = false,name = "worker_id")
    private Integer workerId;

    //以下两个字段决定了子任务的区间
    //该子任务的开始题号
    @Column(nullable = false,name = "begin_at")
    private Integer beginAt;
    //该子任务的结束题号
    @Column(nullable = false,name = "end_at")
    private Integer endAt;

    //子任务类型，0 代表普通任务，1 代表审核任务
    @Column(nullable = false,name = "type_of_subtask")
    private Integer typeOfSubtask;
    //子任务是否完成的标志，-1 代表过期，0 代表未完成，1 代表已完成
    @Column(name = "is_finished")
    private Integer isFinished;

    //创建时间
    @Column(nullable = false,updatable = false,name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;
    //最近更新时间
    @Column(nullable = false,name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    //子任务截止时间，若超出截止时间仍未完成，则子任务过期，该子任务将重新分配
    @Column(name="deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    public Subtask(){

    }

    public Subtask(int taskId, int workerId, int beginAt, int endAt, int typeOfSubtask, int isFinished) {
        this.taskId = taskId;
        this.workerId = workerId;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.typeOfSubtask = typeOfSubtask;
        this.isFinished = isFinished;
    }

    public void setAll(int taskId, int workerId, int beginAt, int endAt, int typeOfSubtask, int isFinished) {
        this.taskId = taskId;
        this.workerId = workerId;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.typeOfSubtask = typeOfSubtask;
        this.isFinished = isFinished;
    }
    public int getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(int beginAt) {
        this.beginAt = beginAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public int getTypeOfSubtask() {
        return typeOfSubtask;
    }

    public void setTypeOfSubtask(int typeOfSubtask) {
        this.typeOfSubtask = typeOfSubtask;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
