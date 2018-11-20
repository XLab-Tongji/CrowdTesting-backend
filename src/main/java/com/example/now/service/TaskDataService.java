package com.example.now.service;

import com.example.now.entity.TaskData;

import java.util.List;

public interface TaskDataService {
    List<TaskData> findTaskData(int id);

    String addTaskData(Integer id, Integer worker_num, Float progress_rate);
}
