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
    public String addAnswer(Integer workerId, int taskId, String answer, Timestamp answerTime, IdStore id,int subtaskId,Integer beginAt,Integer endAt,String isCorrect) {
        if (answer == null){
            return "inputs are not enough";
        }
        if(workerId==null||workerId<=0||taskId<=0||"".equals(answer)||subtaskId<=0||beginAt<=0||endAt<=0||beginAt>endAt){
            return "invalid parameter";
        }
        Answer temp = new Answer(workerId, taskId, answerTime, answer);
        temp.setSubtaskId(subtaskId);
        temp.setBeginAt(beginAt);
        temp.setEndAt(endAt);
        Subtask subtask=subtaskRepository.findById(subtaskId);
        //判断该子任务为普通任务还是审核任务
        int type=subtask.getType();
        if(type==1){//若为审核任务则需对 answer 进行处理
            if("".equals(isCorrect)){
                return "invalid parameter";
            }
            //根据 isCorrect 数组更新 task 的 answer 字段中
            Task task=taskRepository.findById(taskId);
            JSONArray answers=new JSONArray(task.getAnswer());
            JSONArray isCorrectArray =new JSONArray(isCorrect);
            /*//遍历前 population-1 套答案
            for(int i=0;i<task.getPopulation()-1;i++){
                JSONArray singelAnswer=answers.getJSONArray(i);
                JSONArray isCorrectSingleArray=isCorrectArray.getJSONArray(i);
                //处理本套答案中 beginAt--endAt 区间的数据
                for (int j=beginAt-1;j<endAt;j++){
                    if(isCorrectSingleArray.getInt(j-beginAt+1)==0){//该题错误
                        singelAnswer.getJSONObject(j).getJSONObject("content").put("isCorrect",0);
                    }
                    if(isCorrectSingleArray.getInt(j-beginAt+1)==1){//该题正确
                        singelAnswer.getJSONObject(j).getJSONObject("content").put("isCorrect",1);
                    }
                }
                //更新 answers
                answers.put(i,singelAnswer);
            }*/
            //处理本套答案中 beginAt--endAt 区间的数据
            for(int i=beginAt-1;i<endAt;i++){
                JSONArray isCorrectSingleArray=isCorrectArray.getJSONArray(i-beginAt+1);
                for(int j=0;j<task.getPopulation()-1;j++){
                    if(isCorrectSingleArray.getInt(j)==0){//该题错误
                        //更新 answers
                        answers.getJSONArray(j).getJSONObject(i).getJSONObject("content").put("isCorrect",0);
                    }
                    if(isCorrectSingleArray.getInt(j)==1){//该题正确
                        //更新 answers
                        answers.getJSONArray(j).getJSONObject(i).getJSONObject("content").put("isCorrect",1);
                    }
                }
            }

            //存回
            task.setAnswer(answers.toString());
            taskRepository.saveAndFlush(task);
        }
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
    public String updateAnswer(Integer workerId, int taskId, String answer, Timestamp answerTime,Integer id,int subtaskId,Integer beginAt,Integer endAt,String isCorrect) {
        if(workerId==null||workerId<=0||taskId<=0||"".equals(answer)||id<=0||subtaskId<=0||beginAt<=0||endAt<=0||beginAt>endAt){
            return "invalid parameter";
        }
        Answer newAnswer=answerRepository.findById(id.intValue());
        Subtask subtask=subtaskRepository.findById(subtaskId);
        //判断该子任务为普通任务还是审核任务
        int type=subtask.getType();
        if(type==1){//若为审核任务则需对 answer 进行处理
            if("".equals(isCorrect)){
                return "invalid parameter";
            }
            //TODO: 将 isCorrect 数组统计到 task 的 answer 字段中
            Task task=taskRepository.findById(taskId);
            JSONArray answers=new JSONArray(task.getAnswer());
            JSONArray isCorrectArray =new JSONArray(isCorrect);
            /*//遍历前 population-1 套答案
            for(int i=0;i<task.getPopulation()-1;i++){
                JSONArray singelAnswer=answers.getJSONArray(i);
                JSONArray isCorrectSingleArray=isCorrectArray.getJSONArray(i);
                //处理本套答案中 beginAt--endAt 区间的数据
                for (int j=beginAt-1;j<endAt;j++){
                    if(isCorrectSingleArray.getInt(j-beginAt+1)==0){//该题错误
                        singelAnswer.getJSONObject(j).getJSONObject("content").put("isCorrect",0);
                    }
                    if(isCorrectSingleArray.getInt(j-beginAt+1)==1){//该题正确
                        singelAnswer.getJSONObject(j).getJSONObject("content").put("isCorrect",1);
                    }
                }
                //更新 answers
                answers.put(i,singelAnswer);
            }*/
            //处理本套答案中 beginAt--endAt 区间的数据
            for(int i=beginAt-1;i<endAt;i++){
                JSONArray isCorrectSingleArray=isCorrectArray.getJSONArray(i-beginAt+1);
                for(int j=0;j<task.getPopulation()-1;j++){
                    if(isCorrectSingleArray.getInt(j)==0){//该题错误
                        //更新 answers
                        answers.getJSONArray(j).getJSONObject(i).getJSONObject("content").put("isCorrect",0);
                    }
                    if(isCorrectSingleArray.getInt(j)==1){//该题正确
                        //更新 answers
                        answers.getJSONArray(j).getJSONObject(i).getJSONObject("content").put("isCorrect",1);
                    }
                }
            }
            //存回
            task.setAnswer(answers.toString());
            taskRepository.saveAndFlush(task);
        }
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
