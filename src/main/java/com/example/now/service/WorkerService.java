package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Worker;

public interface WorkerService {
    Worker findWorkerById(int id);

    Worker findWorkerByUsername(String username);

    String addWorker(String username, String name,String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, IdStore id);

    String updateWorker(int workerId,String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major);

    String deleteWorker(int id);
}
