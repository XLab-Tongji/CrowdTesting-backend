package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Worker;
import com.example.now.service.WorkerService;
import com.example.now.util.TokenUtils;
import com.example.now.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Worker findWorkerById(int id) {
        Worker worker=workerRepository.findById(id);
        worker.setLevel();
        return worker;
    }

    @Override
    public Worker findWorkerByUsername(String username) {
        Worker worker=workerRepository.findByEMail(username);
        worker.setLevel();
        return worker;
    }

    @Override
    public String addWorker(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, IdStore id, String school) {
        if (username == null || name == null) {
            return "username or name is empty";
        }
        Worker worker = new Worker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,school);
        Worker temp=workerRepository.saveAndFlush(worker);
        id.setId(temp.getId());
        return "succeed";
    }

    @Override
    public String updateWorker(int workerId,String username, String name, String teleNumber, String eMail,String withdrawnMethod, String education, String workArea, int age,String gender, String major, String school) {
        Worker worker=workerRepository.findById(workerId);
        worker.setAll(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,school);
        workerRepository.saveAndFlush(worker);
        return "succeed";
    }

    @Override
    public String deleteWorker(int id) {
        workerRepository.deleteById(id);
        workerRepository.flush();
        return "succeed";
    }
}
