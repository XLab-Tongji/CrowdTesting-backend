package com.example.now.repository;

import com.example.now.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findByName(String name);

    public Task findById(int id);

    public List<Task> findByRequesterid(int id);

    public List<Task> findByRewardBetween(int least, int most);

    public List<Task> findByIsDistributedAndIsFinished(int isDistributed,int isFinished);

    public List<Task> findByIsFinished(int isFinished);

    public void deleteById(int id);

}
