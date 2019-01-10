package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.OptionSelected;
import java.util.List;
public interface OptionSelectedRepository extends JpaRepository<OptionSelected,Integer >{
    List<OptionSelected> findByOptionId(int id);
    OptionSelected findByOptionIdAndWorkerId(int optionId,int workId);
    int countByOptionId(int optionId);
}