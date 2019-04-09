package com.example.now.controller;

import com.example.now.entity.ResultMap;
import com.example.now.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/review-task",method = RequestMethod.PUT)
    public ResultMap reviewTask(int id){
        if(!adminService.reviewTask(id))
            return new ResultMap().fail("400").message("fail to review task");
        return new ResultMap().success().message("succeed in reviewing task");
    }


}
