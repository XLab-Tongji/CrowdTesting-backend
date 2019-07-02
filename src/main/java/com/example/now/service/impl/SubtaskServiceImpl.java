package com.example.now.service.impl;

import com.example.now.entity.*;
import com.example.now.repository.TaskRepository;
import com.example.now.repository.RequesterRepository;
import com.example.now.repository.WorkerRepository;
import com.example.now.service.AnswerService;
import com.example.now.service.SubtaskService;
import com.example.now.repository.SubtaskRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import com.example.now.service.TaskService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Subtask service implementation class
 *
 * @author jjc
 * @date 2019/05/17
 */
@Service
public class SubtaskServiceImpl implements SubtaskService{
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public List<Subtask> findAllSubtask() {
        List<Subtask> temp = subtaskRepository.findAll();
        Collections.reverse(temp);
        for(int i=0;i<temp.size();i++){
            Task theTask =  taskRepository.findById(temp.get(i).getTaskId());
            Requester theRequester = requesterRepository.findById(theTask.getRequesterId()).get();
            temp.get(i).setTaskType(theTask.getType());
            temp.get(i).setTitle(theTask.getName());
            temp.get(i).setUsername(theRequester.getUsername());
        }
        return temp;
    }

    @Override
    public Subtask findSubtaskById(int id) {
        Subtask temp = subtaskRepository.findById(id);
        if(temp!=null) {
            Task theTask = taskRepository.findById(temp.getTaskId());
            Requester theRequester = requesterRepository.findById(theTask.getRequesterId()).get();
            temp.setTaskType(theTask.getType());
            temp.setTitle(theTask.getName());
            temp.setUsername(theRequester.getUsername());
            return temp;
        }
        else{
            return null;
        }
    }

    @Override
    public List<Subtask> findSubtaskByWorkerId(int id) {
        List<Subtask> temp = subtaskRepository.findByWorkerId(id);
        Collections.reverse(temp);
        for(int i=0;i<temp.size();i++){
            Task theTask =  taskRepository.findById(temp.get(i).getTaskId());
            Requester theRequester = requesterRepository.findById(theTask.getRequesterId()).get();
            temp.get(i).setTaskType(theTask.getType());
            temp.get(i).setTitle(theTask.getName());
            temp.get(i).setUsername(theRequester.getUsername());
        }
        return temp;
    }

    @Override
    public List<Subtask> findSubtaskByTaskId(int id){
        List<Subtask> temp = subtaskRepository.findByTaskId(id);
        Collections.reverse(temp);
        for(int i=0;i<temp.size();i++){
            Task theTask =  taskRepository.findById(temp.get(i).getTaskId());
            Requester theRequester = requesterRepository.findById(theTask.getRequesterId()).get();
            temp.get(i).setTaskType(theTask.getType());
            temp.get(i).setTitle(theTask.getName());
            temp.get(i).setUsername(theRequester.getUsername());
        }
        return temp;
    }

    @Override
    public String addSubtask(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, IdStore id, int nowBegin) {
        Subtask temp = new Subtask(begin, end, createdTime, deadline, updatedTime, isFinished, type, workerId, taskId, numberOfTask, nowBegin);
        Subtask result=subtaskRepository.saveAndFlush(temp);
        id.setId(result.getId());
        return "succeed";
    }

    @Override
    public String readSubtaskResource(int subtaskId){
        Subtask theSubtask = subtaskRepository.findById(subtaskId);
        Task task = taskRepository.findById(theSubtask.getTaskId());
        String fileName = task.getResourceLink();
        int begin = theSubtask.getNowBegin() - 1;
        int end = theSubtask.getEnd();
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] filecontent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            String str = new String(filecontent, 0, fileLength.intValue(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(str);
            JSONObject newJson = new JSONObject();
            String desc = json.getString("desc");
            JSONArray opts = json.getJSONArray("opts");
            newJson.put("desc",desc);
            newJson.put("opts",opts);
            JSONArray newUrls = new JSONArray();
            JSONArray urlsList = json.getJSONArray("urls");
            for(int i=begin;i<end;i++){
                newUrls.put(urlsList.getJSONObject(i));
            }
            newJson.put("urls",newUrls);
            return newJson.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public String updateSubtask(int begin, int end, Timestamp createdTime, Timestamp deadline, Timestamp updatedTime, int isFinished, int type, int workerId, int taskId, int numberOfTask, int nowBegin, int id) {
        Subtask newSubtask=subtaskRepository.findById(id);
        newSubtask.setAll(begin, end, createdTime, deadline, updatedTime, isFinished, type, workerId, taskId, numberOfTask,nowBegin);
        subtaskRepository.saveAndFlush(newSubtask);
        return "succeed";
    }

    @Override
    public String deleteSubtask(int id) {
        subtaskRepository.deleteById(id);
        return "succeed";
    }

    @Override
    public void updateIsFinished(){
        //1. 获取所有未完成的子任务
        List<Subtask> subtasks=subtaskRepository.findByIsFinished(0);
        List<Subtask> expiredSubtasks=new ArrayList<Subtask>();
        //2. 获取其中过期的子任务，并执行相关操作
        for(Subtask subtask:subtasks){
            Timestamp currentTime=new Timestamp(System.currentTimeMillis());
            //比较当前时间与 deadline
            if(currentTime.after(subtask.getDeadline())){
                expiredSubtasks.add(subtask);
                //设置 isFinished 字段为 -1
                subtask.setIsFinished(-1);
                //写回
                subtaskRepository.saveAndFlush(subtask);
                //更改对应 task 的 answer 字段
                taskService.deleteExpiredAnswer(subtask.getTaskId(),subtask.getNumberOfTask(),subtask.getBegin(),subtask.getEnd());
                //删除 answer 表中对应的数据
                answerService.deleteAnswerBySubtaskId(subtask.getId());
                //更新对应 worker 的过期子任务数
                Worker worker=workerRepository.findById(subtask.getWorkerId());
                worker.setOvertimeNumber(worker.getOvertimeNumber()+1);
                workerRepository.saveAndFlush(worker);
                Task task = taskRepository.findById(subtask.getTaskId());
                JSONObject restOfQuestion = new JSONObject(task.getRestOfQuestion());
                JSONObject backQuestions = new JSONObject();
                backQuestions.put("begin", subtask.getNowBegin());
                backQuestions.put("end", subtask.getEnd());
                restOfQuestion.getJSONArray(String.valueOf(subtask.getNumberOfTask())).put(backQuestions);
                task.setRestOfQuestion(restOfQuestion.toString());
                String message = updateSubtask(subtask.getBegin(), subtask.getNowBegin() - 1, subtask.getCreatedTime(), subtask.getDeadline(), subtask.getUpdatedTime(), subtask.getIsFinished(), subtask.getType(), subtask.getWorkerId(), subtask.getTaskId(), subtask.getNumberOfTask(), subtask.getNowBegin(),subtask.getId());
            }
        }
    }
}