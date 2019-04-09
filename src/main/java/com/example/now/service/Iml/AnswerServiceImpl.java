package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Answer;
import com.example.now.service.AnswerService;
import com.example.now.repository.AnswerRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.input.BOMInputStream;

import java.nio.charset.StandardCharsets;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<Answer> findAllAnswer() {
        List<Answer> temp = answerRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Answer findAnswerById(int id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> findAnswerByWorkerId(int id) {
        List<Answer> temp = answerRepository.findByWorkerId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Answer> findAnswerByTaskId(int id){
        List<Answer> temp = answerRepository.findByTaskId(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public String addAnswer(int worker_id, int task_id, String answer, Timestamp answer_time, IdStore id) {
        if (answer == null)
            return "inputs are not enough";
        Answer temp = new Answer(worker_id, task_id, answer_time, answer);
        Answer result=answerRepository.saveAndFlush(temp);
        id.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateAnswer(int worker_id, int task_id, String answer, Timestamp answer_time, int id) {
        Answer new_answer=answerRepository.findById(id);
        new_answer.setAll(worker_id, task_id, answer_time, answer);
        answerRepository.saveAndFlush(new_answer);
        return "succeed";
    }

    @Override
    public String deleteAnswer(int id) {
        answerRepository.deleteById(id);
        return "succeed";
    }
}
