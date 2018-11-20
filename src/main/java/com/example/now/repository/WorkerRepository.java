package com.example.now.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    public Worker findByUsername(String username);

    public Worker findById(int id);
}
