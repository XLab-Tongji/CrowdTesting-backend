package com.example.now.service;
import com.example.now.entity.Task;
import com.example.now.service.TaskService;
import com.example.now.repository.TaskRepository;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<Task> findAllTask(){
        List<Task> temp=taskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }
    @Override
    public Task findTaskById(int id){
        return taskRepository.findById(id);
    }
    @Override
    public List<Task> findTaskByName(String name){
        List<Task> temp=taskRepository.findByName(name);
        Collections.reverse(temp);
        return temp;
    }
    @Override
    public List<Task> findTaskByRequesterId(int id){
        List<Task> temp=taskRepository.findByRequesterid(id);
        Collections.reverse(temp);
        return temp;
    }
    @Override
    public List<Task> findTaskByReward(int lowest,int highest){
        return taskRepository.findByRewardBetween(lowest,highest);
    }
    @Override
    public String addTask(String name,String description,int requester_id,int reward){
        if(name==null||description==null)
            return "inputs are not enough";
        Task temp=new Task(name,description,requester_id,reward);
        taskRepository.saveAndFlush(temp);
        return "succeed";
    }
    @Override
    public String updateTask(Task task){
        taskRepository.saveAndFlush(task);
        return "succeed";
    }
    @Override
    public String deleteTask(int id){
        taskRepository.deleteById(id);
        return "succeed";
    }
}
