package com.example.now.controller;
import com.example.now.entity.*;
import com.example.now.repository.TaskRepository;
import com.example.now.service.*;
import com.example.now.service.Iml.QuestionServiceImpl;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private PersonalTaskService personalTaskService;
    @RequestMapping(value = "/add-question", method = RequestMethod.POST)
    public ResultMap questionAdd(int taskId,String content,int resourceLoading,int type,int compulsory,int questionNumber){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        if(taskService.findTaskById(taskId).getRequesterid()!=requesterService.findRequesterByUsername(username).getRequesterId()){
            return new ResultMap().fail("403").message("你没有权限访问此内容");
        }
        IdStore question=new IdStore();
        String message=questionService.addQuestionToTask(taskId, content, resourceLoading, type,compulsory,questionNumber,question);
        return new ResultMap().success().message(message).data("questionId",question.getId());
    }
    @RequestMapping(value = "/add-option", method = RequestMethod.POST)
    public ResultMap optionAdd(String content,Integer questionId, Integer openAnswerPermission , Integer optionNumber){
        IdStore option=new IdStore();
        String message=questionService.addOptionToQuestion(content, questionId, openAnswerPermission, optionNumber,option);
        if(message.equals("succeed"))
            return new ResultMap().fail("400").message(message);
        return new ResultMap().success().message(message).data("optionId",option.getId());
    }
    @RequestMapping(value = "/see-all-question", method = RequestMethod.GET)
    public ResultMap seeAllQuestion(int taskId){
        List<QuestionDetail> questionDetails=questionService.seeAllQuestion(taskId);
        if(questionDetails.isEmpty()){
            return new ResultMap().success("204").message("there are no question");
        }
        else {
            return new ResultMap().success().data("Questions",questionDetails);
        }
    }
    @RequestMapping(value = "/see-my-answer", method = RequestMethod.GET)
    public ResultMap seeMyQuestion(int taskId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("Questions",questionService.seeAllQuestion(taskId,workerService.findWorkerByUsername(username).getWorkerId()));
    }
    @RequestMapping(value = "/see-his-answer", method = RequestMethod.GET)
    public ResultMap seeHisQuestion(int taskId,int workerId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        if(taskService.findTaskById(taskId).getRequesterid()!=requesterService.findRequesterByUsername(username).getRequesterId()){
            return new ResultMap().fail("403").message("你没有权限访问此内容");
        }
        return new ResultMap().success().data("Questions",questionService.seeAllQuestion(taskId,workerId));
    }
    @RequestMapping(value="/see-all-answer",method = RequestMethod.GET)
    public ResultMap seeAllAnswer(int taskId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        if(taskService.findTaskById(taskId).getRequesterid()!=requesterService.findRequesterByUsername(username).getRequesterId()){
            return new ResultMap().fail("403").message("你没有权限访问此内容");
        }
        return new ResultMap().success().data("Questions",questionService.seeAllAnswer(taskId));
    }
    @RequestMapping(value = "/select-one", method = RequestMethod.POST)
    public ResultMap selectOne(int optionId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=workerService.findWorkerByUsername(username).getWorkerId();
        String message=questionService.selectOne(optionId,userid);
        if(message.equals("success")){
            return new ResultMap().success().message(message);
        }
        else
            return new ResultMap().fail("400").message(message);
    }
    @RequestMapping(value = "/answer-one", method = RequestMethod.POST)
    public ResultMap answerOne(int optionId,String content){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=workerService.findWorkerByUsername(username).getWorkerId();
        String message=questionService.answerOne(optionId,userid,content);
        if(message.equals("success")){
            return new ResultMap().success().message(message);
        }
        else
            return new ResultMap().fail("400").message(message);
    }
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResultMap answer(int questionId,String content){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=workerService.findWorkerByUsername(username).getWorkerId();
        String message=questionService.answer(questionId,userid,content);
        if(message.equals("success")){
            return new ResultMap().success().message(message);
        }
        else
            return new ResultMap().fail("400").message(message);
    }
    @RequestMapping(value = "/add-resource", method = RequestMethod.POST)
    public ResultMap addResource(int questionId,int resourceId){
        questionService.addResource(questionId,resourceId);
        return new ResultMap().success("201");
    }
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public ResultMap finish(int taskId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=workerService.findWorkerByUsername(username).getWorkerId();
        String message=personalTaskService.finishOne(userid,taskId);
        if(message.equals("succeed")){
            return new ResultMap().success("201").message(message);
        }
        else
            return new ResultMap().fail("400").message(message);
    }
}
