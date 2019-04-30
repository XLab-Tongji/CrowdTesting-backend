package com.example.now.service;

import com.example.now.entity.Subtask;
import com.example.now.entity.IdStore;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

public interface SubtaskService {
    List<Subtask> findAllSubtask();

    Subtask findSubtaskById(int id);

    List<Subtask> findSubtaskByWorkerId(int id);

    List<Subtask> findSubtaskByTaskId(int id);

    String addSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, IdStore id, int type_of_subtask);

    String updateSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int id);

    String deleteSubtask(int id);
}