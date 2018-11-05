package com.example.now.controller;
import com.example.now.repository.Task_dataRepository;
import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private Task_dataRepository task_dataRepository;
    @RequestMapping(value = "/task/find-all",method = RequestMethod.GET)
    public List<Task> taskFindAll(){
        List<Task> temp=taskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }
    @RequestMapping(value = "/task/find-by-id",method = RequestMethod.GET)
    public Task taskFindById(int id){
        return taskRepository.findById(id);
    }
    @RequestMapping(value = "/task/find-by-name",method = RequestMethod.GET)
    public List<Task> taskFindByName(String name){
        List<Task> temp=taskRepository.findByName(name);
        Collections.reverse(temp);
        return temp;
    }
    @RequestMapping(value = "/task/find-by-requester-id",method = RequestMethod.GET)
    public List<Task> taskFindByRequesterId(int requesterid){
        List<Task> temp=taskRepository.findByRequesterid(requesterid);
        Collections.reverse(temp);
        return temp;
    }
    @RequestMapping(value = "/task/find-by-reward",method = RequestMethod.GET)
    public List<Task> taskFindByReward(int lowest,int highest){
        return taskRepository.findByRewardBetween(lowest,highest);
    }
    @RequestMapping(value = "/task/add",method = RequestMethod.POST)
    public void taskAdd(String name,String description,int requester_id,int reward){
        Task temp=new Task(name,description,requester_id,reward);
        taskRepository.saveAndFlush(temp);
    }
    @RequestMapping(value = "/task/update",method = RequestMethod.PUT)
    public void taskUpdate(Task task){
        taskRepository.saveAndFlush(task);
    }
    @RequestMapping(value = "/task/delete",method = RequestMethod.DELETE)
    public void taskDelete(int id){
        taskRepository.deleteById(id);
        taskRepository.flush();
    }
}
