package com.example.now.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    Worker findByEMail(String eMail);

    Worker findById(int id);
}
