package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;
import com.example.now.service.SubtaskService;
import com.example.now.repository.SubtaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubtaskServiceImpl implements SubtaskService{
    @Autowired
    private SubtaskRepository subtaskRepository;

    @Override
    public List<Subtask> findAllSubtask() {
        List<Subtask> temp = subtaskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Subtask findSubtaskById(int id) {
        return subtaskRepository.findById(id);
    }

    @Override
    public List<Subtask> findSubtaskByWorkerId(int id) {
        List<Subtask> temp = subtaskRepository.findByWorkerId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Subtask> findSubtaskByTaskId(int id){
        List<Subtask> temp = subtaskRepository.findByTaskId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public String addSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, IdStore id, int now_begin) {
        Subtask temp = new Subtask(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task, now_begin);
        Subtask result=subtaskRepository.saveAndFlush(temp);
        id.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, int now_begin, int id) {
        Subtask new_Subtask=subtaskRepository.findById(id);
        new_Subtask.setAll(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task,now_begin);
        subtaskRepository.saveAndFlush(new_Subtask);
        return "succeed";
    }

    @Override
    public String deleteSubtask(int id) {
        subtaskRepository.deleteById(id);
        return "succeed";
    }
}