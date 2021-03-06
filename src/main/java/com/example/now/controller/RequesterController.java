package com.example.now.controller;

import com.example.now.entity.*;
import com.example.now.repository.TransactionInformationRepository;
import com.example.now.service.RequesterService;
import com.example.now.service.TaskService;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import javax.validation.constraints.NotNull;


/**
 * Requester controller class
 *
 * @author hyq
 * @date 2019/05/17
 */
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
    @Autowired
    private TransactionInformationRepository transactionInformationRepository;

    /**
     * 根据ID查找requester
     */
    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap requesterFindById(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        return new ResultMap().success().data("requester", requesterService.findRequesterById(id));
    }

    /**
     * 根据名字查找requester
     */
    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public ResultMap requesterFindByUsername(String username) {
        if (username == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        Requester requester = requesterService.findRequesterByUsername(username);
        if (requester == null) {
            return new ResultMap().fail("204").message("can not find requester");
        }
        return new ResultMap().success().data("requester", requester);
    }

    /**
     * 创建一个requester
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap requesterAdd(String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, Integer age) {
        if (username == null || name == null || teleNumber == null || eMail == null || institutionName == null || address == null || payMethod == null || gender == null || age == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        IdStore idStore=new IdStore();
        String message = requesterService.addRequester(username, name,teleNumber,eMail,institutionName,address,payMethod,gender,age,idStore);
        return new ResultMap().success("201").message(message).data("requesterId",idStore.getId());
    }

    /**
     * 修改requester
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap requesterUpdate(String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, Integer age) {
        if (username == null || name == null || teleNumber == null || eMail == null || institutionName == null || address == null || payMethod == null || gender == null || age == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String temp = this.tokenUtils.getUsernameFromToken(authToken);
        String message = requesterService.updateRequester(requesterService.findRequesterByUsername(temp).getRequesterId(),username, name,teleNumber,eMail,institutionName,address,payMethod,gender,age);
        return new ResultMap().success("201").message(message);
    }

    /**
     * 删除requester
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap requesterDelete(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = requesterService.deleteRequester(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-myself", method = RequestMethod.GET)
    public ResultMap findMyself() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("requester", requesterService.findRequesterByUsername(username));
    }

    /**
     * requester 获取图像标记类型题的答案，用于判断正误，默认一道题目的 n 份一起判断
     * 返回答案结构：
     * [[ {ans:[],index:1,isCorrect:-1 },{} ],[],[]]
     * isCorrect 用来表示答案的正确与否，正确为 1，不正确为 0，未判断正误为 -1
     * 参数：number 代表想要判断的题数，1 题对应 population 份答案
     */
    @RequestMapping(value = "/judge-answer",method = RequestMethod.GET)
    public ResultMap getJudgedAnswer(Integer taskId, Integer number,Integer subtaskId){
        if (taskId == null || number == null||subtaskId==null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String answer=taskService.getJudgedAnswer(taskId,number,subtaskId);
        String fail = "failed";
        if(fail.equals(answer)) {
            return new ResultMap().fail("400").message("failed");
        }
        return new ResultMap().success("201").data("Answers",answer);
    }

    /**
     * 将 requester 的判断结果更新到 task，若此任务的所有答案都已判断完成，则计算对应 worker 的正确数与答题数
     */
    @RequestMapping(value = "/judge-answer",method = RequestMethod.PUT)
    public ResultMap judgeAnswer(Integer taskId,String answer){
        if (taskId == null || answer == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message=taskService.judgeAnswer(taskId,answer);
        String fail = "failed";
        if(fail.equals(message)) {
            return new ResultMap().fail("400").message("failed");
        }
        return new ResultMap().success("201").message(message);
    }

    /**
     * 查看requester交易记录
     */
    @RequestMapping(value = "/view-transaction-info",method = RequestMethod.GET)
    public ResultMap findMyTransactionInfo() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        Requester requester = requesterService.findRequesterByUsername(username);
        List<TransactionInformation> transactionInformations = transactionInformationRepository.findByRequesterId(requester.getRequesterId());
        return new ResultMap().success().data("transaction_info", transactionInformations);
    }

    /**
     * 添加 requester 提现记录
     * @param requesterId requester id
     * @param value 提现数值
     * @param type 提现方式
     * @return ResultMap
     */
    @RequestMapping(value = "/withdrawal-information",method = RequestMethod.POST)
    public ResultMap withdrawMoneyAsRequester(Integer requesterId,Float value,String type){
        if(requesterId==null||value==null||type==null){
            return new ResultMap().fail("400").message("empty input");
        }
        String message=requesterService.withdrawMoneyAsRequester(requesterId,value,type);
        if("requester does not exist".equals(message))
            return new ResultMap().fail("400").message("requester does not exist");
        if("balance is not enough".equals(message))
            return new ResultMap().fail("400").message("balance is not enough");
        return new ResultMap().success("201").message(message);
    }

    /**
     * 查询 requester 提现记录
     * @param requesterId requester id
     * @return ResultMap
     */
    @RequestMapping(value = "/withdrawal-information",method = RequestMethod.GET)
    public ResultMap findWithdrawalInformation(Integer requesterId){
        List<WithdrawalInformation> informations=requesterService.findWithdrawalInformationByRequesterId(requesterId);
        return new ResultMap().success().data("withdrawal_information",informations);
    }
}
