package com.example.now.entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "option_selected")
public class OptionSelected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_selected_id")
    private Integer id;
    @Column(name = "option_id")
    private Integer optionId;
    @Column(name = "worker_id")
    private Integer workerId;
    @Column
    private Timestamp time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public OptionSelected(){

    }

    public OptionSelected(Integer optionId, Integer workerId) {
        this.optionId = optionId;
        this.workerId = workerId;
        this.time=new Timestamp(new Date().getTime());
    }
}
