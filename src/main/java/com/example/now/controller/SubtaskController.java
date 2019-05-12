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
    public ResultMap SubtaskFindAll() {
        return new ResultMap().success().data("Sub_tasks", subtaskService.findAllSubtask());
    }

    @RequestMapping(value = "/find-by-sub-task-id", method = RequestMethod.GET)
    public ResultMap SubtaskFindById(int id) {
        return new ResultMap().success().data("Subtask", subtaskService.findSubtaskById(id));
    }

    @RequestMapping(value = "/find-by-task-id", method = RequestMethod.GET)
    public ResultMap SubtaskFindByTaskId(int taskId) {
        List<Subtask> result = subtaskService.findSubtaskByTaskId(taskId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Subtask.");
        }
        return new ResultMap().success().data("Subtasks", result);
    }

    @RequestMapping(value = "/find-by-worker-id", method = RequestMethod.GET)
    public ResultMap SubtaskFindByWorkerId(int workerId) {
        List<Subtask> result = subtaskService.findSubtaskByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Subtask.");
        }
        return new ResultMap().success().data("Subtasks", result);
    }

    @RequestMapping(value = "/find-my-sub-task", method = RequestMethod.GET)
    public ResultMap SubtaskFindMySubtask() {
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
    public ResultMap SubtaskAdd(int number_wanted, int task_id, Timestamp created_time, Timestamp deadline) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore SubtaskId=new IdStore();
        Task the_task = taskService.findTaskById(task_id);
        int population = the_task.getPopulation();
        JSONObject the_rest_of_questions = new JSONObject(the_task.getRest_of_question());
        int begin = 0;
        int end = 0;
        int number_of_task = -1;
        int type = 0;
        if(the_task.getStatus() == 0) {
            boolean flag=false;
            for (int i = 0; i < population - 1; i++) {
                JSONArray rest_of_questions_list = the_rest_of_questions.getJSONArray(String.valueOf(i));
                number_of_task = i;
                if (rest_of_questions_list.length() > 0) {
                    begin = Integer.parseInt(rest_of_questions_list.getJSONObject(0).getString("begin"));
                    int the_end = Integer.parseInt(rest_of_questions_list.getJSONObject(0).getString("end"));
                    if (the_end - begin >= number_wanted) {
                        end = begin + number_wanted - 1;
                        rest_of_questions_list.getJSONObject(0).put("begin", String.valueOf(end+1));
                    } else {
                        end = the_end;
                        rest_of_questions_list.remove(0);
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
            JSONArray rest_of_questions_list = the_rest_of_questions.getJSONArray(String.valueOf(population));
            number_of_task = population;
            type = 1;
            if (rest_of_questions_list.length() > 0) {
                begin = Integer.parseInt(rest_of_questions_list.getJSONObject(0).getString("begin"));
                int the_end = Integer.parseInt(rest_of_questions_list.getJSONObject(0).getString("end"));
                if (the_end - begin >= number_wanted) {
                    end = begin + number_wanted - 1;
                    rest_of_questions_list.getJSONObject(0).put("begin", String.valueOf(end));
                } else {
                    end = the_end;
                    rest_of_questions_list.remove(0);
                }
            }
        }
        String message = subtaskService.addSubtask(begin, end, created_time, deadline, created_time, 0, type, workerService.findWorkerByUsername(username).getId(), task_id, number_of_task, SubtaskId, begin);
        if (!message.equals("succeed")) {
            return new ResultMap().fail("400").message(message);
        }
        the_task.setRest_of_question(the_rest_of_questions.toString());
        taskRepository.saveAndFlush(the_task);
        return new ResultMap().success("201").message(message).data("SubtaskId",SubtaskId.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap SubtaskUpdate(Timestamp updated_time, Timestamp deadline, int id) {
        Subtask the_Subtask = subtaskService.findSubtaskById(id);
        String message = subtaskService.updateSubtask(the_Subtask.getBegin(), the_Subtask.getEnd(), the_Subtask.getCreated_time(), deadline, updated_time, the_Subtask.getIsFinished(), the_Subtask.getType(), the_Subtask.getWorkerId(), the_Subtask.getTaskId(),the_Subtask.getNumber_of_task(), the_Subtask.getNow_begin(), id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap SubtaskDelete(int id) {
        String message = subtaskService.deleteSubtask(id);
        return new ResultMap().success("201").message(message);
    }

    @Scheduled(cron = "0 0 0,12 * * ?")
    public void revokeOverdueSubtask(){
        subtaskService.updateIsFinished();
        taskService.updateStatus();
    }
}
