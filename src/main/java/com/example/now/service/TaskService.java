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

    String addTask(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age,IdStore taskId,Integer typeOfQuestion,Integer numberOfQuestions,Integer allNumber);

    String updateTask(int taskId,String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age);

    String deleteTask(int id);

    String createTaskResource(int taskId, String description, String options, MultipartFile file);

    String createTaskResource(int taskId, String description, MultipartFile file);

    String createTaskResource(int taskId, String description, String options);

    String createTaskResource(int taskId, String url);

    String readTaskResource(int taskId);

    //更新 distributedNumber 字段
    //并检测该 task 是否分配完成并修改 isDistributed 字段
    String updateDistributedNumber(int taskId,Integer beginAt,Integer endAt);

    //将完成的所有子任务（普通类型）合并为两份答案，存放于 answer 字段中
    String mergeOrdinarySubtask();

    //将审核子任务的答案添加在 answer 字段中
    String mergeAllSubtask();


}
