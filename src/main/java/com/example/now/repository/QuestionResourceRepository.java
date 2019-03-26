package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.QuestionResource;
import java.util.List;
public interface QuestionResourceRepository extends JpaRepository<QuestionResource,Integer>{
    List<QuestionResource> findByQuestionId(int questionId);
}
