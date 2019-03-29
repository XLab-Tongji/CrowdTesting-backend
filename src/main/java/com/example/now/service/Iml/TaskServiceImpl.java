package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Task;
import com.example.now.service.TaskService;
import com.example.now.repository.TaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.FileInputStream;

import org.json.JSONObject;


@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllTask() {
        List<Task> temp = taskRepository.findAll();
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Task findTaskById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findTaskByName(String name) {
        List<Task> temp = taskRepository.findByName(name);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByRequesterId(int id) {
        List<Task> temp = taskRepository.findByRequesterid(id);
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByReward(int lowest, int highest) {
        return taskRepository.findByRewardBetween(lowest, highest);
    }

    @Override
    public String addTask(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age,IdStore taskId) {
        if (name == null || description == null)
            return "inputs are not enough";
        Task temp = new Task(name, description,reward,status,requesterid,type,restrictions,start_time,end_time,level,time_limitation,pay_time,area,usage,min_age,max_age);
        Task result=taskRepository.saveAndFlush(temp);
        taskId.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateTask(int taskId,String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time,int level, Float time_limitation, Float pay_time,String area,String usage,int min_age,int max_age) {
        Task task=taskRepository.findById(taskId);
        task.setAll(name, description,reward,status,requesterid,type,restrictions,start_time,end_time,level,time_limitation,pay_time,area,usage,min_age,max_age);
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String deleteTask(int id) {
        taskRepository.deleteById(id);
        return "succeed";
    }

    @Override
    public String createTaskResource(int taskId, String description, String options, MultipartFile file){

        try
        {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String strRead = new String(bytes);
            JSONObject obj = new JSONObject();
            obj.put("description",description);
            obj.put("options",options);
            obj.put("resources",strRead);
            Task task=taskRepository.findById(taskId);
            String filePath = "./xml/";
            String resource_link = "http://localhost:8880/xml/" + taskId + ".txt";
            String content = obj.toString();
            File dir = new File(filePath);
            // 一、检查放置文件的文件夹路径是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();// mkdirs创建多级目录
            }
            File checkFile = new File(filePath + taskId + ".txt");
            FileWriter writer = null;
            try {
                // 二、检查目标文件是否存在，不存在则创建
                if (!checkFile.exists()) {
                    checkFile.createNewFile();// 创建目标文件
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                writer = new FileWriter(checkFile);
                writer.append(content);
                writer.flush();
                task.setResource_link(resource_link);
                taskRepository.saveAndFlush(task);
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            }
            return "succeed";
        }
        catch (IOException e) {
            return "false";
        }
    }

    @Override
    public String readTaskResource(int taskId){
        Task task=taskRepository.findById(taskId);
        String fileName = "./xml/"+taskId+".txt";
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] filecontent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
        return new String(filecontent);
    }
}
