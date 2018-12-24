package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.Task;
import com.example.now.service.RequesterService;
import com.example.now.service.TaskService;
import com.example.now.entity.ResultMap;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResultMap taskFindAll() {
        return new ResultMap().success().data("tasks", taskService.findAllTask());
    }

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap taskFindById(int id) {
        return new ResultMap().success().data("task", taskService.findTaskById(id));
    }

    @RequestMapping(value = "/find-by-name", method = RequestMethod.GET)
    public ResultMap taskFindByName(String name) {
        List<Task> result = taskService.findTaskByName(name);
        if (result.isEmpty()) {
            return new ResultMap().fail("204").message("there is no task with that name");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/find-by-requester-id", method = RequestMethod.GET)
    public ResultMap taskFindByRequesterId(int requesterid) {
        List<Task> result = taskService.findTaskByRequesterId(requesterid);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no task published by that requester");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/find-by-reward", method = RequestMethod.GET)
    public ResultMap taskFindByReward(int lowest, int highest) {
        List<Task> result = taskService.findTaskByReward(lowest, highest);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no task whose reward is in the given range");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap taskAdd(String name, String description, Integer reward, String status, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, int time_limitation, int pay_time,String area,String usage,int min_age,int max_age) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore taskId=new IdStore();
        String message = taskService.addTask(name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,start_time,end_time,level,time_limitation,pay_time,area,usage,min_age,max_age,taskId);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("taskId",taskId.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap taskUpdate(int taskId,String name, String description, Integer reward, String status, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, int time_limitation, int pay_time,String area,String usage,int min_age,int max_age) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        String message = taskService.updateTask(taskId,name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,start_time,end_time,level,time_limitation,pay_time,area,usage,min_age,max_age);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap taskDelete(int id) {
        String message = taskService.deleteTask(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-my-task", method = RequestMethod.GET)
    public ResultMap findMyPublishTask() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        List<Task> tasks = taskService.findTaskByRequesterId(requesterService.findRequesterByUsername(username).getRequesterId());
        if (tasks.isEmpty()) {
            return new ResultMap().success("204").message("there is no task published by you");
        }
        return new ResultMap().success().data("tasks", tasks);
    }
}
