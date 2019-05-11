package com.example.now.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
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
    private float balance;

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
}
