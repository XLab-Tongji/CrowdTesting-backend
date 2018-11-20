package com.example.now.service;

import com.example.now.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAllTask();

    Task findTaskById(int id);

    List<Task> findTaskByName(String name);

    List<Task> findTaskByRequesterId(int id);

    List<Task> findTaskByReward(int lowest, int highest);

    String addTask(String name, String description, int requester_id, int reward);

    String updateTask(Task task);

    String deleteTask(int id);
}
