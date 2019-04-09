package com.example.now.service.Iml;

import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import com.example.now.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TaskRepository taskRepository;

    private int UNREVIEWED=0;//代表任务未审核
    private int REVIEWED=1;//代表任务已审核
    @Override
    public boolean reviewTask(int id){
        Task task=taskRepository.findById(id);
        task.setAll(task.getName(),task.getDescription(),task.getReward(),task.getStatus(),task.getId(),task.getType(),task.getRestrictions(),task.getStart_time(),task.getEnd_time(),task.getLevel(),task.getTime_limitation(),task.getPay_time(),task.getArea(),task.getUsage(),task.getMin_age(),task.getMax_age(),REVIEWED);
        taskRepository.saveAndFlush(task);
        return true;
    }
}
