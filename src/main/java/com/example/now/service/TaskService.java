package com.example.now.service;

import com.example.now.entity.Task;
import com.example.now.entity.IdStore;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {
    List<Task> findAllTask();

    Task findTaskById(int id);

    List<Task> findTaskByName(String name);

    List<Task> findTaskByRequesterId(int id);

    List<Task> findTaskByReward(int lowest, int highest);

    String addTask(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age,IdStore taskId);

    String updateTask(int taskId,String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age);

    String deleteTask(int id);

    String createTaskResource(int taskId, String description, String options, MultipartFile file);

    String createTaskResource(int taskId, String description, MultipartFile file);

    String createTaskResource(int taskId, String description, String options);

    String createTaskResource(int taskId, String url);

    String readTaskResource(int taskId);
}
