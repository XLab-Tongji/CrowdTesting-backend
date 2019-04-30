package com.example.now.repository;

import com.example.now.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask,Integer> {
    public Subtask findById(int id);

    public List<Subtask> findByTaskId(int taskId);
}
