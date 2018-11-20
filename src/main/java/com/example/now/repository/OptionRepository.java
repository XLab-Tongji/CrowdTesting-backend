package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Option;
import java.util.List;
public interface OptionRepository extends JpaRepository<Option, Integer>{
    List<Option> findByQuestionId(int id);
}
