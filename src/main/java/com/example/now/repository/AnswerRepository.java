package com.example.now.repository;

import com.example.now.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Answer repository class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    /**
     * find answer by answer id
     *
     * @param id answer id
     * @return Answer
     */
    Answer findById(int id);

    /**
     * find answer by subtask id
     *
     * @param id subtask id
     * @return Answer
     */
    Answer findBySubtaskId(int id);

    /**
     * check whether answer exists by subtask id
     *
     * @param id subtask id
     * @return boolean
     */
    boolean existsBySubtaskId(int id);

    /**
     * find answer by worker id
     *
     * @param id worker id
     * @return List<Answer>
     */
    List<Answer> findByWorkerId(int id);

    /**
     * find answer by task id
     *
     * @param id task id
     * @return List<Answer>
     */
    List<Answer> findByTaskId(int id);

    /**
     * find answer by task id order by the property "beginAt"
     *
     * @param id task id
     * @return List<Answer>
     */
    public List<Answer> findByTaskIdOrderByBeginAt(int id);

    /**
     * delete answer by answer id
     *
     * @param id answer id
     */
    void deleteById(int id);

    /**
     * delete answer by subtask id
     *
     * @param id subtask id
     * @return boolean
     */
    boolean deleteBySubtaskId(int id);
}
