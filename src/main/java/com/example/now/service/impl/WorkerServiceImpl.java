package com.example.now.service.impl;

import com.example.now.entity.IdStore;
import com.example.now.entity.WithdrawalInformation;
import com.example.now.entity.Worker;
import com.example.now.repository.WithdrawalInformationRepository;
import com.example.now.service.WorkerService;
import com.example.now.util.TokenUtils;
import com.example.now.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Worker service implementation class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private WithdrawalInformationRepository withdrawalInformationRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public List<Worker> findAllWorker(){
        List<Worker> workers=workerRepository.findAll();
        return workers;
    }
    @Override
    public Worker findWorkerById(int id) {
        Worker worker=workerRepository.findById(id);
        worker.setCredit();
        return worker;
    }

    @Override
    public Worker findWorkerByUsername(String username) {
        Worker worker=workerRepository.findByEMail(username);
        worker.setCredit();
        return worker;
    }

    @Override
    public String addWorker(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, IdStore id, String institution) {
        if (username == null || name == null) {
            return "username or name is empty";
        }
        Worker worker = new Worker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,institution,0,0,0);
        Worker temp=workerRepository.saveAndFlush(worker);
        id.setId(temp.getId());
        return "succeed";
    }

    @Override
    public String updateWorker(int workerId,String username, String name, String teleNumber, String eMail,String withdrawnMethod, String education, String workArea, int age,String gender, String major, String institution, int correctNumberAnswered, int allNumberAnswered, int overtimeNumber, float balance) {
        Worker worker=workerRepository.findById(workerId);
        worker.setAll(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,institution, correctNumberAnswered, allNumberAnswered, overtimeNumber,balance);
        workerRepository.saveAndFlush(worker);
        return "succeed";
    }

    @Override
    public String updateWorkerDirectly(Worker worker) {
        workerRepository.saveAndFlush(worker);
        return "succeed";
    }

    @Override
    public String deleteWorker(int id) {
        workerRepository.deleteById(id);
        workerRepository.flush();
        return "succeed";
    }

    @Override
    public String withdrawMoneyAsWorker(Integer workerId,Float value,String type){
        //1. 检查 worker 是否存在
        if(!workerRepository.existsById(workerId)){
            return "worker does not exist";
        }
        //2. 检查余额是否足够
        Worker worker=workerRepository.findById(workerId.intValue());
        if(worker.getBalance()<value){
            return "balance is not enough";
        }
        //3. 记录提现数据并存回
        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        int NOT_FINISHED=0;
        WithdrawalInformation info=new WithdrawalInformation(0,workerId,currentTime,value,type,NOT_FINISHED);
        withdrawalInformationRepository.saveAndFlush(info);
        //4. 修改 worker 的 balance 字段并存回
        worker.setBalance(worker.getBalance()-value);
        workerRepository.saveAndFlush(worker);
        return "succeed";
    }

    @Override
    public List<WithdrawalInformation> findWithdrawalInformationByWorkerId(Integer workerId){
        //1. 检查 worker 是否存在
        if(!workerRepository.existsById(workerId)){
            return new ArrayList<>();
        }
        //2. 查找提现数据并返回
        return withdrawalInformationRepository.findByWorkerId(workerId);
    }
}
