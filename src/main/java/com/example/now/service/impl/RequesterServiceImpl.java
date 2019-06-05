package com.example.now.service.impl;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;
import com.example.now.entity.WithdrawalInformation;
import com.example.now.entity.Worker;
import com.example.now.repository.WithdrawalInformationRepository;
import com.example.now.service.RequesterService;
import com.example.now.util.TokenUtils;
import com.example.now.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Requester service implementation class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Service
public class RequesterServiceImpl implements RequesterService {
    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private WithdrawalInformationRepository withdrawalInformationRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Requester findRequesterById(int id) {
        return requesterRepository.findById(id);
    }

    @Override
    public Requester findRequesterByUsername(String username) {
        return requesterRepository.findByEMail(username);
    }

    @Override
    public String addRequester(String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, int age, IdStore id) {
        if (username == null || name == null) {
            return "username or name is empty";
        }
        Requester requester = new Requester(username,name,teleNumber,eMail,institutionName,address,payMethod,gender,age);
        Requester temp=requesterRepository.saveAndFlush(requester);
        id.setId(temp.getRequesterId());
        return "succeed";
    }

    @Override
    public String updateRequester(int requesterId,String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, int age) {
        Requester requester=requesterRepository.findById(requesterId);
        requester.setAll(username, name,teleNumber,eMail,institutionName,address,payMethod,gender,age);
        requesterRepository.saveAndFlush(requester);
        return "succeed";
    }

    @Override
    public String deleteRequester(int id) {
        requesterRepository.deleteById(id);
        requesterRepository.flush();
        return "succeed";
    }

    @Override
    public String withdrawMoneyAsRequester(Integer requesterId,Float value,String type){
        //1. 检查 requester 是否存在
        if(!requesterRepository.existsById(requesterId)){
            return "requester does not exist";
        }
        //2. 检查余额是否足够
        Requester requester=requesterRepository.findById(requesterId.intValue());
        if(requester.getBalance()<value){
            return "balance is not enough";
        }
        //3. 记录提现数据并存回
        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        int NOT_FINISHED=0;
        WithdrawalInformation info=new WithdrawalInformation(requesterId,0,currentTime,value,type,NOT_FINISHED);
        withdrawalInformationRepository.saveAndFlush(info);
        //4. 修改 requester 的 balance 字段并存回
        requester.setBalance(requester.getBalance()-value);
        requesterRepository.saveAndFlush(requester);
        return "succeed";
    }

    @Override
    public List<WithdrawalInformation> findWithdrawalInformationByRequesterId(Integer requesterId){
        //1. 检查 requester 是否存在
        if(!requesterRepository.existsById(requesterId)){
            return new ArrayList<>();
        }
        //2. 查找提现数据并返回
        return withdrawalInformationRepository.findByRequesterId(requesterId);
    }
}
