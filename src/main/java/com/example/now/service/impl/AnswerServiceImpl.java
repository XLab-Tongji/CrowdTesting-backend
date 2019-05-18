package com.example.now.service.impl;

import com.example.now.entity.*;
import com.example.now.repository.SubtaskRepository;
import com.example.now.repository.TaskRepository;
import com.example.now.service.AnswerService;
import com.example.now.repository.AnswerRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import com.example.now.service.TaskService;
import com.example.now.util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Answer service implementation class
 *
 * @author jjc
 * @date 2019/05/17
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

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
    public String addAnswer(int workerId, int taskId, String answer, Timestamp answerTime, IdStore id,int subtaskId,Integer beginAt,Integer endAt) {
        if (answer == null){
            return "inputs are not enough";
        }
        Answer temp = new Answer(workerId, taskId, answerTime, answer);
        temp.setSubtaskId(subtaskId);
        temp.setBeginAt(beginAt);
        temp.setEndAt(endAt);
        Subtask subtask=subtaskRepository.findById(subtaskId);
        temp.setNumber(subtask.getEnd()-subtask.getBegin()+1);
        Answer result=answerRepository.saveAndFlush(temp);
        id.setId(result.getId());
        //更新对应 subtask 中的 updated_time,now_begin
        subtask.setNowBegin(endAt+1);
        subtask.setUpdatedTime(new Timestamp(System.currentTimeMillis()));

        //若该子任务已完成，则修改对应子任务字段
        if(isFinished(result)){
            if(result.getSubtaskId()==null) {
                System.out.println("SubtaskId is null");
            }
            //修改对应 subtask 的 isFinished 字段为 1 ，代表已完成
            subtask.setIsFinished(1);
        }
        subtaskRepository.saveAndFlush(subtask);
        //将答案写入 task 中的 answer 字段
        taskService.updateAnswer(taskId,answer,subtask.getNumberOfTask());
        return "succeed";
    }

    @Override
    public String updateAnswer(int workerId, int taskId, String answer, Timestamp answerTime,int id,int subtaskId,Integer beginAt,Integer endAt) {
        Answer newAnswer=answerRepository.findById(id);
        //判断答案是否相连，即 new_answer.endAt 是否等于 beginAt-1
        if(newAnswer.getEndAt()==beginAt-1){
            //答案 json 合并，并更新字段
            newAnswer.setAll(workerId, taskId, answerTime, JsonUtil.appendJson(newAnswer.getAnswer(),answer));
            newAnswer.setEndAt(endAt);
        }
        else{
            return "failed";
        }
        answerRepository.saveAndFlush(newAnswer);
        //更新对应 subtask 中的 updated_time,now_begin
        Subtask subtask=subtaskRepository.findById(subtaskId);
        subtask.setNowBegin(endAt+1);
        subtask.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        //若该子任务已完成，则修改对应子任务字段
        if(isFinished(newAnswer)){
            if(newAnswer.getSubtaskId()==null) {
                System.out.println("SubtaskId is null");
            }
            //修改对应 subtask 的 isFinished 字段为 1 ，代表已完成
            subtask.setIsFinished(1);
        }
        subtaskRepository.saveAndFlush(subtask);
        //将答案写入对应 task 中的 answer 字段
        taskService.updateAnswer(taskId,answer,subtask.getNumberOfTask());
        return "succeed";
    }

    @Override
    public String deleteAnswer(int id) {
        answerRepository.deleteById(id);
        return "succeed";
    }

    @Override
    public String deleteAnswerBySubtaskId(int subtaskId){
        if(answerRepository.deleteBySubtaskId(subtaskId)){
            return "succeed";
        }
        return "failed";
    }

    @Override
    public Boolean isFinished(int id){
        Answer answer=answerRepository.findById(id);
        if(answer.getBeginAt()==null || answer.getEndAt()==null) {
            return false;
        }
        else{
            return answer.getEndAt()-answer.getBeginAt()==answer.getNumber();
        }
    }

    @Override
    public Boolean isFinished(Answer answer){
        if(answer.getBeginAt()==null || answer.getEndAt()==null) {
            return false;
        }
        else{
            return answer.getEndAt()-answer.getBeginAt()==answer.getNumber()-1;
        }
    }

    @Override
    public String findAnswerBySubtaskId(int subtaskId,int taskId){
        Task task=taskRepository.findById(taskId);
        Subtask subtask=subtaskRepository.findById(subtaskId);
        JSONArray currentAnswers=new JSONArray(task.getAnswer());
        //得到当前的开始题号
        int begin=subtask.getNowBegin();
        int end=subtask.getEnd();
        int population=task.getPopulation();
        JSONArray ultimateAnswers=new JSONArray();
        //获取对应的答案，并放入 answers 中
        //遍历前 population-1 套答案，前提是只有最后一份为审核任务的答案
        for(int i=0;i<population-1;i++){
            JSONArray currentAnswer=currentAnswers.getJSONArray(i);
            JSONArray answer=new JSONArray();
            for(int j=begin-1;j<end;j++){
                //将对应答案放入 answer 中
                JSONObject singleAnswer=currentAnswer.getJSONObject(j).getJSONObject("content");
                answer.put(singleAnswer);
            }
            ultimateAnswers.put(answer);
        }
        return ultimateAnswers.toString();
    }
}
