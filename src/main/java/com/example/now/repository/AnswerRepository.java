package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Answer;
import java.util.List;
public interface AnswerRepository extends JpaRepository<Answer,Integer>{
    List<Answer>findByQuestionId();
    Answer findByQuestionIdAndWorkerId();
}
