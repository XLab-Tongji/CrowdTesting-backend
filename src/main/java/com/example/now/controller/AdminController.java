package com.example.now.controller;

import com.example.now.entity.ResultMap;
import com.example.now.entity.Task;
import com.example.now.service.AdminService;
import com.example.now.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.now.util.TaskUtil.selectUnreviewedTask;


/**
 * Admin controller class
 *
 * @author jjc
 * @date 2019/05/17
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    TaskService taskService;

    /**
     * 审核任务，使任务状态变为已审核
     */
    @RequestMapping(value = "/review-task",method = RequestMethod.PUT)
    public ResultMap reviewTask(Integer id){
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        if(!adminService.reviewTask(id)) {
            return new ResultMap().fail("400").message("fail to review task");
        }
        return new ResultMap().success().message("succeed in reviewing task");
    }

    /**
     * 查找未审核的任务
     */
    @RequestMapping(value = "/find-unreviewed-task",method = RequestMethod.GET)
    public ResultMap findUnreviewedTask(){
        List<Task> tasks=taskService.findAllTask();
        return new ResultMap().success().data("tasks",selectUnreviewedTask(tasks));
    }
}
