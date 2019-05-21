package com.example.now.service.impl;

import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import com.example.now.entity.Requester;
import com.example.now.repository.RequesterRepository;
import com.example.now.entity.TransactionInformation;
import com.example.now.repository.TransactionInformationRepository;
import com.example.now.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Admin service implementation class
 *
 * @author jjc
 * @date 2019/05/17
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RequesterRepository requesterRepository;

    @Autowired
    private TransactionInformationRepository transactionInformationRepository;

    /**
     * 代表任务已审核
     */
    private int reviewed=1;

    @Override
    public boolean reviewTask(int id){
        Task task=taskRepository.findById(id);
        task.setReviewed(reviewed);
        String type = "调查问卷";
        if(!type.equals(task.getType())) {
            Requester requester = requesterRepository.findById(task.getRequesterId()).get();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            TransactionInformation transactionInformation = new TransactionInformation(task.getRequesterId(),0,task.getId(), now, (float) (task.getNumberOfQuestions() * task.getReward() * 1.2));
            requester.setBalance(requester.getBalance() - (float) (task.getNumberOfQuestions() * task.getReward() * 1.2));
            transactionInformationRepository.saveAndFlush(transactionInformation);
        }
        else{
            Requester requester = requesterRepository.findById(task.getRequesterId()).get();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            TransactionInformation transactionInformation = new TransactionInformation(task.getRequesterId(),0,task.getId(), now, (float) (task.getPopulation() * task.getReward() * 1.2));
            requester.setBalance(requester.getBalance() - (float) (task.getPopulation() * task.getReward() * 1.2));
            transactionInformationRepository.saveAndFlush(transactionInformation);
        }
        taskRepository.saveAndFlush(task);
        return true;
    }
}
