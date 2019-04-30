package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;

import java.util.List;

public interface SubtaskService {
    List<Subtask> findAllSubtask();

    Subtask findSubtaskById(int id);

    String addSubtask(Integer taskId, Integer workerId, Integer beginAt, Integer endAt, Integer typeOfSubtask, IdStore Id);
    //用于更新子任务的完成状态
    String updateSubtask(int subtaskId,Integer isFinished);

    String deleteSubtask(Integer subtaskId);

}
