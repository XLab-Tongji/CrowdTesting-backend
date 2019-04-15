package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Task;
import com.example.now.service.TaskService;
import com.example.now.repository.TaskRepository;
import com.example.now.service.RequesterService;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.input.BOMInputStream;

import java.nio.charset.StandardCharsets;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RequesterService requesterService;
    //任务未审核的标志
    private int UNREVIEWED = 0;

    @Override
    public List<Task> findAllTask() {
        List<Task> temp = taskRepository.findAll();
        for (Task aTask : temp) {
            aTask.setInstitution_name(requesterService.findRequesterById(aTask.getRequesterid()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Task findTaskById(int id) {
        Task aTask = taskRepository.findById(id);
        aTask.setInstitution_name(requesterService.findRequesterById(aTask.getRequesterid()).getInstitutionName());
        return aTask;
    }

    @Override
    public List<Task> findTaskByName(String name) {
        List<Task> temp = taskRepository.findByName(name);
        for (Task aTask : temp) {
            aTask.setInstitution_name(requesterService.findRequesterById(aTask.getRequesterid()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByRequesterId(int id) {
        List<Task> temp = taskRepository.findByRequesterid(id);
        for (Task aTask : temp) {
            aTask.setInstitution_name(requesterService.findRequesterById(aTask.getRequesterid()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByReward(int lowest, int highest) {
        List<Task> temp = taskRepository.findByRewardBetween(lowest, highest);
        for (Task aTask : temp) {
            aTask.setInstitution_name(requesterService.findRequesterById(aTask.getRequesterid()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public String addTask(String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age, IdStore taskId) {
        if (name == null || description == null)
            return "inputs are not enough";
        Task temp = new Task(name, description, reward, status, requesterid, type, restrictions, start_time, end_time, level, time_limitation, pay_time, area, usage, min_age, max_age, UNREVIEWED);
        Task result = taskRepository.saveAndFlush(temp);
        taskId.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateTask(int taskId, String name, String description, Float reward, String status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age) {
        Task task = taskRepository.findById(taskId);
        task.setAll(name, description, reward, status, requesterid, type, restrictions, start_time, end_time, level, time_limitation, pay_time, area, usage, min_age, max_age, task.getReviewed());
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String deleteTask(int id) {
        taskRepository.deleteById(id);
        return "succeed";
    }

    @Override
    public String createTaskResource(int taskId, String description, String options, MultipartFile file) {

        try {
            // Get the file and save it somewhere
            byte[] bytes = new byte[(int) file.getSize()];
            BOMInputStream inputStream = new BOMInputStream(file.getInputStream());
            inputStream.read(bytes);
            inputStream.close();
            String strRead = new String(bytes);
            JSONArray urlArray = new JSONArray(strRead);
            JSONArray optArray = new JSONArray(options);
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("opts", optArray);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:\\Users\\Administrator\\Desktop\\xml\\";
            String resource_link = "C:\\Users\\Administrator\\Desktop\\xml\\" + taskId + ".txt";
            String content = obj.toString();
            File dir = new File(filePath);
            // 一、检查放置文件的文件夹路径是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();// mkdirs创建多级目录
            }
            File checkFile = new File(filePath + taskId + ".txt");
            BufferedWriter writer = null;
            try {
                // 二、检查目标文件是否存在，不存在则创建
                if (!checkFile.exists()) {
                    checkFile.createNewFile();// 创建目标文件
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                OutputStream out=new FileOutputStream(checkFile);
                writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.append(content);
                writer.flush();
                task.setResource_link(resource_link);
                taskRepository.saveAndFlush(task);
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            }
            return "succeed";
        } catch (IOException e) {
            return "false";
        }
    }

    @Override
    public String createTaskResource(int taskId, String description, MultipartFile file) {

        try {
            // Get the file and save it somewhere
            byte[] bytes = new byte[(int) file.getSize()];
            BOMInputStream inputStream = new BOMInputStream(file.getInputStream());
            inputStream.read(bytes);
            inputStream.close();
            String strRead = new String(bytes);
            JSONArray urlArray = new JSONArray(strRead);
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:\\Users\\Administrator\\Desktop\\xml\\";
            String resource_link = "C:\\Users\\Administrator\\Desktop\\xml\\" + taskId + ".txt";
            String content = obj.toString();
            File dir = new File(filePath);
            // 一、检查放置文件的文件夹路径是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();// mkdirs创建多级目录
            }
            File checkFile = new File(filePath + taskId + ".txt");
            BufferedWriter writer = null;
            try {
                // 二、检查目标文件是否存在，不存在则创建
                if (!checkFile.exists()) {
                    checkFile.createNewFile();// 创建目标文件
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                OutputStream out=new FileOutputStream(checkFile);
                writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.append(content);
                writer.flush();
                task.setResource_link(resource_link);
                taskRepository.saveAndFlush(task);
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            }
            return "succeed";
        } catch (IOException e) {
            return "false";
        }
    }

    @Override
    public String createTaskResource(int taskId, String description, String options) {

        JSONArray optArray = new JSONArray(options);
        JSONObject obj = new JSONObject();
        obj.put("desc", description);
        obj.put("opts", optArray);
        Task task = taskRepository.findById(taskId);
        String filePath = "C:\\Users\\Administrator\\Desktop\\xml\\";
        String resource_link = "C:\\Users\\Administrator\\Desktop\\xml\\" + taskId + ".txt";
        String content = obj.toString();
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + taskId + ".txt");
        BufferedWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            OutputStream out=new FileOutputStream(checkFile);
            writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
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

    @Override
    public String createTaskResource(int taskId, String url) {
        Task task = taskRepository.findById(taskId);
        task.setResource_link(url);
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String readTaskResource(int taskId) {
        Task task = taskRepository.findById(taskId);
        String fileName = "C:\\Users\\Administrator\\Desktop\\xml\\" + taskId + ".txt";
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] filecontent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            String str = new String(filecontent, 0, fileLength.intValue(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(str);
            return json.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
}
