package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "WORKER")
public class Worker {
    @Id
    @GeneratedValue
    @Column(name="WORKER_ID")
    private int id;

    @Column(nullable = false,name="USERNAME")
    private String username;

    @Column(nullable = false,name="NAME")
    private String name;

    @Column(name="TELE_NUMBER")
    private int teleNumber;

    @Column(name="E_MAIL")
    private String eMail;

    @Column(name="BALANCE")
    private int balance;

    @Column(name="WITHDRAWN_METHOD")
    private String withdrawnMethod;

    @Column(name="EDUCATION")
    private String education;

    @Column(name="WORK_AREA")
    private String workArea;

    @Column(name="AGE")
    private int age;

    public Worker() {

    }

    public Worker(String username,String name) {
        this.username = username;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(int teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getWithdrawnMethod() {
        return withdrawnMethod;
    }

    public void setWithdrawnMethod(String withdrawnMethod) {
        this.withdrawnMethod = withdrawnMethod;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWorkerId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}
