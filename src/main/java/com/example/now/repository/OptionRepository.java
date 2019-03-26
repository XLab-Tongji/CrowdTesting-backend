package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Option;
import java.util.List;
public interface OptionRepository extends JpaRepository<Option, Integer>{
    Option findById(int id);
    List<Option> findByQuestionIdOrderByOptionNumber(int id);
}
