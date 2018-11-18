package com.example.now.service;
import com.example.now.entity.PersonalTask;
import com.example.now.entity.Worker;
import com.example.now.entity.Task;
import com.example.now.repository.PersonalTaskRepository;
import com.example.now.service.PersonalTaskService;
import com.example.now.repository.TaskRepository;
import com.example.now.repository.WorkerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PersonalTaskServiceImpl implements PersonalTaskService{
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private PersonalTaskRepository personalTaskRepository;
    @Override
    public List<Task> findTaskByWorkerId(int id){
        List<Integer> taskId=new ArrayList<Integer>();
        List<Task> tasks=new ArrayList<Task>();
        for(PersonalTask personalTask:personalTaskRepository.findByIdWorkerId(id)){
            taskId.add(personalTask.getTaskId());
        }
        for(int taskid:taskId){
            tasks.add(taskRepository.findById(taskid));
        }
        Collections.reverse(tasks);
        return tasks;
    }
    @Override
    public List<Worker> findWorkerByTaskId(int id){
        List<Integer> workerId=new ArrayList<Integer>();
        List<Worker> workers=new ArrayList<Worker>();
        for(PersonalTask personalTask:personalTaskRepository.findByIdTaskId(id)){
            workerId.add(personalTask.getWorkerId());
        }
        for(int workerid:workerId){
            workers.add(workerRepository.findById(workerid));
        }
        return workers;
    }
    @Override
    public String addPersonalTask(Integer workerid,Integer taskid){
        if(workerid==null||taskid==null){
            return "inputs are not enough";
        }
        PersonalTask personalTask = new PersonalTask(workerid,taskid);
        personalTaskRepository.saveAndFlush(personalTask);
        return "succeed";
    }
    @Override
    public String deletePersonalTask(Integer workerid,Integer taskid){
        if(workerid==null||taskid==null){
            return "inputs are not enough";
        }
        PersonalTask personalTask=personalTaskRepository.findByIdWorkerIdAndIdTaskId(workerid,taskid);
        if(personalTask==null){
            return "wrong input";
        }
        personalTaskRepository.delete(personalTask);
        personalTaskRepository.flush();
        return "succeed";
    }
}
