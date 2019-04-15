package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Worker;

import java.util.List;

public interface WorkerService {

    List<Worker> findAllWorker();

    Worker findWorkerById(int id);

    Worker findWorkerByUsername(String username);

    String addWorker(String username, String name,String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, IdStore id, String school);

    String updateWorker(int workerId,String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String school);

    String deleteWorker(int id);
}
