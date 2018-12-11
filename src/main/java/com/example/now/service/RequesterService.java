package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;

public interface RequesterService {
    Requester findRequesterById(int id);

    Requester findRequesterByUsername(String username);

    String addRequester(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age,IdStore id);

    String updateRequester(int requesterId,String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age);

    String deleteRequester(int id);
}