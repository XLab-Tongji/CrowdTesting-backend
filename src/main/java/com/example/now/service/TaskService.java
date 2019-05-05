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

    public String addTask(String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age, IdStore taskId,Integer allNumber);

    public String updateTask(int taskId, String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age);

    String deleteTask(int id);

    String createTaskResource(int taskId, String description, String options, MultipartFile file);

    String createTaskResource(int taskId, String description, MultipartFile file);

    String createTaskResource(int taskId, String description, String options);

    String createTaskResource(int taskId, String url);
  
    String readTaskResource(int taskId, int workerId);

    //更新 distributedNumber 字段
    //并检测该 task 是否分配完成并修改 isDistributed 字段
    String updateDistributedNumber(int taskId,Integer beginAt,Integer endAt);

    //（已弃用）将完成的所有子任务（普通类型）合并为两份答案，存放于 answer 字段中
    String mergeOrdinarySubtask();

    //（已弃用）将审核子任务的答案添加在 answer 字段中
    String mergeAllSubtask();

    //更新 answer 字段（每次提交答案都会执行）
    Boolean updateAnswer(int taskId,String answer,int numberOfTask);

    //TODO：检测 task 所有普通任务是否完成
    Boolean isFinishedForSimpleSubtasks(int taskId);

    //TODO: 检测 task 所有任务（普通和审核）是否完成
    Boolean isFinishedForAllSubtasks(int taskId);

    //TODO: 遍历 task 表的 answer 字段，更新 isFinished 字段
    //若已完成，则执行计算正确率函数
    void updateStatus();
}
