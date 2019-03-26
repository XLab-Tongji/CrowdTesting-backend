package com.example.now.service;

import com.example.now.entity.MyTask;
import com.example.now.entity.Worker;
import com.example.now.entity.Task;

import java.util.List;

public interface PersonalTaskService {
    List<MyTask> findTaskByWorkerId(int id);

    List<Worker> findWorkerByTaskId(int id);

    String addPersonalTask(Integer workerid, Integer taskid);

    String deletePersonalTask(Integer workerid, Integer taskid);

    String finishOne(int workerId,int taskId);
}
