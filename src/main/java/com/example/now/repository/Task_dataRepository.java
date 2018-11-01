package com.example.now.repository;
import com.example.now.entity.Task_data;
import com.example.now.entity.Task_data_prime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface Task_dataRepository extends JpaRepository<Task_data, Task_data_prime>{
    public List<Task_data> findByPrimeId(int id);
}
