package com.example.now.controller;

import com.example.now.entity.*;
import com.example.now.service.PersonalTaskService;
import com.example.now.util.TokenUtils;
import com.example.now.service.WorkerService;
import com.example.now.service.TaskService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personal-task")
public class PersonalTaskController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private PersonalTaskService personalTaskService;
    @Autowired
    private TaskService TaskService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-task-list", method = RequestMethod.GET)
    public ResultMap personalTaskFindTaskList(int id) {
        List<MyTask> tasks = personalTaskService.findTaskByWorkerId(id);
        if (tasks.isEmpty()) {
            return new ResultMap().success("204").message("this worker have no task");
        }
        return new ResultMap().success("200").data("tasks", tasks);
    }

    @RequestMapping(value = "/find-worker-list", method = RequestMethod.GET)
    public ResultMap personalTaskFindWorkerList(int id) {
        List<Worker> workers = personalTaskService.findWorkerByTaskId(id);
        if (workers.isEmpty()) {
            return new ResultMap().success("204").message("this task have no worker");
        }
        return new ResultMap().success("200").data("workers", workers);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap personalTaskAdd(Integer taskId) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int workerId = workerService.findWorkerByUsername(username).getWorkerId();
        String message = personalTaskService.addPersonalTask(workerId, taskId);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        Task a_task = TaskService.findTaskById(taskId);
//        if(a_task.getStatus().equals("0"))
//        {
//            a_task.setStatus("1");
//        }
//        else if(a_task.getStatus().equals("1"))
//        {
//            a_task.setStatus("2");
//        }
//        else if(a_task.getStatus().equals("2"))
//        {
//            a_task.setStatus("3");
//        }
       // message = TaskService.updateTask(taskId, a_task.getName(),a_task.getDescription(),a_task.getReward(),a_task.getStatus(),a_task.getRequesterid(),a_task.getType(),a_task.getRestrictions(),a_task.getStart_time(),a_task.getEnd_time(),a_task.getLevel(),a_task.getTime_limitation(),a_task.getPay_time(),a_task.getArea(),a_task.getUsage(),a_task.getMin_age(),a_task.getMax_age());
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap personalTaskDelete(Integer workerId, Integer taskId) {
        String message = personalTaskService.addPersonalTask(workerId, taskId);
        if (message != "succeed") {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-my-task", method = RequestMethod.GET)
    public ResultMap findMyTask() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        List<MyTask> tasks = personalTaskService.findTaskByWorkerId(workerService.findWorkerByUsername(username).getWorkerId());
        if (tasks.isEmpty()) {
            return new ResultMap().success("204").message("you have no task");
        }
        return new ResultMap().success().data("tasks", tasks);
    }
}
