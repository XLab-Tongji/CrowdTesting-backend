package com.example.now.repository;

import com.example.now.entity.TaskData;
import com.example.now.entity.TaskDataPrime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDataRepository extends JpaRepository<TaskData, TaskDataPrime> {
    public List<TaskData> findByPrimeId(int id);
}
