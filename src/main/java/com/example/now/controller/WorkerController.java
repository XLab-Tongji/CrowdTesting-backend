package com.example.now.controller;
import com.example.now.entity.Worker;
import com.example.now.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;
    @RequestMapping(value = "/workerid",method = RequestMethod.GET)
    public Worker getWorkerById(int id){
        return workerRepository.findById(id);
    }           //根据ID查找worker
    @RequestMapping(value = "/workerusername",method = RequestMethod.GET)
    public Worker getWorkerByUsername(String username){                           //根据名字查找worker
        return workerRepository.findByUsername(username);
    }

    @RequestMapping(value = "/worker",method = RequestMethod.PUT)
    public Worker createWorker(String username,String name){                      //创建一个worker
        Worker worker = new Worker(username,name);
        return workerRepository.save(worker);
    }

    @RequestMapping(value = "/worker",method = RequestMethod.POST)
    public Worker updateWorker(Worker worker){                      //修改worker
        return workerRepository.save(worker);
    }

    @RequestMapping(value = "/worker",method = RequestMethod.DELETE)
    public void deleteWorker(int id){                      //删除worker
        workerRepository.deleteById(id);
    }
}
