package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.Answer;
import com.example.now.repository.AnswerRepository;
import com.example.now.service.RequesterService;
import com.example.now.service.AnswerService;
import com.example.now.entity.ResultMap;
import com.example.now.service.WorkerService;
import com.example.now.util.JsonUtil;
import com.example.now.util.TokenUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.json.JSONArray;
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
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResultMap answerFindAll() {
        return new ResultMap().success().data("Answers", answerService.findAllAnswer());
    }

    @RequestMapping(value = "/find-by-answer-id", method = RequestMethod.GET)
    public ResultMap answerFindById(int id) {
        return new ResultMap().success().data("Answer", answerService.findAnswerById(id));
    }

    @RequestMapping(value = "/find-by-task-id", method = RequestMethod.GET)
    public ResultMap answerFindByTaskId(int taskId) {
        List<Answer> result = answerService.findAnswerByTaskId(taskId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Answer.");
        }
        return new ResultMap().success().data("Answers", result);
    }

    @RequestMapping(value = "/find-by-worker-id", method = RequestMethod.GET)
    public ResultMap answerFindByWorkerId(int workerId) {
        List<Answer> result = answerService.findAnswerByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Answer.");
        }
        return new ResultMap().success().data("Answers", result);
    }

    @RequestMapping(value = "/find-my-answer", method = RequestMethod.GET)
    public ResultMap answerFindMyAnswer() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int workerId = workerService.findWorkerByUsername(username).getId();
        List<Answer> result = answerService.findAnswerByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Answer.");
        }
        return new ResultMap().success().data("Answers", result);
    }

    // worker 第一次提交子任务答案（弃用）
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap answerAdd(int task_id, String answer, Timestamp answer_time,int subtaskId,Integer beginAt,Integer endAt) {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore answerId=new IdStore();
        String message = answerService.addAnswer(workerService.findWorkerByUsername(username).getId(), task_id,answer,answer_time,answerId,subtaskId,beginAt,endAt);
        if (!message.equals("succeed")) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("AnswerId",answerId.getId());
    }

    // worker 更新子任务答案
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultMap answerUpdate(int task_id, String answer, Timestamp answer_time,int subtaskId,Integer beginAt,Integer endAt) {
        //判断该答案字段是否已存在
        if(answerRepository.existsBySubtaskId(subtaskId)){//答案字段已存在
            Answer answer1=answerRepository.findBySubtaskId(subtaskId);
            String authToken = request.getHeader(this.tokenHeader);
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            String message = answerService.updateAnswer(workerService.findWorkerByUsername(username).getId(),task_id, answer,answer_time,answer1.getId(),subtaskId,beginAt,endAt);
            if(!message.equals("succeed")){
                return new ResultMap().fail("400").message("update failed");
            }
            return new ResultMap().success("201").message(message).data("AnswerId",answer1.getId());
        }
        else{//答案字段不存在
            String authToken = request.getHeader(this.tokenHeader);
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            IdStore answerId=new IdStore();
            String message = answerService.addAnswer(workerService.findWorkerByUsername(username).getId(), task_id,answer,answer_time,answerId,subtaskId,beginAt,endAt);
            if (!message.equals("succeed")) {
                return new ResultMap().fail("400").message(message);
            }
            return new ResultMap().success("201").message(message).data("AnswerId",answerId.getId());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap answerDelete(int id) {
        String message = answerService.deleteAnswer(id);
        return new ResultMap().success("201").message(message);
    }
}
