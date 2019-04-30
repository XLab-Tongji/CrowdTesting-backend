package com.example.now.service;

import com.example.now.entity.SubTask;
import com.example.now.entity.IdStore;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

public interface SubTaskService {
    List<SubTask> findAllSubTask();

    SubTask findSubTaskById(int id);

    List<SubTask> findSubTaskByWorkerId(int id);

    List<SubTask> findSubTaskByTaskId(int id);

    String addSubTask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, IdStore id);

    String updateSubTask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, int id);

    String deleteSubTask(int id);
}
