package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "task_data")
public class TaskData {
    @EmbeddedId
    private TaskDataPrime prime;
    @Column
    private Integer worker_num;
    @Column
    private Float progress_rate;

    public Integer getWorker_num() {
        return worker_num;
    }

    public void setWorker_num(Integer worker_num) {
        this.worker_num = worker_num;
    }

    public Float getProgress_rate() {
        return progress_rate;
    }

    public void setProgress_rate(Float progress_rate) {
        this.progress_rate = progress_rate;
    }

    public TaskDataPrime getPrime() {
        return prime;
    }

    public Integer getId() {
        return prime.getId();
    }

    public Timestamp getTime() {
        return prime.getTime();
    }

    public void setPrime(TaskDataPrime prime) {
        this.prime = prime;
    }

    public TaskData(){

    }

    public TaskData(int id,int worker_num,float progress_rate){
        this.prime=new TaskDataPrime(id);
        this.worker_num=worker_num;
        this.progress_rate=progress_rate;
    }
}
