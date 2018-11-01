package com.example.now.controller;
import com.example.now.entity.PersonalTask;
import com.example.now.repository.PersonalTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PersonalTaskController {
    @Autowired
    private PersonalTaskRepository personalTaskRepository;
    @RequestMapping(value = "/personalTaskId1",method = RequestMethod.GET)
    public List<PersonalTask> getTaskList(int id){
        return personalTaskRepository.findByIdWorkerId(id);
    }
    @RequestMapping(value = "/personalTaskId2",method = RequestMethod.GET)
    public List<PersonalTask> getWorkerList(int id){
        return personalTaskRepository.findByIdTaskId(id);
    }

    @RequestMapping(value = "/personalTask",method = RequestMethod.PUT)
    public PersonalTask createWorker(int workerId, int taskId){
        PersonalTask personalTask = new PersonalTask(workerId, taskId);
        return personalTaskRepository.save(personalTask);
    }

    @RequestMapping(value = "/personalTask",method = RequestMethod.POST)
    public PersonalTask updatePersonalTask(PersonalTask personalTask){
        return personalTaskRepository.save(personalTask);
    }

    @RequestMapping(value = "/personalTask",method = RequestMethod.DELETE)
    public void deletePersonalTask(int workerId, int taskId){
        PersonalTask personalTask = new PersonalTask(workerId, taskId);
        personalTaskRepository.delete(personalTask);
    }
}
