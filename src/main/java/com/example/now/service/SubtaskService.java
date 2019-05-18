package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;

import java.sql.Timestamp;
import java.util.List;


/**
 * Subtask service class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface SubtaskService {
    /**
     * find all subtask
     *
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findAllSubtask();

    /**
     * find subtask by subtask id
     *
     * @param id subtask id
     * @return 返回值说明：subtask
     */
    Subtask findSubtaskById(int id);

    /**
     * find subtask by worker id
     *
     * @param id worker id
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findSubtaskByWorkerId(int id);

    /**
     * find subtask by task id
     *
     * @param id task id
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findSubtaskByTaskId(int id);

    /**
     * create new subtask
     *
     * @param begin begin position
     * @param end end position
     * @param createdTime created time
     * @param deadline deadline
     * @param updatedTime updated time
     * @param isFinished whether is finished or not
     * @param type type
     * @param workerId worker id
     * @param taskId task id
     * @param numberOfTask the number of questions in task
     * @param id get id of the new subtask
     * @param nowBegin the position of the questions start
     * @return 返回值说明：成功或失败信息
     */
    String addSubtask(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, IdStore id, int nowBegin);

    /**
     * find subtask resource(questions) by subtask id
     *
     * @param subtaskId subtask id
     * @return 返回值说明：成功或失败信息
     */
    String readSubtaskResource(int subtaskId);

    /**
     * update subtask information
     *
     * @param begin begin position
     * @param end end position
     * @param createdTime created time
     * @param deadline deadline
     * @param updatedTime updated time
     * @param isFinished whether is finished or not
     * @param type type
     * @param workerId worker id
     * @param taskId task id
     * @param numberOfTask the number of questions in task
     * @param id get id of the new subtask
     * @param nowBegin the position of the questions start
     * @return String
     */
    String updateSubtask(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, int nowBegin, int id);

    /**
     * delete subtask by subtask id
     *
     * @param id subtask id
     * @return 返回值说明：成功或失败信息
     */
    String deleteSubtask(int id);

    /**
     * update information whether subtask is finished or not
     * 检测子任务是否过期
     * 遍历 isFinished 字段为 0 的子任务，若当前时间超过 deadline 则置 isFinished 为 -1，同时更新此 worker 的过期任务数
     *
     */
    void updateIsFinished();
}