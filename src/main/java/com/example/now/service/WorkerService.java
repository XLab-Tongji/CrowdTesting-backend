package com.example.now.service;

import com.example.now.entity.Worker;

public interface WorkerService {
    Worker findWorkerById(int id);

    Worker findWorkerByUsername(String username);

    String addWorker(String username, String name);

    String updateWorker(Worker worker);

    String deleteWorker(int id);
}
