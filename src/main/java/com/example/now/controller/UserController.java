package com.example.now.controller;

import com.example.now.service.UserService;
import com.example.now.entity.ResultMap;
import com.example.now.util.MD5Util;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultMap register(String username,String password, String role) {
        String message = userService.register(username, password, role);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public ResultMap changePassword(String password) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        String message = userService.changePassword(username, password);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }
}
