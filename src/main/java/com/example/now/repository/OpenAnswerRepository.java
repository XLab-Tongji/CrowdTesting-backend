package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.OpenAnswer;
import java.util.List;
public interface OpenAnswerRepository extends JpaRepository<OpenAnswer,Integer>{
    List<OpenAnswer> findByQuestionId(int id);
    List<OpenAnswer> findByOptionId(int id);
    OpenAnswer findByOptionIdAndWorkerId(int optionId,int workerId);
    List<OpenAnswer> findByQuestionIdAndWorkerId(int questionId,int workerId);
}
