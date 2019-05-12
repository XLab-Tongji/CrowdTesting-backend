package com.example.now.service.Iml;

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

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RequesterRepository requesterRepository;

    private int UNREVIEWED=0;//代表任务未审核
    private int REVIEWED=1;//代表任务已审核
    @Override
    public boolean reviewTask(int id){
        Task task=taskRepository.findById(id);
        task.setReviewed(REVIEWED);
        if(task.getType().equals("调查问卷")) {
            Requester requester = requesterRepository.findById(task.getRequesterid()).get();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            TransactionInformation transactionInformation = new TransactionInformation(task.getRequesterid(), task.getId(), now, (float) (task.getNumberOfQuestions() * task.getReward() * 0.2));
        }
        taskRepository.saveAndFlush(task);
        return true;
    }
}
