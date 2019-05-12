package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.Task;
import com.example.now.entity.Worker;
import com.example.now.service.RequesterService;
import com.example.now.service.WorkerService;
import com.example.now.service.TaskService;
import com.example.now.entity.ResultMap;
import com.example.now.util.TaskUtil;
import com.example.now.util.TokenUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.now.util.DESUtil;

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
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResultMap taskFindAll() {
        List<Task> usable_task=taskService.findAllTask();
        List<Task> tasks=new ArrayList<Task>();
        for(Task task : usable_task){
            if(task.getStatus() != task.getPopulation() + 1){
                tasks.add(task);
            }
        }
        return new ResultMap().success().data("tasks", TaskUtil.selectReviewedTask(tasks));
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
    public ResultMap taskAdd(String name, String description, Float reward, int status, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore taskId=new IdStore();
        String message = taskService.addTask(name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,start_time,end_time,population,level,time_limitation,pay_time,area,usage,min_age,max_age,taskId);
        if (!message.equals("succeed")) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("taskId",taskId.getId());
    }

    @RequestMapping(value = "/add-questionaire", method = RequestMethod.POST)
    public ResultMap questionaireAdd(String name, String description, Float reward, int status, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age,String url) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore taskId=new IdStore();
        String message = taskService.addTask(name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,start_time,end_time,population,level,time_limitation,pay_time,area,usage,min_age,max_age,taskId);
        if (!message.equals("succeed")) {
            return new ResultMap().fail("400").message(message);
        }
        Task the_task = taskService.findTaskById(taskId.getId());
        String message1 = taskService.createTaskResource(taskId.getId(), url);
        if (!message1.equals("succeed")) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("taskId",taskId.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap taskUpdate(int taskId, String name, String description, Float reward, int status, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        String message = taskService.updateTask(taskId,name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,start_time,end_time,population,level,time_limitation,pay_time,area,usage,min_age,max_age);
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

    @RequestMapping(value = "/add-resource", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(int taskId, String description, String options, MultipartFile file) {
        String message = taskService.createTaskResource(taskId, description, options, file);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-no-file", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(int taskId, String description, String options) {
        String message = taskService.createTaskResource(taskId, description, options);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-no-options", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(int taskId, String description, MultipartFile file) {
        String message = taskService.createTaskResource(taskId, description, file);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-from-url", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(int taskId, String url) {
        String message = taskService.createTaskResource(taskId, url);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/read-resource", method = RequestMethod.GET)
    public String taskResourceFind(int taskId) {
        String content = taskService.readTaskResource(taskId);
        JSONObject json=new JSONObject(content);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/submitResponseCallback", method = RequestMethod.POST)
    public ResultMap submitResponseCallback(String userId, String taskId, int status) {
        int realUserId = Integer.parseInt(DESUtil.decode("monetware", userId));
        int realTaskId = Integer.parseInt(DESUtil.decode("monetware", taskId));
        Worker the_worker = workerService.findWorkerById(realUserId);
        Task the_task = taskService.findTaskById(realTaskId);
        if (status == 1) {
            try {
                the_worker.setBalance(the_worker.getBalance() + the_task.getReward());
                workerService.updateWorker(the_worker.getId(), the_worker.getUsername(), the_worker.getName(), the_worker.getTeleNumber(), the_worker.getEMail(), the_worker.getWithdrawnMethod(), the_worker.getEducation(), the_worker.getWorkArea(), the_worker.getAge(), the_worker.getGender(), the_worker.getMajor(), the_worker.getSchool(), the_worker.getCorrect_number_answered(), the_worker.getAll_number_answered(), the_worker.getOvertime_number(), the_worker.getBalance());
            } catch (Exception e) {
                return new ResultMap().fail("503");
            }
        }
        return new ResultMap().success();
    }
    @RequestMapping(value = "/getDES", method = RequestMethod.POST)
    public String submit(String userId, String taskId) {
        String realUserId = DESUtil.encode("monetware", userId);
        String realTaskId = DESUtil.encode("monetware", taskId);
        JSONObject json=new JSONObject();
        json.put("userId",realUserId);
        json.put("taskId",realTaskId);
        return json.toString();
    }
    @RequestMapping(value = "/update-status",method = RequestMethod.PUT)
    public ResultMap taskUpdateStatus(){
        taskService.updateStatus();
        return new ResultMap().success("201").message("success");
    }
}
