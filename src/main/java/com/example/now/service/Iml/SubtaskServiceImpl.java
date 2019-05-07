package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;
import com.example.now.entity.Task;
import com.example.now.entity.Requester;
import com.example.now.repository.TaskRepository;
import com.example.now.repository.RequesterRepository;
import com.example.now.service.SubtaskService;
import com.example.now.repository.SubtaskRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubtaskServiceImpl implements SubtaskService{
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RequesterRepository requesterRepository;

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
}