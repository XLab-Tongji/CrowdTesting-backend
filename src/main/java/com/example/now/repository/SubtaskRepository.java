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
     * @return Subtask
     */
    Subtask findById(int id);

    /**
     * find subtask by worker id
     *
     * @param id worker id
     * @return List<Subtask>
     */
    List<Subtask> findByWorkerId(int id);

    /**
     * find subtask by task id
     *
     * @param id task id
     * @return List<Subtask>
     */
    List<Subtask> findByTaskId(int id);

    /**
     * find subtask by task id and type
     *
     * @param taskId task id
     * @param type type
     * @return List<Subtask>
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
     * @return List<Subtask>
     */
    List<Subtask> findByIsFinished(int isFinished);
}