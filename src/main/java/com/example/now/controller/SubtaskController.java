package com.example.now.controller;

import com.example.now.entity.*;
import com.example.now.service.RequesterService;
import com.example.now.service.SubtaskService;
import com.example.now.service.TaskService;
import com.example.now.service.WorkerService;
import com.example.now.repository.TaskRepository;
import com.example.now.util.TokenUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.lang.Integer;


/**
 * Subtask controller class
 *
 * @author jjc
 * @date 2019/05/17
 */
@RestController
@RequestMapping("/sub-task")
public class SubtaskController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private SubtaskService subtaskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResultMap subtaskFindAll() {
        return new ResultMap().success().data("Sub_tasks", subtaskService.findAllSubtask());
    }

    @RequestMapping(value = "/find-by-sub-task-id", method = RequestMethod.GET)
    public ResultMap subtaskFindById(int id) {
        return new ResultMap().success().data("Subtask", subtaskService.findSubtaskById(id));
    }

    @RequestMapping(value = "/find-by-task-id", method = RequestMethod.GET)
    public ResultMap subtaskFindByTaskId(int taskId) {
        List<Subtask> result = subtaskService.findSubtaskByTaskId(taskId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Subtask.");
        }
        return new ResultMap().success().data("Subtasks", result);
    }

    @RequestMapping(value = "/find-by-worker-id", method = RequestMethod.GET)
    public ResultMap subtaskFindByWorkerId(int workerId) {
        List<Subtask> result = subtaskService.findSubtaskByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Subtask.");
        }
        return new ResultMap().success().data("Subtasks", result);
    }

    @RequestMapping(value = "/find-my-sub-task", method = RequestMethod.GET)
    public ResultMap subtaskFindMySubtask() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int workerId = workerService.findWorkerByUsername(username).getId();
        List<Subtask> result = subtaskService.findSubtaskByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Subtask.");
        }
        return new ResultMap().success().data("Subtasks", result);
    }

    @RequestMapping(value = "/read-subtask-resource", method = RequestMethod.GET)
    public String subtaskResourceFind(int subtaskId){
        String content = subtaskService.readSubtaskResource(subtaskId);
        JSONObject json=new JSONObject(content);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap subtaskAdd(int numberWanted, int taskId, Timestamp createdTime, Timestamp deadline) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore subtaskId=new IdStore();
        Task theTask = taskService.findTaskById(taskId);
        int population = theTask.getPopulation();
        JSONObject theRestOfQuestions = new JSONObject(theTask.getRestOfQuestion());
        int begin = 0;
        int end = 0;
        int numberOfTask = -1;
        int type = 0;
        if(theTask.getStatus() == 0) {
            boolean flag=false;
            for (int i = 0; i < population - 1; i++) {
                JSONArray restOfQuestionsList = theRestOfQuestions.getJSONArray(String.valueOf(i));
                numberOfTask = i;
                if (restOfQuestionsList.length() > 0) {
                    begin = Integer.parseInt(restOfQuestionsList.getJSONObject(0).getString("begin"));
                    int theEnd = Integer.parseInt(restOfQuestionsList.getJSONObject(0).getString("end"));
                    if (theEnd - begin >= numberWanted) {
                        end = begin + numberWanted - 1;
                        restOfQuestionsList.getJSONObject(0).put("begin", String.valueOf(end+1));
                    } else {
                        end = theEnd;
                        restOfQuestionsList.remove(0);
                    }
                    flag=true;
                    break;
                }
            }
            if(!flag){
                return new ResultMap().fail("400").message("no questions available");
            }

        }
        else {
            JSONArray restOfQuestionsList = theRestOfQuestions.getJSONArray(String.valueOf(population-1));
            numberOfTask = population-1;
            type = 1;
            if (restOfQuestionsList.length() > 0) {
                begin = Integer.parseInt(restOfQuestionsList.getJSONObject(0).getString("begin"));
                int theEnd = Integer.parseInt(restOfQuestionsList.getJSONObject(0).getString("end"));
                if (theEnd - begin >= numberWanted) {
                    end = begin + numberWanted - 1;
                    restOfQuestionsList.getJSONObject(0).put("begin", String.valueOf(end));
                } else {
                    end = theEnd;
                    restOfQuestionsList.remove(0);
                }
            }
        }
        String message = subtaskService.addSubtask(begin, end, createdTime, deadline, createdTime, 0, type, workerService.findWorkerByUsername(username).getId(), taskId, numberOfTask, subtaskId, begin);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        theTask.setRestOfQuestion(theRestOfQuestions.toString());
        taskRepository.saveAndFlush(theTask);
        return new ResultMap().success("201").message(message).data("SubtaskId",subtaskId.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap subtaskUpdate(Timestamp updatedTime, Timestamp deadline, int id) {
        Subtask theSubtask = subtaskService.findSubtaskById(id);
        String message = subtaskService.updateSubtask(theSubtask.getBegin(), theSubtask.getEnd(), theSubtask.getCreatedTime(), deadline, updatedTime, theSubtask.getIsFinished(), theSubtask.getType(), theSubtask.getWorkerId(), theSubtask.getTaskId(),theSubtask.getNumberOfTask(), theSubtask.getNowBegin(), id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap subtaskDelete(int id) {
        String message = subtaskService.deleteSubtask(id);
        return new ResultMap().success("201").message(message);
    }

    @Scheduled(cron = "0 0 0,12 * * ?")
    public void revokeOverdueSubtask(){
        subtaskService.updateIsFinished();
        taskService.updateStatus();
    }
}
