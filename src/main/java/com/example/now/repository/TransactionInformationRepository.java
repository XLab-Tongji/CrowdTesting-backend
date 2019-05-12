package com.example.now.repository;

import com.example.now.entity.TransactionInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionInformationRepository extends JpaRepository<TransactionInformation, Integer> {
    public TransactionInformation findById(int id);

    public List<TransactionInformation> findByRequesterId(int id);

    public List<TransactionInformation> findByTaskId(int id);

    public void deleteById(int id);
}
