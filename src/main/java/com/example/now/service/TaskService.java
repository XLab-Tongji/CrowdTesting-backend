package com.example.now.service;

import com.example.now.entity.Task;
import com.example.now.entity.IdStore;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {
    List<Task> findAllTask();

    Task findTaskById(int id);

    List<Task> findTaskByName(String name);

    List<Task> findTaskByRequesterId(int id);

    List<Task> findTaskByReward(int lowest, int highest);

    String addTask(String name, String description, Integer reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, int time_limitation, int pay_time,IdStore taskId);

    String updateTask(int taskId,String name, String description, Integer reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, int time_limitation, int pay_time);

    String deleteTask(int id);
}
