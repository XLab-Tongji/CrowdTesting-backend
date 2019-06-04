package com.example.now.entity;

import javax.persistence.*;
import lombok.Data;


/**
 * Worker entity class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Entity
@Data
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue
    @Column(name = "worker_id")
    private int id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "tele_number")
    private String teleNumber;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "balance")
    private float balance;

    @Column(name = "withdrawn_method")
    private String withdrawnMethod;

    @Column(name = "education")
    private String education;

    @Column(name = "work_area")
    private String workArea;

    @Column(name = "age")
    private int age;


    @Column(name = "gender")
    private String gender;

    @Column(name = "major")
    private String major;

    @Column(name = "institution")
    private String institution;

    @Column(name = "correct_number_answered")
    private int correctNumberAnswered;

    @Column(name = "overtime_number")
    private int overtimeNumber;

    @Column(name = "all_number_answered")
    private int allNumberAnswered;

    @Transient
    private int credit;

    @Transient
    public int getCredit() {
        return credit;
    }

    @Transient
    public void setCredit(){
        final double accuracyRateLine = 0.6;
        double correctNumberAnswered = this.correctNumberAnswered;
        double allNumberAnswered = this.allNumberAnswered;
        double overtimeNumber = this.overtimeNumber;
        double accuracyRate = 0;
        if(allNumberAnswered!=0){
            accuracyRate = correctNumberAnswered / allNumberAnswered;
        }
        else{
            accuracyRate = 0.8;
        }
        final double numberAnsweredLine = 100;
        if(allNumberAnswered > numberAnsweredLine && accuracyRate <= accuracyRateLine){
            this.credit = 0;
        }
        else{
            double a = 3 + (accuracyRate - 0.8) * 5;
            double b = 2 / (Math.exp(-allNumberAnswered/100)+1);
            double c = 2 /(Math.exp(-overtimeNumber/10)+1);
            this.credit = (int)(4 + (accuracyRate - 0.8) * 5 + 2 / (Math.exp(-allNumberAnswered/100)+1) - 2/(Math.exp(-overtimeNumber/10)+1));
            //this.credit = (int)(((accuracyRate - 0.6) * 2.5 * 0.8527 + 0.1862 * 2 / (Math.exp(-allNumberAnswered/100)+1) - 0.4881 * 2/(Math.exp(-overtimeNumber/10)+1))*6/1.527);
        }
    }

    public Worker() {

    }

    public Worker(String username, String name, String teleNumber, String eMail,  String withdrawnMethod, String education, String workArea, int age,String gender, String major, String institution, int correctNumberAnswered, int allNumberAnswered, int overtimeNumber) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.balance = 0;
        this.withdrawnMethod = withdrawnMethod;
        this.education = education;
        this.workArea = workArea;
        this.age = age;
        this.gender = gender;
        this.major = major;
        this.institution = institution;
        this.allNumberAnswered = allNumberAnswered;
        this.correctNumberAnswered = correctNumberAnswered;
        this.overtimeNumber = overtimeNumber;
    }

    public void setAll(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String institution, int correctNumberAnswered, int allNumberAnswered, int overtimeNumber, float balance) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.withdrawnMethod = withdrawnMethod;
        this.education = education;
        this.workArea = workArea;
        this.age = age;
        this.gender = gender;
        this.major = major;
        this.institution = institution;
        this.allNumberAnswered = allNumberAnswered;
        this.correctNumberAnswered = correctNumberAnswered;
        this.overtimeNumber = overtimeNumber;
        this.balance = balance;
    }
}
