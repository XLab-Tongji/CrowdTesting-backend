package com.example.now.service;

import com.example.now.entity.Requester;

public interface RequesterService {
    Requester findRequesterById(int id);

    Requester findRequesterByUsername(String username);

    String addRequester(String username, String name);

    String updateRequester(Requester requester);

    String deleteRequester(int id);
}