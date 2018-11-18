package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Time;
import java.io.Serializable;
import java.util.Date;
@Embeddable
public class TaskDataPrime implements Serializable{
    @Column(name = "task_id")
    private Integer id;
    @Column
    private Timestamp time;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return "TaskDataPrime[id="+id.toString()+",time="+time.toString()+"]";
    }

    public TaskDataPrime(){

    }

    public TaskDataPrime(int id){
        this.id=id;
        this.time=new Timestamp(new Date().getTime());
    }
}
