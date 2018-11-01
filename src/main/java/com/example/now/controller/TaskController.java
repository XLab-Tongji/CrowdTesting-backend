package com.example.now.controller;
import com.example.now.repository.Task_dataRepository;
import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private Task_dataRepository task_dataRepository;
    @RequestMapping(value = "/taskfindall",method = RequestMethod.GET)
    public List<Task> taskfindall(){
        return taskRepository.findAll();
    }
    @RequestMapping(value = "/taskfind/id",method = RequestMethod.GET)
    public Task taskfind_id(int id){
        return taskRepository.findById(id);
    }
    @RequestMapping(value = "/taskfind/name",method = RequestMethod.GET)
    public List<Task> taskfind_name(String name){
        return taskRepository.findByName(name);
    }
    @RequestMapping(value = "/taskfind/requesterid",method = RequestMethod.GET)
    public List<Task> taskfind_requesterid(int requesterid){
        return taskRepository.findByRequesterid(requesterid);
    }
    @RequestMapping(value = "/taskfind/reward",method = RequestMethod.GET)
    public List<Task> taskfind_reward(int least,int most){
        return taskRepository.findByRewardBetween(least,most);
    }
    @RequestMapping(value = "/taskadd",method = RequestMethod.POST)
    public void taskadd(String name,String description,int requester_id){
        Task temp=new Task(name,description,requester_id);
        taskRepository.saveAndFlush(temp);
    }
    @RequestMapping(value = "/taskupdate",method = RequestMethod.PUT)
    public void taskupdata(Task temp){
        taskRepository.saveAndFlush(temp);
    }
    @RequestMapping(value = "/taskdelete",method = RequestMethod.DELETE)
    public void taskdelete(int id){
        taskRepository.deleteById(id);
        taskRepository.flush();
    }
}
