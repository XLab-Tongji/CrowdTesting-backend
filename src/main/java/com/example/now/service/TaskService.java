package com.example.now.service;

import com.example.now.entity.Task;
import com.example.now.entity.IdStore;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;


/**
 * Task service class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface TaskService {
    /**
     * find all task
     *
     * @return 返回值说明：task列表
     */
    List<Task> findAllTask();

    /**
     * find task by id
     *
     * @param id task id
     * @return 返回值说明：task
     */
    Task findTaskById(int id);

    /**
     * find task by name
     *
     * @param name name
     * @return 返回值说明：task列表
     */
    List<Task> findTaskByName(String name);

    /**
     * find task by requester id
     *
     * @param id requester id
     * @return 返回值说明：task列表
     */
    List<Task> findTaskByRequesterId(int id);

    /**
     * find task by reward between least money and most money
     *
     * @param lowest least money
     * @param highest most money
     * @return 返回值说明：task列表
     */
    List<Task> findTaskByReward(int lowest, int highest);

    /**
     * create new task
     *
     * @param name name
     * @param description description
     * @param reward reward
     * @param status status
     * @param requesterId requester id
     * @param type type
     * @param restrictions restrictions
     * @param startTime startTime
     * @param endTime endTime
     * @param population the number of results the requester wants
     * @param level level
     * @param timeLimitation time limitation
     * @param payTime the time of paying money after finishing the task
     * @param area area
     * @param usage the usage of the result
     * @param minAge min age
     * @param maxAge max age
     * @param taskId get id of the new task
     * @return 返回值说明：成功或失败信息
     */
    String addTask(String name, String description, Float reward, int status, Integer requesterId, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge, IdStore taskId);

    /**
     * update task information
     *
     * @param taskId task id
     * @param name name
     * @param description description
     * @param reward reward
     * @param status status
     * @param requesterId requester id
     * @param type type
     * @param restrictions restrictions
     * @param startTime startTime
     * @param endTime endTime
     * @param population the number of results the requester wants
     * @param level level
     * @param timeLimitation time limitation
     * @param payTime the time of paying money after finishing the task
     * @param area area
     * @param usage the usage of the result
     * @param minAge min age
     * @param maxAge max age
     * @return 返回值说明：成功或失败信息
     */
    String updateTask(int taskId, String name, String description, Float reward, int status, Integer requesterId, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge);

    /**
     * update task directly
     *
     * @param task task
     * @return 返回值说明：成功或失败信息
     */
    String updateTaskDirectly(Task task);

    /**
     * delete task by task id
     *
     * @param id task id
     * @return 返回值说明：成功或失败信息
     */
    String deleteTask(int id);

    /**
     * create task resource
     *
     * @param taskId task id
     * @param description description
     * @param options options
     * @param file url file
     * @return 返回值说明：成功或失败信息
     */
    String createTaskResource(int taskId, String description, String options, MultipartFile file);

    /**
     * create task resource
     *
     * @param taskId task id
     * @param description description
     * @param file url file
     * @return 返回值说明：成功或失败信息
     */
    String createTaskResource(int taskId, String description, MultipartFile file);

    /**
     * create task resource
     *
     * @param taskId task id
     * @param description description
     * @param options options
     * @return 返回值说明：成功或失败信息
     */
    String createTaskResource(int taskId, String description, String options);

    /**
     * create task resource
     *
     * @param taskId task id
     * @param url questionnaire url
     * @return 返回值说明：成功或失败信息
     */
    String createTaskResource(int taskId, String url);

    /**
     * read task resource
     *
     * @param taskId task id
     * @return 返回值说明：成功或失败信息
     */
    String readTaskResource(int taskId);

    /**
     * update distributed number
     * 更新 distributedNumber 字段
     * 并检测该 task 是否分配完成并修改 isDistributed 字段
     *
     * @param taskId task id
     * @param beginAt begin position
     * @param endAt end position
     * @return 返回值说明：成功或失败信息
     */
    String updateDistributedNumber(int taskId,Integer beginAt,Integer endAt);

    /**
     * merge ordinary subtask
     * （已弃用）将完成的所有子任务（普通类型）合并为两份答案，存放于 answer 字段中
     *
     * @return 返回值说明：成功或失败信息
     */
    String mergeOrdinarySubtask();

    /**
     * merge all subtask
     * （已弃用）将审核子任务的答案添加在 answer 字段中
     *
     * @return 返回值说明：成功或失败信息
     */
    String mergeAllSubtask();

    /**
     * update answer
     * 更新 answer 字段（每次提交答案都会执行）
     *
     * @param taskId task id
     * @param answer answer
     * @param numberOfTask number of task
     * @return 返回值说明：是否更新成功
     */
    Boolean updateAnswer(int taskId,String answer,int numberOfTask);

    /**
     * check whether the ordinary answer has been finished
     * 检测 task 所有普通任务是否完成，通过 task 的 answer 字段来判断
     *
     * @param taskId task id
     * @return 返回值说明：是否完成
     */
    Boolean isFinishedForSimpleSubtasks(int taskId);

    /**
     * check whether all answers have been finished
     * 检测 task 所有任务（普通和审核）是否完成，通过 task 的 answer 字段来判断
     *
     * @param taskId task id
     * @return 返回值说明：是否完成
     */
    Boolean isFinishedForAllSubtasks(int taskId);

    /**
     * delete expired answer
     * 删除已经过期子任务的答案
     *
     * @param taskId task id
     * @param numberOfTask number of task
     * @param beginAt begin position
     * @param endAt end position
     * @return 返回值说明：是否删除成功
     */
    Boolean deleteExpiredAnswer(int taskId,int numberOfTask,int beginAt,int endAt);

    /**
     * update task status
     * 遍历 task 表的 answer 字段，更新 isFinished 字段
     * 若已完成，则计算 worker 的正确题数和做题总数和余额并更新
     *
     *
     */
    void updateStatus();

    /**
     * calculate the correct number of answers and update balance
     * 计算 worker 的正确题数、做题总数和余额并更新（针对 ver1,ver4 类型题）
     *
     * @param taskId task id
     */
    void calculateCorrectNumberAndBalanceForChoice(int taskId);

    /**
     * calculate the correct number of answers and update balance
     * 计算 worker 的正确题数、做题总数和余额并更新（针对 ver2,ver3 类型题）
     *
     * @param taskId task id
     */
    void calculateCorrectNumberAndBalanceForImage(int taskId);

    /**
     * get judge answer
     *
     * @param taskId task id
     * @param number number
     * @return 返回值说明：成功或失败信息
     */
    String getJudgedAnswer(int taskId,int number);

    /**
     * get judge answer
     * 更新对应 task（类型为ver2,ver3) 的 answer,若判断正误任务已完成，则更新对应 worker 的正确数，答题总数
     *
     * @param taskId task id
     * @param answer answer
     * @return String
     */
    String judgeAnswer(int taskId,String answer);

}
