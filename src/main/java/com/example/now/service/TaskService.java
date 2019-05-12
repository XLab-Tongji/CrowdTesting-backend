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

    public String addTask(String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age, IdStore taskId);

    public String updateTask(int taskId, String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age);

    public String updateTaskDirectly(Task task);

    String deleteTask(int id);

    String createTaskResource(int taskId, String description, String options, MultipartFile file);

    String createTaskResource(int taskId, String description, MultipartFile file);

    String createTaskResource(int taskId, String description, String options);

    String createTaskResource(int taskId, String url);
  
    String readTaskResource(int taskId);

    //更新 distributedNumber 字段
    //并检测该 task 是否分配完成并修改 isDistributed 字段
    String updateDistributedNumber(int taskId,Integer beginAt,Integer endAt);

    //（已弃用）将完成的所有子任务（普通类型）合并为两份答案，存放于 answer 字段中
    String mergeOrdinarySubtask();

    //（已弃用）将审核子任务的答案添加在 answer 字段中
    String mergeAllSubtask();

    //更新 answer 字段（每次提交答案都会执行）
    Boolean updateAnswer(int taskId,String answer,int numberOfTask);

    //检测 task 所有普通任务是否完成，通过 task 的 answer 字段来判断
    Boolean isFinishedForSimpleSubtasks(int taskId);

    //检测 task 所有任务（普通和审核）是否完成，通过 task 的 answer 字段来判断
    Boolean isFinishedForAllSubtasks(int taskId);

    //删除已经过期子任务的答案
    Boolean deleteExpiredAnswer(int taskId,int number_of_task,int beginAt,int endAt);

    //遍历 task 表的 answer 字段，更新 isFinished 字段
    //若已完成，则计算 worker 的正确题数和做题总数并更新
    //TODO ：给用户加钱
    void updateStatus();

    //计算 worker 的正确题数、做题总数和余额并更新（针对 ver1,ver4 类型题）
    void calculateCorrectNumberAndBalanceForChoice(int taskId);

    //计算 worker 的正确题数、做题总数和余额并更新（针对 ver2,ver3 类型题）
    void calculateCorrectNumberAndBalanceForImage(int taskId);

    //requester 获取图形标记类型（ver2,ver3）题目的答案，用于判断正误。number 为获取的题数
    String getJudgedAnswer(int taskId,int number);

    //更新对应 task（类型为ver2,ver3) 的 answer,若判断正误任务已完成，则更新对应 worker 的正确数，答题总数
    String judgeAnswer(int taskId,String answer);

}
