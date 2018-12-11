package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Task;
import com.example.now.service.TaskService;
import com.example.now.repository.TaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllTask() {
        List<Task> temp = taskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Task findTaskById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findTaskByName(String name) {
        List<Task> temp = taskRepository.findByName(name);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByRequesterId(int id) {
        List<Task> temp = taskRepository.findByRequesterid(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByReward(int lowest, int highest) {
        return taskRepository.findByRewardBetween(lowest, highest);
    }

    @Override
    public String addTask(String name, String description, Integer reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, int time_limitation, int pay_time, IdStore taskId) {
        if (name == null || description == null)
            return "inputs are not enough";
        Task temp = new Task(name, description,reward,status,requesterid,type,restrictions,start_time,end_time,level,time_limitation,pay_time);
        Task result=taskRepository.saveAndFlush(temp);
        taskId.setId(result.getTask_id());
        return "succeed";
    }

    @Override
    public String updateTask(int taskId,String name, String description, Integer reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, int time_limitation, int pay_time) {
        Task task=taskRepository.findById(taskId);
        task.setAll(name, description,reward,status,requesterid,type,restrictions,start_time,end_time,level,time_limitation,pay_time);
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String deleteTask(int id) {
        taskRepository.deleteById(id);
        return "succeed";
    }
}
