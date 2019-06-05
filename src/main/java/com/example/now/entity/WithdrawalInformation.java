package com.example.now.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Withdrawal information entity class
 *
 * @author jjc
 * @date 2019/06/05
 */
@Data
@Entity
@Table(name = "withdrawal_information")
public class WithdrawalInformation {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false,name = "requester_id")
    private Integer requesterId;

    @Column(nullable = false,name = "worker_id")
    private Integer workerId;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "value")
    private Float value;

    //提现方式，如支付宝、微信等
    @Column(name="type")
    private String type;

    //提现是否完成,0 代表未完成，1 代表完成，默认值为 0
    @Column(name = "status")
    private Integer status;

    public WithdrawalInformation(){
    }

    public WithdrawalInformation(Integer requesterId, Integer workerId, Timestamp time, Float value, String type, Integer status) {
        this.requesterId = requesterId;
        this.workerId = workerId;
        this.time = time;
        this.value = value;
        this.type = type;
        this.status = status;
    }

    public void setAll(Integer requesterId, Integer workerId, Timestamp time, Float value, String type, Integer status) {
        this.requesterId = requesterId;
        this.workerId = workerId;
        this.time = time;
        this.value = value;
        this.type = type;
        this.status=status;
    }
}
