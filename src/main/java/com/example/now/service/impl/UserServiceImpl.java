package com.example.now.service.impl;

import com.example.now.repository.UserRepository;
import com.example.now.entity.User;
import com.example.now.service.UserService;
import com.example.now.service.WorkerService;
import com.example.now.service.RequesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.now.util.MD5Util;


/**
 * User service implementation class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RequesterService requesterService;

    @Override
    public String register(String username, String password, String role) {
        if (username == null || password == null) {
            return "inputs are not enough";
        }
        String encodePassword = MD5Util.encode(password);
        User temp = userRepository.findByUsername(username);
        if (temp != null) {
            return "user has existed";
        }
        User user = new User(username, encodePassword, role);
        userRepository.saveAndFlush(user);
        return "succeed";
    }

    @Override
    public String changePassword(String name, String password) {
        if (password == null) {
            return "password can not be empty";
        }
        User user = userRepository.findByUsername(name);
        String encodePassword = MD5Util.encode(password);
        user.setPassword(encodePassword);
        userRepository.saveAndFlush(user);
        return "success";
    }
}
