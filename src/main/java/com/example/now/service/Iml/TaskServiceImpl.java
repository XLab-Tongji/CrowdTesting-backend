package com.example.now.service.Iml;

import com.example.now.entity.Answer;
import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;
import com.example.now.entity.Task;
import com.example.now.repository.AnswerRepository;
import com.example.now.repository.SubtaskRepository;
import com.example.now.service.TaskService;
import com.example.now.repository.SubtaskRepository;
import com.example.now.repository.TaskRepository;
import com.example.now.service.RequesterService;

import java.io.*;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

import com.example.now.util.JsonUtil;
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
    private SubtaskRepository subTaskRepository;
    @Autowired
    private AnswerRepository answerRepository;
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
    public String addTask(String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age, IdStore taskId,Integer allNumber) {
        if (name == null || description == null)
            return "inputs are not enough";
        Task temp = new Task(name, description, reward, status, requesterid, type, restrictions, start_time, end_time, population, level, time_limitation, pay_time, area, usage, min_age, max_age, UNREVIEWED, allNumber);
        //初始化 answer
        String answer=JsonUtil.initializeAnswer(population,allNumber,type);
        temp.setAnswer(answer);
        temp.setNumberOfQuestions(allNumber/population);
        Task result = taskRepository.saveAndFlush(temp);
        taskId.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateTask(int taskId, String name, String description, Float reward, int status, Integer requesterid, String type, String restrictions, Timestamp start_time, Timestamp end_time, int population, int level, Float time_limitation, Float pay_time, String area, String usage, int min_age, int max_age) {
        Task task = taskRepository.findById(taskId);
        task.setAll(name, description, reward, status, requesterid, type, restrictions, start_time, end_time, population, level, time_limitation, pay_time, area, usage, min_age, max_age, task.getReviewed(), task.getAllNumber());
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
            int number_of_questions = urlArray.length();
            JSONArray optArray = new JSONArray(options);
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("opts", optArray);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:/Users/Administrator/Desktop/xml/";
            String resource_link = filePath + taskId + ".txt";
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
                task.setNumberOfQuestions(number_of_questions);
                JSONObject rest_of_questions = new JSONObject();
                JSONArray rest_of_question_list = new JSONArray();
                JSONObject rest_of_question = new JSONObject();
                rest_of_question.put("begin","1");
                rest_of_question.put("end",String.valueOf(number_of_questions));
                rest_of_question_list.put(rest_of_question);
                for(int i = 0; i < task.getPopulation()+1;i++){
                    rest_of_questions.put(String.valueOf(i), rest_of_question_list);
                }
                task.setRest_of_question(rest_of_questions.toString());
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
            int number_of_questions = urlArray.length();
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:/Users/Administrator/Desktop/xml/";
            String resource_link = filePath + taskId + ".txt";
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
                task.setNumberOfQuestions(number_of_questions);
                JSONObject rest_of_questions = new JSONObject();
                JSONArray rest_of_question_list = new JSONArray();
                JSONObject rest_of_question = new JSONObject();
                rest_of_question.put("begin","1");
                rest_of_question.put("end",String.valueOf(number_of_questions));
                rest_of_question_list.put(rest_of_question);
                for(int i = 0; i < task.getPopulation()+1;i++){
                    rest_of_questions.put(String.valueOf(i), rest_of_question_list);
                }
                task.setRest_of_question(rest_of_questions.toString());
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
        String filePath = "C:/Users/Administrator/Desktop/xml/";
        String resource_link = filePath + taskId + ".txt";
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
    public String readTaskResource(int taskId, int workerId) {
        Task task = taskRepository.findById(taskId);
        String fileName = task.getResource_link();
        List<Subtask> subTask = subTaskRepository.findByTaskId(taskId);
        Subtask theSubTask = new Subtask();
        for(int i=0;i<subTask.size();i++){
            if(subTask.get(i).getWorkerId() == workerId){
                theSubTask = subTask.get(i);
                break;
            }
        }
        int begin = theSubTask.getBegin() - 1;
        int end = theSubTask.getEnd();
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
    public String updateDistributedNumber(int taskId,Integer beginAt,Integer endAt){
        if(beginAt==null||endAt==null){
            return "failed";
        }
        //更新 distributedNumber 字段
        Task task=taskRepository.findById(taskId);
        int oldDistributedNumber=task.getDistributedNumber();
        int increment=endAt-beginAt+1;
        task.setDistributedNumber(oldDistributedNumber+increment);
        int newDistributedNumber=task.getDistributedNumber();
        int allNumber=task.getAllNumber();//应分配的总数
        //检测该 task 是否分配完成，若修改 isDistributed 字段
        if(newDistributedNumber==(allNumber*3/2)){
            task.setIsDistributed(1);//普通任务分配完成
        }
        else if(newDistributedNumber==allNumber){
            task.setIsDistributed(2);//所有任务分配完成
        }
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String mergeOrdinarySubtask(){
        // 1. 查找 isDistributed 字段为 1（代表普通任务分配完成） 且 isFinished 字段为 0（代表普通任务未完成） 的所有任务
        List<Task> tasks=taskRepository.findByIsDistributedAndIsFinished(1,0);
        for(Iterator<Task> it =tasks.iterator();it.hasNext();){
            // 2. 查找所有子任务 isFinished 字段为 1 的任务
            Task task=it.next();
            int numberOfQuestions=task.getNumberOfQuestions();
            List<Subtask> subtasks=subTaskRepository.findByTaskId(task.getId());
            int flag=0;
            for(Subtask subtask : subtasks){
                if(subtask.getIs_finished()==0){
                    flag=1;
                    break;
                }
            }
            if(flag==1){continue;}
            // 3. 将所有子任务的答案合并成两份，存入任务的 answer 字段
            List<Answer> answers=answerRepository.findByTaskIdOrderByBeginAt(task.getId());
            int temp=2;//temp 为普通任务的份数，目前为两份
            if (!mergeAndUpdateAnswer(task, numberOfQuestions, temp, answers)) return "failed";
            // 4. 将任务 isFinished 字段置为 1，代表该任务的所有普通子任务已完成，可以分配审核任务
            task.setIsFinished(1);
            taskRepository.saveAndFlush(task);
        }
        return "succeed";

    }

    @Override
    public String mergeAllSubtask(){
        // 1. 查找 isDistributed 字段为 2（代表所有任务分配完成）且 isFinished 字段为 1（代表普通任务已完成） 的所有任务
        List<Task> tasks=taskRepository.findByIsDistributedAndIsFinished(2,1);
        for(Iterator<Task> it =tasks.iterator();it.hasNext();){
            // 2. 查找所有子任务 isFinished 字段为 1 且 typeOfSubtask 字段为1（审核任务) 的任务
            Task task =it.next();
            int numberOfQuestions=task.getNumberOfQuestions();
            List<Subtask> subtasks=subTaskRepository.findByTaskId(task.getId());
            int flag=0;
            for(Subtask subtask: subtasks){
                if(subtask.getIs_finished()==0){
                    flag=1;
                    break;
                }
            }
            if (flag == 1) { continue; }
            for(Subtask subtask:subtasks){
                if(subtask.getType()==0){
                    flag=1;
                    break;
                }
            }
            if (flag == 1) { continue; }
            // 3. 将审核任务的答案合并成一份，添加到任务的 answer 字段
            int temp=1;//表示审核任务的答案只有一份
            List<Answer> answers=answerRepository.findByTaskIdOrderByBeginAt(task.getId());
            if (!mergeAndUpdateAnswer(task, numberOfQuestions, temp, answers)) return "failed";
            // 4. 将任务 isFinished 字段置为 2，代表该任务的所有子任务已完成
            task.setIsFinished(1);
            taskRepository.saveAndFlush(task);
        }
        return "succeed";
    }

    private boolean mergeAndUpdateAnswer(Task task, int numberOfQuestions, int temp, List<Answer> answers) {
        for(int i=0;i<temp;i++){
            String ultimateAnswer= JsonUtil.mergeJson(answers,answers.get(i).getAnswer(),answers.get(i).getEndAt(),numberOfQuestions);
            if(ultimateAnswer.equals("failed"))
                return false;
            //存入任务的 answer 字段
            if(i==0){
                JSONArray jsonArray=new JSONArray();
                JSONObject jsonObject=new JSONObject(ultimateAnswer);
                jsonArray.put(jsonObject);
                task.setAnswer(jsonArray.toString());
            }
            else{
                JSONArray jsonArray=new JSONArray(task.getAnswer());
                JSONObject jsonObject=new JSONObject(ultimateAnswer);
                jsonArray.put(jsonObject);
                task.setAnswer(jsonArray.toString());
            }
        }
        return true;
    }
}
