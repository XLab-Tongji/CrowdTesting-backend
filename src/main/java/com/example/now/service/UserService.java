package com.example.now.service;

public interface UserService {
    String register(String username,String name,String password,String role);
    String changePassword(String name,String password);
}
