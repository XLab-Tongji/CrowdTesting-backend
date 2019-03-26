package com.example.now.entity;

import javax.persistence.*;

@Entity
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

    public Worker(String username, String name, String teleNumber, String eMail,  String withdrawnMethod, String education, String workArea, int age,String gender, String major) {
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
    }

    public void setAll(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major) {
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
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
