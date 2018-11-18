package com.example.now.controller;
import com.example.now.entity.PersonalTask;
import com.example.now.entity.PersonalTaskKey;
import com.example.now.service.PersonalTaskService;
import com.example.now.entity.ResultMap;
import com.example.now.entity.Worker;
import com.example.now.entity.Task;
import com.example.now.util.TokenUtils;
import com.example.now.service.WorkerService;
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
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping(value = "/find-task-list",method = RequestMethod.GET)
    public ResultMap personalTaskFindTaskList(int id){
        List<Task> tasks=personalTaskService.findTaskByWorkerId(id);
        if(tasks.isEmpty()){
            return new ResultMap().success("204").message("this worker have no task");
        }
        return new ResultMap().success("200").data("tasks",tasks);
    }
    @RequestMapping(value = "/find-worker-list",method = RequestMethod.GET)
    public ResultMap personalTaskFindWorkerList(int id){
        List<Worker> workers=personalTaskService.findWorkerByTaskId(id);
        if(workers.isEmpty()){
            return new ResultMap().success("204").message("this task have no worker");
        }
        return new ResultMap().success("200").data("workers",workers);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultMap personalTaskAdd(Integer taskId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int workerId=workerService.findWorkerByUsername(username).getWorkerId();
        String message=personalTaskService.addPersonalTask(workerId, taskId);
        if(message!="succeed"){
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResultMap personalTaskDelete(Integer workerId,Integer taskId){
        String message=personalTaskService.addPersonalTask(workerId, taskId);
        if(message!="succeed"){
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value="/find-my-task",method = RequestMethod.GET)
    public ResultMap findMyTask(){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        List<Task> tasks=personalTaskService.findTaskByWorkerId(workerService.findWorkerByUsername(username).getWorkerId());
        if(tasks.isEmpty()){
            return new ResultMap().success("204").message("you have no task");
        }
        return new ResultMap().success().data("tasks",tasks);
    }
}
