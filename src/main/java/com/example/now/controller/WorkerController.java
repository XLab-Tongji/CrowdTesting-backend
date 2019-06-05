package com.example.now.controller;

import com.example.now.entity.*;
import com.example.now.repository.TransactionInformationRepository;
import com.example.now.service.WorkerService;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Worker controller class
 *
 * @author hyq
 * @date 2019/05/17
 */
@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TransactionInformationRepository transactionInformationRepository;

    /**
     * 查找所有worker，具有管理员权限可以调用
     */
    @RequestMapping(value = "/find-all",method = RequestMethod.GET)
    public ResultMap workerFindAll(){
        return new ResultMap().success().data("workers",workerService.findAllWorker());
    }

    /**
     * 根据ID查找worker
     */
    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap workerFindById(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        return new ResultMap().success().data("worker", workerService.findWorkerById(id));
    }

    /**
     * 根据名字查找worker
     */
    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public ResultMap workerFindByUsername(String username) {
        if (username == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        Worker worker = workerService.findWorkerByUsername(username);
        if (worker == null) {
            return new ResultMap().fail("204").message("can not find worker");
        }
        return new ResultMap().success().data("worker", worker);
    }

    /**
     * 创建一个worker
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap workerAdd(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, Integer age, String gender, String major, String institution) {
        if (eMail == null || username == null || name == null || teleNumber == null || withdrawnMethod == null || education == null || workArea == null || age == null || gender == null || major == null || institution == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        IdStore idStore=new IdStore();
        String message = workerService.addWorker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,idStore,institution);
        return new ResultMap().success("201").message(message).data("workerId",idStore.getId());
    }

    /**
     * 修改worker
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap workerUpdate(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, Integer age, String gender, String major, String institution) {
        if (eMail == null || username == null || name == null || teleNumber == null || withdrawnMethod == null || education == null || workArea == null || age == null || gender == null || major == null || institution == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String authToken = request.getHeader(this.tokenHeader);
        String temp = this.tokenUtils.getUsernameFromToken(authToken);
        Worker theWorker = workerService.findWorkerByUsername(temp);
        String message = workerService.updateWorker(theWorker.getId(),username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,institution,theWorker.getCorrectNumberAnswered(),theWorker.getAllNumberAnswered(), theWorker.getOvertimeNumber(),theWorker.getBalance());
        return new ResultMap().success("201").message(message);
    }

    /**
     * 删除worker
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap workerDelete(Integer id) {
        if (id == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        String message = workerService.deleteWorker(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-myself", method = RequestMethod.GET)
    public ResultMap findMyself() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("worker", workerService.findWorkerByUsername(username));
    }

    /**
     * 查看worker交易记录
     */
    @RequestMapping(value = "/view-transaction-info",method = RequestMethod.GET)
    public ResultMap findMyTransactionInfo() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        Worker worker = workerService.findWorkerByUsername(username);
        List<TransactionInformation> transactionInformations = transactionInformationRepository.findByWorkerId(worker.getId());
        return new ResultMap().success().data("transaction_info", transactionInformations);
    }

    /**
     * 添加 worker 提现记录
     * @param workerId worker id
     * @param value 提现数值
     * @param type 提现方式
     * @return ResultMap
     */
    @RequestMapping(value = "/withdrawal-information",method = RequestMethod.POST)
    public ResultMap withdrawMoneyAsWorker(Integer workerId,Float value,String type){
        if(workerId==null||value==null||type==null){
            return new ResultMap().fail("400").message("empty input");
        }
        String message=workerService.withdrawMoneyAsWorker(workerId,value,type);
        if("worker does not exist".equals(message))
            return new ResultMap().fail("400").message("worker does not exist");
        if("balance is not enough".equals(message))
            return new ResultMap().fail("400").message("balance is not enough");
        return new ResultMap().success("201").message(message);
    }

    /**
     * 查询 worker 提现记录
     * @param workerId worker id
     * @return ResultMap
     */
    @RequestMapping(value = "/withdrawal-information",method = RequestMethod.GET)
    public ResultMap findWithdrawalInformation(Integer workerId){
        List<WithdrawalInformation> informations=workerService.findWithdrawalInformationByWorkerId(workerId);
        return new ResultMap().success().data("withdrawal_information",informations);
    }
}
