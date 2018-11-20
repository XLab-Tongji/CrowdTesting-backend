package com.example.now.repository;

import com.example.now.entity.Requester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequesterRepository extends JpaRepository<Requester, Integer> {
    public Requester findByUsername(String username);

    public Requester findById(int id);
}
