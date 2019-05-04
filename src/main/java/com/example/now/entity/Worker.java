package com.example.now.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "WORKER")
public class Worker {
    @Id
    @GeneratedValue
    @Column(name = "WORKER_ID")
    private int id;

    @Column(nullable = false, name = "USERNAME")
    private String username;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "tele_number")
    private String teleNumber;

    @Column(name = "E_MAIL")
    private String eMail;

    @Column(name = "BALANCE")
    private int balance;

    @Column(name = "WITHDRAWN_METHOD")
    private String withdrawnMethod;

    @Column(name = "EDUCATION")
    private String education;

    @Column(name = "WORK_AREA")
    private String workArea;

    @Column(name = "AGE")
    private int age;

    @Column(name = "xp")
    private int xp;

    @Column(name = "gender")
    private String gender;

    @Column(name = "major")
    private String major;

    @Column(name = "school")
    private String school;

    @Column(name = "correct_number_answered")
    private int correct_number_answered;

    @Column(name = "overtime_number")
    private int overtime_number;

    @Column(name = "all_number_answered")
    private int all_number_answered;

    @Transient
    private int level;
    @Transient
    public int getLevel() {
        return level;
    }
    @Transient
    public void setLevel(){
        if(xp<=16500)
            this.level=(int)((1+Math.sqrt(1+2*xp/75))/2);
        else
            this.level=11+(xp-16500)/3000;
    }

    public Worker() {

    }

    public Worker(String username, String name, String teleNumber, String eMail,  String withdrawnMethod, String education, String workArea, int age,String gender, String major, String school, int correct_number_answered, int all_number_answered, int overtime_number) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.balance = 0;
        this.withdrawnMethod = withdrawnMethod;
        this.education = education;
        this.workArea = workArea;
        this.age = age;
        this.xp=0;
        this.gender = gender;
        this.major = major;
        this.school = school;
        this.all_number_answered = all_number_answered;
        this.correct_number_answered = correct_number_answered;
        this.overtime_number = overtime_number;
    }

    public void setAll(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String school, int correct_number_answered, int all_number_answered, int overtime_number) {
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
        this.school = school;
        this.all_number_answered = all_number_answered;
        this.correct_number_answered = correct_number_answered;
        this.overtime_number = overtime_number;
    }
}
