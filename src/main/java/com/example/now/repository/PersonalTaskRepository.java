package com.example.now.repository;

import com.example.now.entity.PersonalTask;
import com.example.now.entity.PersonalTaskKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalTaskRepository extends JpaRepository<PersonalTask, PersonalTaskKey> {
    public List<PersonalTask> findByIdWorkerId(int id);

    public List<PersonalTask> findByIdTaskId(int id);

    public PersonalTask findByIdWorkerIdAndIdTaskId(int workerId, int taskId);
}
