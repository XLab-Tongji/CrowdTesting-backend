package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "task_data")
public class Task_data {
    @EmbeddedId
    private Task_data_prime prime;
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

    public Task_data_prime getPrime() {
        return prime;
    }

    public void setPrime(Task_data_prime prime) {
        this.prime = prime;
    }

    public Task_data(){

    }

    public Task_data(int id,int worker_num,float progress_rate){
        this.prime=new Task_data_prime(id);
        this.worker_num=worker_num;
        this.progress_rate=progress_rate;
    }
}
