package com.example.now.repository;

import com.example.now.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
    public Subtask findById(int id);

    public List<Subtask> findByWorkerId(int id);

    public List<Subtask> findByTaskId(int id);

    public List<Subtask> findByTaskIdAndType(int taskid,int type);

    public void deleteById(int id);
}