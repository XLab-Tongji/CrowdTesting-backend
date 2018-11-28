package com.example.now.controller;
import com.example.now.entity.QuestionDetail;
import com.example.now.service.QuestionService;
import com.example.now.service.Iml.QuestionServiceImpl;
import com.example.now.entity.ResultMap;
import com.example.now.service.WorkerService;
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
    @RequestMapping(value = "/add-question", method = RequestMethod.PUT)
    public ResultMap questionAdd(int taskId,String content,int resourceLoading,int type){
        String message=questionService.addQuestionToTask(taskId, content, resourceLoading, type);
        return new ResultMap().success().message(message);
    }
    @RequestMapping(value = "/option-add", method = RequestMethod.PUT)
    public ResultMap optionAdd(String content,int questionId, int openAnswerPermittion, int optionNumber){
        String message=questionService.addOptionToQuestion(content, questionId, openAnswerPermittion, optionNumber);
        return new ResultMap().success().message(message);
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
        return new ResultMap().success().data("Questions",questionService.seeAllQuestion(taskId,workerId));
    }
}
