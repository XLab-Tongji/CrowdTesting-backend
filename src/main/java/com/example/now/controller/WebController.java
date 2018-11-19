package com.example.now.controller;
import com.example.now.entity.User;
import com.example.now.repository.UserRepository;
import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import com.example.now.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(String name,String password,String role) {
        String encodePassword = MD5Util.encode(password);
        User temp = userRepository.findByUsername(name);
        if(temp != null)
            return "user has existed";
        User user = new User(name,encodePassword,role);
        userRepository.saveAndFlush(user);
        return "success";
    }
    @RequestMapping(value = "/changePassword",method = RequestMethod.PUT)
    public String changePassword(String name,String password) {
        User user = userRepository.findByUsername(name);
        if(user == null)
            return "user did not exist";
        user.setPassword(password);
        userRepository.saveAndFlush(user);
        return "success";
    }
}
