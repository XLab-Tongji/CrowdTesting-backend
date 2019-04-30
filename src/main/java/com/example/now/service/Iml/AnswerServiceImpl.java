package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Answer;
import com.example.now.entity.MultipleChoiceAnswer;
import com.example.now.entity.Subtask;
import com.example.now.repository.SubtaskRepository;
import com.example.now.service.AnswerService;
import com.example.now.repository.AnswerRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;
import java.io.InputStream;

import com.example.now.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SubtaskRepository subtaskRepository;

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
    public String addAnswer(int worker_id, int task_id, String answer, Timestamp answer_time, IdStore id,int subtaskId,Integer beginAt,Integer endAt) {
        if (answer == null)
            return "inputs are not enough";
        Answer temp = new Answer(worker_id, task_id, answer_time, answer);
        temp.setSubtaskId(subtaskId);
        temp.setBeginAt(beginAt);
        temp.setEndAt(endAt);
        temp.setNumber(endAt-beginAt);
        Answer result=answerRepository.saveAndFlush(temp);
        id.setId(result.getId());
        //若该子任务已完成，则修改对应子任务字段
        if(isFinished(result)){
            if(result.getSubtaskId()==null)
                System.out.println("SubtaskId is null");
            //修改对应 subtask 的 isFinished 字段为 1 ，代表已完成
            Subtask subtask=subtaskRepository.findById(subtaskId);
            subtask.setIs_finished(1);
            subtaskRepository.saveAndFlush(subtask);
        }
        return "succeed";
    }

    @Override
    public String updateAnswer(int worker_id, int task_id, String answer, Timestamp answer_time,int id,int subtaskId,Integer beginAt,Integer endAt) {
        Answer new_answer=answerRepository.findById(id);
        //判断答案是否相连，即 new_answer.endAt 是否等于 beginAt-1
        if(new_answer.getEndAt()==beginAt-1){
            //答案 json 合并，并更新字段
            new_answer.setAll(worker_id, task_id, answer_time, JsonUtil.appendJson(new_answer.getAnswer(),answer));
        }
        else{
            return "failed";
        }
        answerRepository.saveAndFlush(new_answer);
        //若该子任务已完成，则修改对应子任务字段
        if(isFinished(new_answer)){
            if(new_answer.getSubtaskId()==null)
                System.out.println("SubtaskId is null");
            //修改对应 subtask 的 isFinished 字段为 1 ，代表已完成
            Subtask subtask=subtaskRepository.findById(subtaskId);
            subtask.setIs_finished(1);
            subtaskRepository.saveAndFlush(subtask);
        }
        return "succeed";
    }

    @Override
    public String deleteAnswer(int id) {
        answerRepository.deleteById(id);
        return "succeed";
    }

    @Override
    public Boolean isFinished(int id){
        Answer answer=answerRepository.findById(id);
        if(answer.getBeginAt()==null || answer.getEndAt()==null)
            return false;
        else{
            return answer.getEndAt()-answer.getBeginAt()==answer.getNumber();
        }
    }

    @Override
    public Boolean isFinished(Answer answer){
        if(answer.getBeginAt()==null || answer.getEndAt()==null)
            return false;
        else{
            return answer.getEndAt()-answer.getBeginAt()==answer.getNumber();
        }
    }
}
