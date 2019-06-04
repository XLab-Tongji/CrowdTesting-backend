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
import java.util.Iterator;
import java.util.List;

import com.example.now.util.DESUtil;


/**
 * Task controller class
 *
 * @author hyq
 * @date 2019/05/17
 */
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
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        Worker worker = workerService.findWorkerByUsername(username);
        worker.setCredit();
        JSONObject areaList = new JSONObject(worker.getWorkArea());
        String specialType = "ver5";
        List<Task> allTask=taskService.findAllTask();
        List<Task> tasks=new ArrayList<Task>();
        for(Task task : allTask){
            if(specialType.equals(task.getType()) && task.getMinAge() <= worker.getAge() && task.getMaxAge() >= worker.getAge() && task.getRestrictions() <= worker.getCredit() && areaList.toString().contains(task.getArea())){
                if(task.getStatus() < task.getPopulation()){
                    tasks.add(task);
                }
            }
            else if(task.getStatus() == 0 && task.getMinAge() <= worker.getAge() && task.getMaxAge() >= worker.getAge() && task.getRestrictions() <= worker.getCredit() && areaList.toString().contains(task.getArea())){
                JSONObject restOfQuestions = new JSONObject(task.getRestOfQuestion());
                Iterator iterator = restOfQuestions.keys();
                while(iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if(iterator.hasNext()) {
                        if (restOfQuestions.getJSONArray(key).length() > 0) {
                            tasks.add(task);
                            break;
                        }
                    }
                }
            }
            else if(task.getStatus() == 1 && task.getMinAge() <= worker.getAge() && task.getMaxAge() >= worker.getAge() && task.getRestrictions() <= worker.getCredit() && areaList.toString().contains(task.getArea())){
                JSONObject restOfQuestions = new JSONObject(task.getRestOfQuestion());
                Iterator iterator = restOfQuestions.keys();
                while(iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if(!iterator.hasNext()) {
                        if (restOfQuestions.getJSONArray(key).length() > 0) {
                            tasks.add(task);
                            break;
                        }
                    }
                }
            }
        }
        return new ResultMap().success().data("tasks", TaskUtil.selectReviewedTask(tasks));
    }

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap taskFindById(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        return new ResultMap().success().data("task", taskService.findTaskById(id));
    }

    @RequestMapping(value = "/find-by-name", method = RequestMethod.GET)
    public ResultMap taskFindByName(String name) {
        if (name == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        List<Task> result = taskService.findTaskByName(name);
        if (result.isEmpty()) {
            return new ResultMap().fail("204").message("there is no task with that name");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/find-by-requester-id", method = RequestMethod.GET)
    public ResultMap taskFindByRequesterId(Integer requesterId) {
        if (requesterId == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        List<Task> result = taskService.findTaskByRequesterId(requesterId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no task published by that requester");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/find-by-reward", method = RequestMethod.GET)
    public ResultMap taskFindByReward(Integer lowest, Integer highest) {
        if (lowest == null || highest == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        List<Task> result = taskService.findTaskByReward(lowest, highest);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no task whose reward is in the given range");
        }
        return new ResultMap().success().data("tasks", result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap taskAdd(String name, String description, Float reward, Integer status, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, Integer population, Integer level, Float timeLimitation, Float payTime, String area, String usage, Integer minAge, Integer maxAge) {
        if (name == null || description == null || reward == null || status == null || type == null || restrictions == null || startTime == null || endTime == null || population == null || level == null || timeLimitation == null || payTime == null || area == null || usage == null || minAge == null || maxAge == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore taskId=new IdStore();
        String message = taskService.addTask(name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,startTime,endTime,population,level,timeLimitation,payTime,area,usage,minAge,maxAge,taskId);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("taskId",taskId.getId());
    }

    @RequestMapping(value = "/add-questionnaire", method = RequestMethod.POST)
    public ResultMap questionnaireAdd(String name, String description, Float reward, Integer status, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, Integer population, Integer level, Float timeLimitation, Float payTime, String area, String usage, Integer minAge, Integer maxAge,String url) {
        if (name == null || description == null || reward == null || status == null || type == null || restrictions == null || startTime == null || endTime == null || population == null || level == null || timeLimitation == null || payTime == null || area == null || usage == null || minAge == null || maxAge == null || url == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore taskId=new IdStore();
        String message = taskService.addTask(name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,startTime,endTime,population,level,timeLimitation,payTime,area,usage,minAge,maxAge,taskId);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        String message1 = taskService.createTaskResource(taskId.getId(), url);
        if (!succeed.equals(message1)) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("taskId",taskId.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap taskUpdate(int taskId, String name, String description, Float reward, Integer status, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, Integer population, Integer level, Float timeLimitation, Float payTime, String area, String usage, Integer minAge, Integer maxAge) {
        if (name == null || description == null || reward == null || status == null || type == null || restrictions == null || startTime == null || endTime == null || population == null || level == null || timeLimitation == null || payTime == null || area == null || usage == null || minAge == null || maxAge == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        String message = taskService.updateTask(taskId,name, description,reward,status,requesterService.findRequesterByUsername(username).getRequesterId(),type,restrictions,startTime,endTime,population,level,timeLimitation,payTime,area,usage,minAge,maxAge);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap taskDelete(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
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
    public ResultMap taskResourceAdd(Integer taskId, String description, String options, MultipartFile file) {
        if (taskId == null || description == null || options == null || file == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = taskService.createTaskResource(taskId, description, options, file);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-no-file", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(Integer taskId, String description, String options) {
        if (taskId == null || description == null || options == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = taskService.createTaskResource(taskId, description, options);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-no-options", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(Integer taskId, String description, MultipartFile file) {
        if (taskId == null || description == null || file == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = taskService.createTaskResource(taskId, description, file);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/add-resource-from-url", method = RequestMethod.POST)
    public ResultMap taskResourceAdd(Integer taskId, String url) {
        if (taskId == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = taskService.createTaskResource(taskId, url);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/read-resource", method = RequestMethod.GET)
    public String taskResourceFind(Integer taskId) {
        if (taskId == null) {
            return "empty input";
        }
        String content = taskService.readTaskResource(taskId);
        JSONObject json=new JSONObject(content);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/submitResponseCallback", method = RequestMethod.POST)
    public ResultMap submitResponseCallback(String userId, String taskId, Integer status) {
        if (taskId == null || userId == null || status == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        int realUserId = Integer.parseInt(DESUtil.decode("monetware", userId));
        int realTaskId = Integer.parseInt(DESUtil.decode("monetware", taskId));
        Worker theWorker = workerService.findWorkerById(realUserId);
        Task theTask = taskService.findTaskById(realTaskId);
        if (status == 1) {
            try {
                theWorker.setBalance(theWorker.getBalance() + theTask.getReward());
                workerService.updateWorker(theWorker.getId(), theWorker.getUsername(), theWorker.getName(), theWorker.getTeleNumber(), theWorker.getEMail(), theWorker.getWithdrawnMethod(), theWorker.getEducation(), theWorker.getWorkArea(), theWorker.getAge(), theWorker.getGender(), theWorker.getMajor(), theWorker.getInstitution(), theWorker.getCorrectNumberAnswered(), theWorker.getAllNumberAnswered(), theWorker.getOvertimeNumber(), theWorker.getBalance());
                theTask.setStatus(theTask.getStatus() + 1);
                if(theTask.getPopulation() == theTask.getStatus()){
                    theTask.setIsFinished(1);
                }
            } catch (Exception e) {
                return new ResultMap().fail("503");
            }
        }
        return new ResultMap().success();
    }
    @RequestMapping(value = "/getDES", method = RequestMethod.POST)
    public String submit(String userId, String taskId) {
        if (userId == null || taskId == null) {
            return "empty input";
        }
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
