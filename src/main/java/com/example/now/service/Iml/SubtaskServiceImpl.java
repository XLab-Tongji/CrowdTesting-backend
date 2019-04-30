package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;
import com.example.now.repository.SubtaskRepository;
import com.example.now.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class SubtaskServiceImpl implements SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;
    @Override
    public List<Subtask> findAllSubtask(){
        List<Subtask> subtasks=subtaskRepository.findAll();
        Collections.reverse(subtasks);
        return subtasks;
    }

    @Override
    public Subtask findSubtaskById(int id){
        return subtaskRepository.findById(id);
    }

    @Override
    public String addSubtask(Integer taskId, Integer workerId, Integer beginAt, Integer endAt, Integer typeOfSubtask, IdStore Id){
        Subtask temp=new Subtask(taskId,workerId,beginAt,endAt,typeOfSubtask,0);
        Subtask subtask=subtaskRepository.saveAndFlush(temp);
        Id.setId(subtask.getSubtaskId());
        return "succeed";
    }

    @Override
    public String updateSubtask(int subtaskId,Integer isFinished){
        Subtask subtask=subtaskRepository.findById(subtaskId);
        subtask.setIsFinished(isFinished);
        subtaskRepository.saveAndFlush(subtask);
        return "succeed";
    }

    @Override
    public String deleteSubtask(Integer subtaskId){
        subtaskRepository.deleteById(subtaskId);
        subtaskRepository.flush();
        return "succeed";
    }
}
