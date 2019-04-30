package com.example.now.repository;

import com.example.now.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    public Answer findById(int id);

    public List<Answer> findByWorkerId(int id);

    public List<Answer> findByTaskId(int id);

    public List<Answer> findByTaskIdOrderByBeginAt(int id);

    public void deleteById(int id);
}
