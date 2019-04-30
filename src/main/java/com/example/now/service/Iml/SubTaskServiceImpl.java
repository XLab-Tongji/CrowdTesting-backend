package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.SubTask;
import com.example.now.service.SubTaskService;
import com.example.now.repository.SubTaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubTaskServiceImpl  implements SubTaskService{
    @Autowired
    private SubTaskRepository subTaskRepository;

    @Override
    public List<SubTask> findAllSubTask() {
        List<SubTask> temp = subTaskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public SubTask findSubTaskById(int id) {
        return subTaskRepository.findById(id);
    }

    @Override
    public List<SubTask> findSubTaskByWorkerId(int id) {
        List<SubTask> temp = subTaskRepository.findByWorkerId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<SubTask> findSubTaskByTaskId(int id){
        List<SubTask> temp = subTaskRepository.findByTaskId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public String addSubTask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, IdStore id) {
        SubTask temp = new SubTask(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task);
        SubTask result=subTaskRepository.saveAndFlush(temp);
        id.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateSubTask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, int id) {
        SubTask new_SubTask=subTaskRepository.findById(id);
        new_SubTask.setAll(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task);
        subTaskRepository.saveAndFlush(new_SubTask);
        return "succeed";
    }

    @Override
    public String deleteSubTask(int id) {
        subTaskRepository.deleteById(id);
        return "succeed";
    }
}
