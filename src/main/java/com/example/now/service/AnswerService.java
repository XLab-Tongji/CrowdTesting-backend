package com.example.now.service;

import com.example.now.entity.Answer;
import com.example.now.entity.IdStore;

import java.sql.Timestamp;
import java.util.List;


/**
 * Answer service class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface AnswerService {
    /**
     * find all answer
     *
     * @return 返回值说明：answer列表
     */
    List<Answer> findAllAnswer();

    /**
     * find answer by answer id
     *
     * @param id answer id
     * @return 返回值说明：answer
     */
    Answer findAnswerById(int id);

    /**
     * find answer by worker id
     *
     * @param id worker id
     * @return 返回值说明：answer列表
     */
    List<Answer> findAnswerByWorkerId(int id);

    /**
     * find answer by task id
     *
     * @param id task id
     * @return 返回值说明：answer列表
     */
    List<Answer> findAnswerByTaskId(int id);

    /**
     * create new answer
     *
     * @param workerId worker id
     * @param taskId task id
     * @param answer answer
     * @param answerTime answerTime
     * @param id get id of the new answer
     * @param subtaskId subtask id
     * @param beginAt beginAt
     * @param endAt endAt
     * @return 返回值说明：成功或失败信息
     */
    String addAnswer(Integer workerId, int taskId, String answer, Timestamp answerTime, IdStore id,int subtaskId,Integer beginAt,Integer endAt);

    /**
     * update new answer
     *
     * @param workerId worker id
     * @param taskId task id
     * @param answer answer
     * @param answerTime answerTime
     * @param id answer id
     * @param subtaskId subtask id
     * @param beginAt beginAt
     * @param endAt endAt
     * @return 返回值说明：成功或失败信息
     */
    String updateAnswer(Integer workerId, int taskId, String answer, Timestamp answerTime, Integer id,int subtaskId,Integer beginAt,Integer endAt);

    /**
     * delete answer by answer id
     *
     * @param id answer id
     * @return String
     */
    String deleteAnswer(int id);

    /**
     * delete answer by subtask id
     *
     * @param subtaskId subtaskId id
     * @return 返回值说明：成功或失败信息
     */
    String deleteAnswerBySubtaskId(int subtaskId);

    /**
     * check whether the subtask is finished or not by answerId
     * 检测该子任务是否完成，参数为 answerId
     *
     * @param id answer id
     * @return 返回值说明：是否已完成
     */
    Boolean isFinished(int id);

    /**
     * check whether the subtask is finished or not by answer
     * 检测该子任务是否完成，参数为 answer
     *
     * @param answer answer
     * @return boolean
     */
    Boolean isFinished(Answer answer);

    /**
     * find answer by subtaskId and taskId
     * checker 获取答案
     *
     * @param subtaskId subtask id
     * @param taskId task id
     * @return 返回值说明：成功或失败信息
     */
    String findAnswerBySubtaskId(int subtaskId,int taskId);
}
