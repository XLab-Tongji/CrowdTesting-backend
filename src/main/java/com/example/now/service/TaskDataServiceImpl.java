package com.example.now.service;
import com.example.now.entity.TaskData;
import com.example.now.service.TaskDataService;
import com.example.now.repository.TaskDataRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TaskDataServiceImpl implements TaskDataService{
    @Autowired
    private TaskDataRepository taskDataRepository;
    @Override
    public List<TaskData> findTaskData(int id){
        List<TaskData> temp=taskDataRepository.findByPrimeId(id);
        Collections.reverse(temp);
        return temp;
    }
    @Override
    public String addTaskData(Integer id,Integer worker_num,Float progress_rate){
        if(id==null||worker_num==null||progress_rate==null){
            return "inputs are not enough";
        }
        TaskData temp=new TaskData(id,worker_num,progress_rate);
        taskDataRepository.saveAndFlush(temp);
        return "succeed";
    }
}
