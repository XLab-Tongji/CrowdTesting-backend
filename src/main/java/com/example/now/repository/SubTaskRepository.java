package com.example.now.repository;

import com.example.now.entity.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Integer> {
    public SubTask findById(int id);

    public List<SubTask> findByWorkerId(int id);

    public List<SubTask> findByTaskId(int id);

    public void deleteById(int id);
}
