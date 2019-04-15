package com.example.now.service.Iml;

import com.example.now.entity.MyTask;
import com.example.now.entity.PersonalTask;
import com.example.now.entity.Worker;
import com.example.now.entity.Task;
import com.example.now.repository.PersonalTaskRepository;
import com.example.now.service.PersonalTaskService;
import com.example.now.service.RequesterService;
import com.example.now.repository.TaskRepository;
import com.example.now.repository.WorkerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalTaskServiceImpl implements PersonalTaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private PersonalTaskRepository personalTaskRepository;
    @Autowired
    private RequesterService requesterService;

    @Override
    public List<MyTask> findTaskByWorkerId(int id) {
        List<Integer> taskId = new ArrayList<Integer>();
        List<Integer> finished=new ArrayList<>();
        List<MyTask> tasks = new ArrayList<>();
        for (PersonalTask personalTask : personalTaskRepository.findByIdWorkerId(id)) {
            taskId.add(personalTask.getTaskId());
            finished.add(personalTask.getFinished());
        }
        for (int i=0;i<taskId.size();i++) {
            tasks.add(new MyTask(taskRepository.findById(taskId.get(i).intValue()),finished.get(i)));
        }
        for(MyTask aTask : tasks) {
            aTask.getTask().setInstitution_name(requesterService.findRequesterById(aTask.getTask().getRequesterid()).getInstitutionName());
        }
        Collections.reverse(tasks);
        return tasks;
    }

    @Override
    public List<Worker> findWorkerByTaskId(int id) {
        List<Integer> workerId = new ArrayList<Integer>();
        List<Worker> workers = new ArrayList<Worker>();
        for (PersonalTask personalTask : personalTaskRepository.findByIdTaskId(id)) {
            workerId.add(personalTask.getWorkerId());
        }
        for (int workerid : workerId) {
            Worker worker=workerRepository.findById(workerid);
            worker.setLevel();
            workers.add(worker);
        }
        return workers;
    }

    @Override
    public String addPersonalTask(Integer workerid, Integer taskid) {
        if (workerid == null || taskid == null) {
            return "inputs are not enough";
        }
        PersonalTask personalTask = new PersonalTask(workerid, taskid);
        personalTaskRepository.saveAndFlush(personalTask);
        return "succeed";
    }

    @Override
    public String deletePersonalTask(Integer workerid, Integer taskid) {
        if (workerid == null || taskid == null) {
            return "inputs are not enough";
        }
        PersonalTask personalTask = personalTaskRepository.findByIdWorkerIdAndIdTaskId(workerid, taskid);
        if (personalTask == null) {
            return "wrong input";
        }
        personalTaskRepository.delete(personalTask);
        personalTaskRepository.flush();
        return "succeed";
    }
    @Override
    public String finishOne(int workerId,int taskId){
        PersonalTask personalTask = personalTaskRepository.findByIdWorkerIdAndIdTaskId(workerId, taskId);
        if (personalTask == null) {
            return "wrong input";
        }
        personalTask.setFinished(1);
        personalTaskRepository.saveAndFlush(personalTask);
        return "succeed";
    }
}
