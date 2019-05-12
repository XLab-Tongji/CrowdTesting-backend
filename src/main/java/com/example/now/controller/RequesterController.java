package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;
import com.example.now.service.RequesterService;
import com.example.now.entity.ResultMap;
import com.example.now.service.TaskService;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requester")
public class RequesterController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap requesterFindById(int id) {
        return new ResultMap().success().data("requester", requesterService.findRequesterById(id));
    }           //根据ID查找requester

    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public ResultMap requesterFindByUsername(String username) {                           //根据名字查找requester
        Requester requester = requesterService.findRequesterByUsername(username);
        if (requester == null) {
            return new ResultMap().fail("204").message("can not find requester");
        }
        return new ResultMap().success().data("requester", requester);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap requesterAdd(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age) {                      //创建一个requester
        IdStore idStore=new IdStore();
        String message = requesterService.addRequester(username, name,teleNumber,eMail,research_field,institutionName,address,payMethod,gender,age,idStore);
        return new ResultMap().success("201").message(message).data("requesterId",idStore.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap requesterUpdate(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age) {                      //修改requester
        String authToken = request.getHeader(this.tokenHeader);
        String temp = this.tokenUtils.getUsernameFromToken(authToken);
        String message = requesterService.updateRequester(requesterService.findRequesterByUsername(temp).getRequesterId(),username, name,teleNumber,eMail,research_field,institutionName,address,payMethod,gender,age);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap requesterDelete(int id) {                      //删除requester
        String message = requesterService.deleteRequester(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-myself", method = RequestMethod.GET)
    public ResultMap findMyself() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("requester", requesterService.findRequesterByUsername(username));
    }

    //requester 获取图像标记类型题的答案，用于判断正误，默认一道题目的 n 份一起判断
    //返回答案结构：
    // [[ {ans:[],index:1,isCorrect:-1 },{} ],[],[]]
    // isCorrect 用来表示答案的正确与否，正确为 1，不正确为 0，未判断正误为 -1
    // 参数：number 代表想要判断的题数，1 题对应 population 份答案
    @RequestMapping(value = "/judge-answer",method = RequestMethod.GET)
    public ResultMap getJudgedAnswer(@NotNull Integer taskId, @NotNull Integer number){
        String answer=taskService.getJudgedAnswer(taskId,number);
        if("failed".equals(answer))
            return new ResultMap().fail("400").message("failed");
        return new ResultMap().success("201").data("Answers",answer);
    }

    //将 requester 的判断结果更新到 task，若此任务的所有答案都已判断完成，则计算对应 worker 的正确数与答题数
    @RequestMapping(value = "/judge-answer",method = RequestMethod.PUT)
    public ResultMap judgeAnswer(@NotNull Integer taskId,@NotNull String answer){
        String message=taskService.judgeAnswer(taskId,answer);
        if("failed".equals(message))
            return new ResultMap().fail("400").message("failed");
        return new ResultMap().success("201").message(message);
    }
}
