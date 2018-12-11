package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Question;
import java.util.List;
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    List<Question> findByTaskId(int id);
    Question findById(int id);
}
