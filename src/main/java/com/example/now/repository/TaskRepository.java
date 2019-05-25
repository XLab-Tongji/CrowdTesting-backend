package com.example.now.repository;

import com.example.now.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Task repository class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
    /**
     * find task by task name
     *
     * @param name task name
     * @return 返回值说明：task列表
     */
    List<Task> findByName(String name);

    /**
     * find task by task id
     *
     * @param id task id
     * @return 返回值说明：task
     */
    Task findById(int id);

    /**
     * find task by requester id
     *
     * @param id requester id
     * @return 返回值说明：task列表
     */
    List<Task> findByRequesterId(int id);

    /**
     * find task by reward between least money and most money
     *
     * @param least least money
     * @param most most money
     * @return 返回值说明：task列表
     */
    List<Task> findByRewardBetween(int least, int most);

    /**
     * find task by isDistributed and isFinished
     *
     * @param isDistributed isDistributed
     * @param isFinished isFinished
     * @return 返回值说明：task列表
     */
    List<Task> findByIsDistributedAndIsFinished(int isDistributed,int isFinished);

    /**
     * find task by status
     *
     * @param status status
     * @return 返回值说明：task列表
     */
    List<Task> findByStatus(int status);

    /**
     * delete task by task id
     *
     * @param id task id
     */
    void deleteById(int id);
}
