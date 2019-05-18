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


/**
 * Answer controller class
 *
 * @author jjc
 * @date 2019/05/17
 */
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
    public ResultMap answerFindById(Integer id) {
        if(id == null){
            return new ResultMap().fail("400").message("empty input");
        }
        return new ResultMap().success().data("Answer", answerService.findAnswerById(id));
    }

    @RequestMapping(value = "/find-by-task-id", method = RequestMethod.GET)
    public ResultMap answerFindByTaskId(Integer taskId) {
        if(taskId == null){
            return new ResultMap().fail("400").message("empty input");
        }
        List<Answer> result = answerService.findAnswerByTaskId(taskId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Answer.");
        }
        return new ResultMap().success().data("Answers", result);
    }

    @RequestMapping(value = "/find-by-worker-id", method = RequestMethod.GET)
    public ResultMap answerFindByWorkerId(Integer workerId) {
        if(workerId == null){
            return new ResultMap().fail("400").message("empty input");
        }
        List<Answer> result = answerService.findAnswerByWorkerId(workerId);
        if (result.isEmpty()) {
            return new ResultMap().success("204").message("there is no Answer.");
        }
        return new ResultMap().success().data("Answers", result);
    }

    //根据 subtaskId 获取答案信息，用于 checker 获取对应的前 population-1 组答案
    /**
     * worker 第一次提交子任务答案（弃用）
     */
    @RequestMapping(value = "/find-by-subtask-id",method = RequestMethod.GET)
    public ResultMap answerFindBySubtaskId(Integer subtaskId,Integer taskId){
        if(taskId == null || subtaskId == null){
            return new ResultMap().fail("400").message("empty input");
        }
        String answers=answerService.findAnswerBySubtaskId(subtaskId,taskId);
        return new ResultMap().success().data("answers",answers);
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

    /**
     * worker 第一次提交子任务答案（弃用）
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap answerAdd(Integer taskId, String answer, Timestamp answerTime,Integer subtaskId,Integer beginAt,Integer endAt) {
        if(taskId == null || answer == null || answerTime == null || subtaskId == null || beginAt == null || endAt == null){
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore answerId=new IdStore();
        String message = answerService.addAnswer(workerService.findWorkerByUsername(username).getId(), taskId,answer,answerTime,answerId,subtaskId,beginAt,endAt);
        String succeed = "succeed";
        if (!succeed.equals(message)) {
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("AnswerId",answerId.getId());
    }

    // worker 更新子任务答案
    /**
     * worker 第一次提交子任务答案（弃用）
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultMap answerUpdate(Integer taskId, String answer, Timestamp answerTime,Integer subtaskId, Integer beginAt,Integer endAt) {
        if(taskId == null || answer == null || answerTime == null || subtaskId == null || beginAt == null || endAt == null){
            return new ResultMap().fail("400").message("empty input");
        }
        //判断该答案字段是否已存在
        if(answerRepository.existsBySubtaskId(subtaskId)){
            //答案字段已存在
            Answer answer1=answerRepository.findBySubtaskId(subtaskId);
            String authToken = request.getHeader(this.tokenHeader);
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            String message = answerService.updateAnswer(workerService.findWorkerByUsername(username).getId(),taskId, answer,answerTime,answer1.getId(),subtaskId,beginAt,endAt);
            String succeed = "succeed";
            if(!succeed.equals(message)){
                return new ResultMap().fail("400").message("update failed");
            }
            return new ResultMap().success("201").message(message).data("AnswerId",answer1.getId());
        }
        else{
            //答案字段不存在
            String authToken = request.getHeader(this.tokenHeader);
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            IdStore answerId=new IdStore();
            String message = answerService.addAnswer(workerService.findWorkerByUsername(username).getId(), taskId,answer,answerTime,answerId,subtaskId,beginAt,endAt);
            String succeed = "succeed";
            if (!succeed.equals(message)) {
                return new ResultMap().fail("400").message(message);
            }
            return new ResultMap().success("201").message(message).data("AnswerId",answerId.getId());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap answerDelete(Integer id) {
        if(id == null){
            return new ResultMap().fail("400").message("empty input");
        }
        String message = answerService.deleteAnswer(id);
        return new ResultMap().success("201").message(message);
    }
}
