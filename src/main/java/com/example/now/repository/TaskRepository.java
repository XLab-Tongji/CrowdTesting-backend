package com.example.now.repository;

import com.example.now.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * @return List<Task>
     */
    List<Task> findByName(String name);

    /**
     * find task by task id
     *
     * @param id task id
     * @return Task
     */
    Task findById(int id);

    /**
     * find task by requester id
     *
     * @param id requester id
     * @return List<Task>
     */
    List<Task> findByRequesterId(int id);

    /**
     * find task by reward between least money and most money
     *
     * @param least least money
     * @param most most money
     * @return List<Task>
     */
    List<Task> findByRewardBetween(int least, int most);

    /**
     * find task by isDistributed and isFinished
     *
     * @param isDistributed isDistributed
     * @param isFinished isFinished
     * @return List<Task>
     */
    List<Task> findByIsDistributedAndIsFinished(int isDistributed,int isFinished);

    /**
     * find task by status
     *
     * @param status status
     * @return List<Task>
     */
    List<Task> findByStatus(int status);

    /**
     * delete task by task id
     *
     * @param id task id
     */
    void deleteById(int id);
}
