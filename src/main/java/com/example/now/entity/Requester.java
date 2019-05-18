package com.example.now.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Requester entity class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Entity
@Data
@Table(name = "requester")
public class Requester {
    @Id
    @GeneratedValue
    @Column(name = "requester_id")
    private int requesterId;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "tele_number")
    private String teleNumber;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "research_field")
    private String researchField;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "address")
    private String address;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "balance")
    private float balance;

    public Requester() {

    }

    public Requester(String username, String name, String teleNumber, String eMail, String researchField, String institutionName, String address, String payMethod, String gender, int age) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.researchField = researchField;
        this.institutionName = institutionName;
        this.address = address;
        this.payMethod = payMethod;
        this.gender = gender;
        this.age = age;
        this.balance = 0;
    }

    public void setAll(String username, String name, String teleNumber, String eMail, String researchField, String institutionName, String address, String payMethod, String gender, int age) {
        this.username = username;
        this.name = name;
        this.teleNumber = teleNumber;
        this.eMail = eMail;
        this.researchField = researchField;
        this.institutionName = institutionName;
        this.address = address;
        this.payMethod = payMethod;
        this.gender = gender;
        this.age = age;
    }
}
