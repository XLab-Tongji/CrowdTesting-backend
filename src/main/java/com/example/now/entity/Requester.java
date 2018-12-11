package com.example.now.entity;

import javax.persistence.*;

@Entity
@Table(name = "REQUESTER")
public class Requester {
    @Id
    @GeneratedValue
    @Column(name = "REQUESTER_ID")
    private int requesterId;

    @Column(nullable = false, name = "USERNAME")
    private String username;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(name = "TELE_NUMBER")
    private String teleNumber;

    @Column(name = "E_MAIL")
    private String eMail;

    @Column(name = "research_field")
    private String research_field;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    @Column(name = "address")
    private String address;

    @Column(name = "PAY_METHOD")
    private String payMethod;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "balance")
    private int balance;

    public Requester() {

    }

    public Requester(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.research_field = research_field;
        this.institutionName = institutionName;
        this.address = address;
        this.payMethod = payMethod;
        this.gender = gender;
        this.age = age;
        this.balance = 0;
    }
    public void setAll(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.research_field = research_field;
        this.institutionName = institutionName;
        this.address = address;
        this.payMethod = payMethod;
        this.gender = gender;
        this.age = age;
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

    public void setTeleNumber(String tele_number) {
        this.teleNumber = tele_number;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }


    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public String getUsername() {
        return username;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
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

    public String getResearch_field() {
        return research_field;
    }

    public void setResearch_field(String research_field) {
        this.research_field = research_field;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
