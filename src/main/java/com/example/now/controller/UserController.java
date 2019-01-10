package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;
import com.example.now.service.RequesterService;
import com.example.now.entity.Worker;
import com.example.now.service.WorkerService;

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
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultMap register(String username,String password, String role) {
        String message = userService.register(username, password, role);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }
    @RequestMapping(value = "/register-as-requester", method = RequestMethod.POST)
    public ResultMap registerAsRequester(String eMail,String password, String username, String name, String teleNumber, String research_field, String institutionName, String address, String payMethod, String gender, int age) {
        String message1 = userService.register(eMail, password, "ROLE_REQUESTER");
        if (message1 != "succeed") {
            return new ResultMap().fail("400").message(message1);
        }
        IdStore idStore=new IdStore();
        String message2 = requesterService.addRequester(username, name,teleNumber,eMail,research_field,institutionName,address,payMethod,gender,age,idStore);
        return new ResultMap().success("201").message(message2).data("requesterId",idStore.getId());
    }
    @RequestMapping(value = "/register-as-worker", method = RequestMethod.POST)
    public ResultMap registerAsWorker(String eMail,String password, String username, String name, String teleNumber, String withdrawnMethod, String education, String workArea, int age, String gender, String major) {
        String message1 = userService.register(eMail, password, "ROLE_WORKER");
        if (message1 != "succeed") {
            return new ResultMap().fail("400").message(message1);
        }
        IdStore idStore=new IdStore();
        String message2 = workerService.addWorker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,idStore);
        return new ResultMap().success("201").message(message2).data("workerId",idStore.getId());
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
