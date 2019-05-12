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

    String addSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, IdStore id, int now_begin);

    String readSubtaskResource(int subtaskId);

    String updateSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, int now_begin, int id);

    String deleteSubtask(int id);

    //检测子任务是否过期
    //遍历 is_finished 字段为 0 的子任务，若当前时间超过 deadline 则置 isFinished 为 -1，同时更新此 worker 的过期任务数
    void updateIsFinished();
}