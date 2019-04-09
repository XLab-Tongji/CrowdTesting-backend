package com.example.now.service;

import com.example.now.entity.Answer;
import com.example.now.entity.IdStore;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;


public interface AnswerService {
    List<Answer> findAllAnswer();

    Answer findAnswerById(int id);

    List<Answer> findAnswerByWorkerId(int id);

    List<Answer> findAnswerByTaskId(int id);

    String addAnswer(int worker_id, int task_id, String answer, Timestamp answer_time, IdStore id);

    String updateAnswer(int worker_id, int task_id, String answer, Timestamp answer_time, int id);

    String deleteAnswer(int id);
}
