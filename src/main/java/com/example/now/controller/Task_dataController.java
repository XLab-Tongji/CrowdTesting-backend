package com.example.now.controller;
import com.example.now.entity.Task_data;
import com.example.now.repository.Task_dataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Collections;
@RestController
public class Task_dataController {
    @Autowired
    private Task_dataRepository task_dataRepository;
    @RequestMapping(value = "/task_data/find",method = RequestMethod.GET)
    public List<Task_data> taskDataFind(int id){
        List<Task_data> temp=task_dataRepository.findByPrimeId(id);
        Collections.reverse(temp);
        return temp;
    }
    @RequestMapping(value = "/task_data/add",method = RequestMethod.POST)
    public void taskDataAdd(int id,int worker_num,float progress_rate){
        Task_data temp=new Task_data(id,worker_num,progress_rate);
        task_dataRepository.saveAndFlush(temp);
    }
}
