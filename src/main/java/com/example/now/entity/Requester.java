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
    private int teleNumber;

    @Column(name = "E_MAIL")
    private String eMail;

    @Column(name = "AREA")
    private String area;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    @Column(name = "PAY_METHOD")
    private String payMethod;

    public Requester() {

    }

    public Requester(String username, String name) {
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

    public void setTeleNumber(int tele_number) {
        this.teleNumber = tele_number;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
}
