package com.example.now.service.Iml;

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
            Task the_task =  taskRepository.findById(temp.get(i).getTaskId());
            Requester the_requester = requesterRepository.findById(the_task.getRequesterid()).get();
            temp.get(i).setTask_type(the_task.getType());
            temp.get(i).setTitle(the_task.getName());
            temp.get(i).setUsername(the_requester.getUsername());
        }
        return temp;
    }

    @Override
    public Subtask findSubtaskById(int id) {
        Subtask temp = subtaskRepository.findById(id);
        Task the_task =  taskRepository.findById(temp.getTaskId());
        Requester the_requester = requesterRepository.findById(the_task.getRequesterid()).get();
        temp.setTask_type(the_task.getType());
        temp.setTitle(the_task.getName());
        temp.setUsername(the_requester.getUsername());
        return temp;
    }

    @Override
    public List<Subtask> findSubtaskByWorkerId(int id) {
        List<Subtask> temp = subtaskRepository.findByWorkerId(id);
        Collections.reverse(temp);
        for(int i=0;i<temp.size();i++){
            Task the_task =  taskRepository.findById(temp.get(i).getTaskId());
            Requester the_requester = requesterRepository.findById(the_task.getRequesterid()).get();
            temp.get(i).setTask_type(the_task.getType());
            temp.get(i).setTitle(the_task.getName());
            temp.get(i).setUsername(the_requester.getUsername());
        }
        return temp;
    }

    @Override
    public List<Subtask> findSubtaskByTaskId(int id){
        List<Subtask> temp = subtaskRepository.findByTaskId(id);
        Collections.reverse(temp);
        for(int i=0;i<temp.size();i++){
            Task the_task =  taskRepository.findById(temp.get(i).getTaskId());
            Requester the_requester = requesterRepository.findById(the_task.getRequesterid()).get();
            temp.get(i).setTask_type(the_task.getType());
            temp.get(i).setTitle(the_task.getName());
            temp.get(i).setUsername(the_requester.getUsername());
        }
        return temp;
    }

    @Override
    public String addSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, IdStore id, int now_begin) {
        Subtask temp = new Subtask(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task, now_begin);
        Subtask result=subtaskRepository.saveAndFlush(temp);
        id.setId(result.getId());
        return "succeed";
    }

    @Override
    public String readSubtaskResource(int subtaskId){
        Subtask the_subtask = subtaskRepository.findById(subtaskId);
        Task task = taskRepository.findById(the_subtask.getTaskId());
        String fileName = task.getResource_link();
        int begin = the_subtask.getNow_begin() - 1;
        int end = the_subtask.getEnd();
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] filecontent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            String str = new String(filecontent, 0, fileLength.intValue(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(str);
            JSONObject new_json = new JSONObject();
            String desc = json.getString("desc");
            JSONArray opts = json.getJSONArray("opts");
            new_json.put("desc",desc);
            new_json.put("opts",opts);
            JSONArray new_urls = new JSONArray();
            JSONArray urls_list = json.getJSONArray("urls");
            for(int i=begin;i<end;i++){
                new_urls.put(urls_list.getJSONObject(i));
            }
            new_json.put("urls",new_urls);
            return new_json.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public String updateSubtask(int begin, int end, Timestamp created_time, Timestamp deadline, Timestamp updated_time, int is_finished, int type, int workerId, int taskId, int number_of_task, int now_begin, int id) {
        Subtask new_Subtask=subtaskRepository.findById(id);
        new_Subtask.setAll(begin, end, created_time, deadline, updated_time, is_finished, type, workerId, taskId, number_of_task,now_begin);
        subtaskRepository.saveAndFlush(new_Subtask);
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
                taskService.deleteExpiredAnswer(subtask.getTaskId(),subtask.getNumber_of_task(),subtask.getBegin(),subtask.getEnd());
                //删除 answer 表中对应的数据
                answerService.deleteAnswerBySubtaskId(subtask.getId());
                //更新对应 worker 的过期子任务数
                Worker worker=workerRepository.findById(subtask.getWorkerId());
                worker.setOvertime_number(worker.getOvertime_number()+1);
                workerRepository.saveAndFlush(worker);
                Task task = taskRepository.findById(subtask.getTaskId());
                JSONObject rest_of_question = new JSONObject(task.getRest_of_question());
                JSONObject backQuestions = new JSONObject();
                backQuestions.put("begin", subtask.getNow_begin());
                backQuestions.put("end", subtask.getEnd());
                rest_of_question.getJSONArray(String.valueOf(subtask.getNumber_of_task())).put(backQuestions);
                task.setRest_of_question(rest_of_question.toString());
                String message = updateSubtask(subtask.getBegin(), subtask.getNow_begin() - 1, subtask.getCreated_time(), subtask.getDeadline(), subtask.getUpdated_time(), subtask.getIsFinished(), subtask.getType(), subtask.getWorkerId(), subtask.getTaskId(), subtask.getNumber_of_task(), subtask.getNow_begin(),subtask.getId());
            }
        }
    }
}