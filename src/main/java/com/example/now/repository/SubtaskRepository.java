package com.example.now.repository;

import com.example.now.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Subtask repository class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
    /**
     * find subtask by subtask id
     *
     * @param id subtask id
     * @return 返回值说明：subtask
     */
    Subtask findById(int id);

    /**
     * find subtask by worker id
     *
     * @param id worker id
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findByWorkerId(int id);

    /**
     * find subtask by task id
     *
     * @param id task id
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findByTaskId(int id);

    /**
     * find subtask by task id and type
     *
     * @param taskId task id
     * @param type type
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findByTaskIdAndType(int taskId,int type);

    /**
     * delete subtask by subtask id
     *
     * @param id subtask id
     */
    void deleteById(int id);

    /**
     * find subtask by isFinished
     *
     * @param isFinished isFinished
     * @return 返回值说明：subtask列表
     */
    List<Subtask> findByIsFinished(int isFinished);
}