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


/**
 * User controller class
 *
 * @author hyq
 * @date 2019/05/17
 */
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
        if (username == null || password == null || role == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = userService.register(username, password, role);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/register-as-requester", method = RequestMethod.POST)
    public ResultMap registerAsRequester(String eMail,String password, String username, String name, String teleNumber, String institutionName, String address, String payMethod, String gender, Integer age) {
        if (eMail == null || password == null || username == null || name == null || teleNumber == null || institutionName == null || address == null || payMethod == null || gender == null || age == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message1 = userService.register(eMail, password, "ROLE_REQUESTER");
        String succeed = "succeed";
        if (!succeed.equals(message1)) {
            return new ResultMap().fail("400").message(message1);
        }
        IdStore idStore=new IdStore();
        String message2 = requesterService.addRequester(username, name,teleNumber,eMail,institutionName,address,payMethod,gender,age,idStore);
        return new ResultMap().success("201").message(message2).data("requesterId",idStore.getId());
    }

    @RequestMapping(value = "/register-as-worker", method = RequestMethod.POST)
    public ResultMap registerAsWorker(String eMail,String password, String username, String name, String teleNumber, String withdrawnMethod, String education, String workArea, Integer age, String gender, String major, String institution) {
        if (eMail == null || password == null || username == null || name == null || teleNumber == null || withdrawnMethod == null || education == null || workArea == null || age == null || gender == null || major == null || institution == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message1 = userService.register(eMail, password, "ROLE_WORKER");
        String succeed = "succeed";
        if (!succeed.equals(message1)) {
            return new ResultMap().fail("400").message(message1);
        }
        IdStore idStore=new IdStore();
        String message2 = workerService.addWorker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,idStore,institution);
        return new ResultMap().success("201").message(message2).data("workerId",idStore.getId());
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public ResultMap changePassword(String password) {
        if (password == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        String message = userService.changePassword(username, password);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }
}
