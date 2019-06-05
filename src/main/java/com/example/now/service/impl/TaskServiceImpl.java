package com.example.now.service.impl;

import com.example.now.entity.*;
import com.example.now.repository.*;
import com.example.now.service.TaskService;
import com.example.now.repository.SubtaskRepository;
import com.example.now.service.RequesterService;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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

/**
 * Task service implementation class
 *
 * @author hyq
 * @date 2019/05/17
 */
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
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TransactionInformationRepository transactionInformationRepository;
    /**
     * 任务未审核的标志
     */
    private int unReviewed = 0;

    @Override
    public List<Task> findAllTask() {
        List<Task> temp = taskRepository.findAll();
        for (Task aTask : temp) {
            aTask.setInstitutionName(requesterService.findRequesterById(aTask.getRequesterId()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public Task findTaskById(int id) {
        Task aTask = taskRepository.findById(id);
        aTask.setInstitutionName(requesterService.findRequesterById(aTask.getRequesterId()).getInstitutionName());
        return aTask;
    }

    @Override
    public List<Task> findTaskByName(String name) {
        List<Task> temp = taskRepository.findByName(name);
        for (Task aTask : temp) {
            aTask.setInstitutionName(requesterService.findRequesterById(aTask.getRequesterId()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByRequesterId(int id) {
        List<Task> temp = taskRepository.findByRequesterId(id);
        for (Task aTask : temp) {
            aTask.setInstitutionName(requesterService.findRequesterById(aTask.getRequesterId()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public List<Task> findTaskByReward(int lowest, int highest) {
        List<Task> temp = taskRepository.findByRewardBetween(lowest, highest);
        for (Task aTask : temp) {
            aTask.setInstitutionName(requesterService.findRequesterById(aTask.getRequesterId()).getInstitutionName());
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public String addTask(String name, String description, Float reward, int status, Integer requesterId, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge, IdStore taskId) {
        if (name == null || description == null) {
            return "inputs are not enough";
        }
        int allNumber = 0;
        Task temp = new Task(name, description, reward, status, requesterId, type, restrictions, startTime, endTime, population, level, timeLimitation, payTime, area, usage, minAge, maxAge, unReviewed, allNumber);
        //初始化 answer 放在 createTaskResource() 中
        temp.setJudgedNumber(0);
        Task result = taskRepository.saveAndFlush(temp);
        taskId.setId(result.getId());
        return "succeed";
    }

    @Override
    public String updateTask(int taskId, String name, String description, Float reward, int status, Integer requesterId, String type, Integer restrictions, Timestamp startTime, Timestamp endTime, int population, int level, Float timeLimitation, Float payTime, String area, String usage, int minAge, int maxAge) {
        Task task = taskRepository.findById(taskId);
        task.setAll(name, description, reward, status, requesterId, type, restrictions, startTime, endTime, population, level, timeLimitation, payTime, area, usage, minAge, maxAge, task.getReviewed(), task.getAllNumber());
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String updateTaskDirectly(Task task){
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
            int numberOfQuestions = urlArray.length();
            JSONArray optArray = new JSONArray(options);
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("opts", optArray);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:/Users/Administrator/Desktop/xml/";
            String resourceLink = filePath + taskId + ".txt";
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
                    // 创建目标文件
                    checkFile.createNewFile();
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                OutputStream out=new FileOutputStream(checkFile);
                writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.append(content);
                writer.flush();
                task.setResourceLink(resourceLink);
                task.setNumberOfQuestions(numberOfQuestions);
                JSONObject restOfQuestions = new JSONObject();
                JSONArray restOfQuestionList = new JSONArray();
                JSONObject restOfQuestion = new JSONObject();
                restOfQuestion.put("begin","1");
                restOfQuestion.put("end",String.valueOf(numberOfQuestions));
                restOfQuestionList.put(restOfQuestion);
                for(int i = 0; i < task.getPopulation();i++){
                    restOfQuestions.put(String.valueOf(i), restOfQuestionList);
                }
                task.setRestOfQuestion(restOfQuestions.toString());
                task.setNumberOfQuestions(numberOfQuestions);
                //初始化 task 中的 answer 字段
                String answer=JsonUtil.initializeAnswer(task.getPopulation(),numberOfQuestions,task.getType());
                task.setAnswer(answer);
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
            int numberOfQuestions = urlArray.length();
            JSONObject obj = new JSONObject();
            obj.put("desc", description);
            obj.put("urls", urlArray);
            Task task = taskRepository.findById(taskId);
            String filePath = "C:/Users/Administrator/Desktop/xml/";
            String resourceLink = filePath + taskId + ".txt";
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
                    // 创建目标文件
                    checkFile.createNewFile();
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                OutputStream out=new FileOutputStream(checkFile);
                writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.append(content);
                writer.flush();
                task.setResourceLink(resourceLink);
                task.setNumberOfQuestions(numberOfQuestions);
                JSONObject restOfQuestions = new JSONObject();
                JSONArray restOfQuestionList = new JSONArray();
                JSONObject restOfQuestion = new JSONObject();
                restOfQuestion.put("begin","1");
                restOfQuestion.put("end",String.valueOf(numberOfQuestions));
                restOfQuestionList.put(restOfQuestion);
                for(int i = 0; i < task.getPopulation();i++){
                    restOfQuestions.put(String.valueOf(i), restOfQuestionList);
                }
                task.setRestOfQuestion(restOfQuestions.toString());
                task.setNumberOfQuestions(numberOfQuestions);
                //初始化 task 中的 answer 字段
                String answer=JsonUtil.initializeAnswer(task.getPopulation(),numberOfQuestions,task.getType());
                task.setAnswer(answer);
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
        String resourceLink = filePath + taskId + ".txt";
        String content = obj.toString();
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            // mkdirs创建多级目录
            dir.mkdirs();
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
            task.setResourceLink(resourceLink);
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
        task.setResourceLink(url);
        taskRepository.saveAndFlush(task);
        return "succeed";
    }

    @Override
    public String readTaskResource(int taskId) {
        Task task = taskRepository.findById(taskId);
        String fileName = task.getResourceLink();
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
        int allNumber=task.getAllNumber();
        //应分配的总数
        //检测该 task 是否分配完成，若修改 isDistributed 字段
        final int num3 = 3;
        final int num2 = 2;
        if(newDistributedNumber==(allNumber*num3/num2)){
            //普通任务分配完成
            task.setIsDistributed(1);
        }
        else if(newDistributedNumber==allNumber){
            //所有任务分配完成
            task.setIsDistributed(2);
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
                if(subtask.getIsFinished()==0){
                    flag=1;
                    break;
                }
            }
            if(flag==1){continue;}
            // 3. 将所有子任务的答案合并成两份，存入任务的 answer 字段
            List<Answer> answers=answerRepository.findByTaskIdOrderByBeginAt(task.getId());
            int temp=2;
            //temp 为普通任务的份数，目前为两份
            if (!mergeAndUpdateAnswer(task, numberOfQuestions, temp, answers)) {
                return "failed";
            }
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
                if(subtask.getIsFinished()==0){
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
            //表示审核任务的答案只有一份
            int temp=1;
            List<Answer> answers=answerRepository.findByTaskIdOrderByBeginAt(task.getId());
            if (!mergeAndUpdateAnswer(task, numberOfQuestions, temp, answers)) {
                return "failed";
            }
            // 4. 将任务 isFinished 字段置为 2，代表该任务的所有子任务已完成
            task.setIsFinished(1);
            taskRepository.saveAndFlush(task);
        }
        return "succeed";
    }

    private boolean mergeAndUpdateAnswer(Task task, int numberOfQuestions, int temp, List<Answer> answers) {
        for(int i=0;i<temp;i++){
            String ultimateAnswer= JsonUtil.mergeJson(answers,answers.get(i).getAnswer(),answers.get(i).getEndAt(),numberOfQuestions);
            if("failed".equals(ultimateAnswer)) {
                return false;
            }
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

    @Override
    public Boolean updateAnswer(int taskId,String partialAnswer,int numberOfTask){
        Task task=taskRepository.findById(taskId);
        String type=task.getType();
        JSONArray answers = new JSONArray(task.getAnswer());
        JSONArray answer = answers.getJSONArray(numberOfTask);
        JSONArray partialAnswerJson = new JSONArray(partialAnswer);
        for (int i = 0; i < partialAnswerJson.length(); i++) {
            //获取当前题号
            int index = partialAnswerJson.getJSONObject(i).getInt("index");

            //被更新的答案
            JSONObject updatedAnswer = answer.getJSONObject(index - 1);
            updatedAnswer.put("isFinished", true);
            updatedAnswer.put("content",partialAnswerJson.getJSONObject(i));
            updatedAnswer.getJSONObject("content").put("index", index);

            //存回
            answer.put(index - 1, updatedAnswer);
            }
        //存回
        answers.put(numberOfTask,answer);
        task.setAnswer(answers.toString());
        taskRepository.saveAndFlush(task);
        return true;
    }

    @Override
    public Boolean isFinishedForSimpleSubtasks(int taskId){
        Task task=taskRepository.findById(taskId);
        String empty = "[]";
        if(empty.equals(task.getAnswer())) {
            return false;
        }
        JSONArray answers=new JSONArray(task.getAnswer());
        int number=2;
        //TODO : 改为由 population 生成
        for(int i=0;i<number;i++){
            JSONArray answer=answers.getJSONArray(i);
            for(int j=0;j<answer.length();j++){
                if(!answer.getJSONObject(j).getBoolean("isFinished")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean isFinishedForAllSubtasks(int taskId){
        Task task=taskRepository.findById(taskId);
        String empty = "[]";
        if(empty.equals(task.getAnswer())) {
            return false;
        }
        JSONArray answers=new JSONArray(task.getAnswer());
        int number=task.getPopulation();
        for(int i=0;i<number;i++){
            JSONArray answer=answers.getJSONArray(i);
            for(int j=0;j<answer.length();j++){
                if(!answer.getJSONObject(j).getBoolean("isFinished")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void updateStatus(){
        //获得 isFinished 字段为 0 或 1 的任务
        List<Task> tasks0=taskRepository.findByStatus(0);
        List<Task> tasks1=taskRepository.findByStatus(1);
        //处理 isFinished 字段为 0 的任务(普通任务已完成)
        for(int i=0;i<tasks0.size();i++){
            if(isFinishedForSimpleSubtasks(tasks0.get(i).getId())){
                tasks0.get(i).setStatus(1);
            }
            if(isFinishedForAllSubtasks(tasks0.get(i).getId())){
                tasks0.get(i).setStatus(2);
                //更新 worker 的正确题数、做题总数、余额
                String ver1 = "ver1";
                String ver4 = "ver4";
                if(ver1.equals(tasks0.get(i).getType())||ver4.equals(tasks0.get(i).getType())){
                    calculateCorrectNumberAndBalanceForChoice(tasks0.get(i).getId());
                }
            }
            taskRepository.saveAndFlush(tasks0.get(i));
            //存回
        }
        //处理 isFinished 字段为 1 的任务(所有任务都已完成)
        for(int i=0;i<tasks1.size();i++){
            if(isFinishedForAllSubtasks(tasks1.get(i).getId())){
                tasks1.get(i).setStatus(2);
                //更新 worker 的正确题数和做题总数、余额
                String ver1 = "ver1";
                String ver4 = "ver4";
                if(ver1.equals(tasks1.get(i).getType())||ver4.equals(tasks1.get(i).getType())){
                    calculateCorrectNumberAndBalanceForChoice(tasks1.get(i).getId());
                }
            }
            taskRepository.saveAndFlush(tasks1.get(i));
            //存回
        }
    }

    @Override
    public void calculateCorrectNumberAndBalanceForChoice(int taskId){
        //更新 CorrectNumberAnswered 字段和 AllNumberAnswered 字段
        //1. 取出正确答案
        Task task=taskRepository.findById(taskId);
        JSONArray answers=new JSONArray(task.getAnswer());
        JSONArray correctAnswer=answers.getJSONArray(task.getPopulation()-1);
        //2. 获取 task 对应的所有 type=0 的 subtask(普通类型的子任务)
        List<Subtask> subtasks=subTaskRepository.findByTaskIdAndType(taskId,0);
        for(int i=0;i<subtasks.size();i++){
            Answer answer=answerRepository.findBySubtaskId(subtasks.get(i).getId());
            JSONArray currentAnswer=new JSONArray(answer.getAnswer());
            int correctNumber=0;
            //3. 比较 correctAnswer 和 currentAnswer
            for(int j=0;j<currentAnswer.length();j++){
                int index=currentAnswer.getJSONObject(j).getInt("index");
                if(currentAnswer.getJSONObject(j).getInt("ans")==correctAnswer.getJSONObject(index-1).getJSONObject("content").getInt("ans")){
                    correctNumber++;
                }
            }
            //4. 将correctNumber 写回对应 worker 的 CorrectNumberAnswered 字段
            //   并更新对应 worker 的 AllNumberAnswered 字段
            //   并更新 worker 的 balance 字段
            Worker worker=workerRepository.findById(answer.getWorkerId());
            worker.setCorrectNumberAnswered(worker.getCorrectNumberAnswered()+correctNumber);
            worker.setAllNumberAnswered(worker.getAllNumberAnswered()+answer.getNumber());

            //5. 记录收支情况
            worker.setBalance(worker.getBalance()+answer.getNumber()*task.getReward());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            TransactionInformation transactionInformation = new TransactionInformation(0,worker.getId(),task.getId(), now, (float) (task.getPopulation() * task.getReward() * 1.2));
            transactionInformationRepository.saveAndFlush(transactionInformation);
            workerRepository.saveAndFlush(worker);
        }
    }

    @Override
    public Boolean deleteExpiredAnswer(int taskId,int numberOfTask,int beginAt,int endAt){
        if(beginAt>endAt||numberOfTask<0) {
            return false;
        }
        //1. 获取对应 answer
        Task task=taskRepository.findById(taskId);
        JSONArray answers=new JSONArray(task.getAnswer());
        JSONArray answer=answers.getJSONArray(numberOfTask);
        //2. 将已过期答案的 isFinished 字段设为 false
        for(int i=beginAt-1;i<endAt;i++){
            JSONObject expiredAnswer=answer.getJSONObject(i);
            expiredAnswer.put("isFinished",false);
            answer.put(i,expiredAnswer);
        }
        //3. 存回
        answers.put(numberOfTask,answer);
        task.setAnswer(answers.toString());
        taskRepository.saveAndFlush(task);
        return true;
    }

    @Override
    public String getJudgedAnswer(int taskId,int number){
        //判断任务类型
        Task task=taskRepository.findById(taskId);
        String ver2 = "ver2";
        String ver3 = "ver3";
        if(!(ver2.equals(task.getType()))&&!(ver3.equals(task.getType()))) {
            return "failed";
        }
        //判断是否有足够多需要被判断的题
        if(task.getJudgedNumber()*task.getPopulation()+number*task.getPopulation()>task.getAllNumber()) {
            return "failed";
        }
        //获取答案
        JSONArray answers=new JSONArray(task.getAnswer());
        JSONArray outputAnswers=new JSONArray();
        for(int i=0;i<task.getPopulation();i++){
            JSONArray answer=answers.getJSONArray(i);
            JSONArray outputAnswer=new JSONArray();

            //计数器
            int count=0;
            for(int j=0;j<answer.length();j++){
                JSONObject singleAnswer=answer.getJSONObject(j);
                if(singleAnswer.getJSONObject("content").isNull("isCorrect")){
                    //将 isCorrect 放入 content 中
                    singleAnswer.getJSONObject("content").put("isCorrect",-1);
                    outputAnswer.put(singleAnswer);
                    count++;
                    if(count==number){
                        break;
                    }
                }
            }
            outputAnswers.put(outputAnswer);
        }
        //更新 judgedNumber 与 answer
        task.setJudgedNumber(task.getJudgedNumber()+number);
        task.setAnswer(outputAnswers.toString());
        //存回
        taskRepository.saveAndFlush(task);
        return outputAnswers.toString();
    }

    @Override
    public String judgeAnswer(int taskId,String answer){
        //判断任务类型
        Task task=taskRepository.findById(taskId);
        String ver2 = "ver2";
        String ver3 = "ver3";
        if(!(ver2.equals(task.getType()))&&!(ver3.equals(task.getType()))) {
            return "failed";
        }
        //更新 answer 字段
        JSONArray answers=new JSONArray(task.getAnswer());
        JSONArray inputAnswers=new JSONArray(answer);
        for(int i=0;i<task.getPopulation();i++){
            JSONArray singleAnswer=answers.getJSONArray(i);
            JSONArray inputAnswer=inputAnswers.getJSONArray(i);
            for(int j=0;j<inputAnswer.length();j++){
                int index=inputAnswer.getJSONObject(j).getInt("index");
                singleAnswer.put(index-1,inputAnswer);
            }
            answers.put(i,answer);
        }
        //存回
        task.setAnswer(answers.toString());
        //此任务的所有答案是否已经判断完成,若完成，则更新对应 worker 的正确题数，做题总数
        if(task.getJudgedNumber()*task.getPopulation()==task.getAllNumber()){
            calculateCorrectNumberAndBalanceForImage(taskId);
        }
        return "success";
    }

    @Override
    public void calculateCorrectNumberAndBalanceForImage(int taskId){
        //1. 获取所有答案
        Task task=taskRepository.findById(taskId);
        JSONArray answers=new JSONArray(task.getAnswer());
        //2. 获取 task 对应的所有 subtask
        List<Subtask> subtasks=subTaskRepository.findByTaskId(taskId);
        for(int i=0;i<subtasks.size();i++){
            int workerId=subtasks.get(i).getWorkerId();
            int numberOfTask=subtasks.get(i).getNumberOfTask();
            int begin=subtasks.get(i).getBegin();
            int end=subtasks.get(i).getEnd();
            Worker worker=workerRepository.findById(workerId);
            //3. 根据以上信息检验相对应的 isCorrect 字段,并修改对应 worker 的正确题数，做题总数
            JSONArray answer=answers.getJSONArray(numberOfTask);
            for(int j=begin-1;j<end;j++){
                int isCorrect=answer.getJSONObject(j).getJSONObject("content").getInt("isCorrect");
                if(isCorrect==1){
                    worker.setCorrectNumberAnswered(worker.getCorrectNumberAnswered()+1);
                }
                worker.setAllNumberAnswered(worker.getAllNumberAnswered()+1);
            }
            //4. 记录收支情况
            int number=end-begin+1;
            worker.setBalance(worker.getBalance()+number*task.getReward());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            TransactionInformation transactionInformation = new TransactionInformation(0,worker.getId(),task.getId(), now, (float) (task.getPopulation() * task.getReward() * 1.2));
            transactionInformationRepository.saveAndFlush(transactionInformation);
            //5. 存回
            workerRepository.saveAndFlush(worker);
        }
    }

    //TODO : 目前只生成一个 txt 文件，是否要改成一套答案一个 txt 文件
    //TODO : 放入检测 task 是否完成的函数中
    @Override
    public Boolean convertAnswerToFile(Integer taskId){
        //1. 检查 task 是否存在
        if(taskId<=0||!taskRepository.existsById(taskId))
            return false;
        //2. 准备存入文件内容
        Task task=taskRepository.findById(taskId.intValue());
        JSONArray answers=new JSONArray(task.getAnswer());
        int population=task.getPopulation();
        int numberOfQuestions=task.getNumberOfQuestions();
        //TODO : 待改
        //String filepath="C:/Users/Administrator/Desktop/answer/";
        String filepath="C:\\testdata\\";
        List<String> outputAnswer=new ArrayList<>();
        for(int i=0;i<population;i++){
            JSONArray currentAnswer=answers.getJSONArray(i);
            //每道题按 1,1 （前为题号，后为答案）方式存储
            for(int j=0;j<numberOfQuestions;j++){
                JSONObject content=currentAnswer.getJSONObject(j).getJSONObject("content");
                String singleAnswer=content.getInt("index")+","+content.getInt("ans");
                outputAnswer.add(singleAnswer);
            }
        }
        String resourceLink=filepath+taskId+".txt";
        //3. 检查放置文件的文件夹路径是否存在，不存在则创建
        File dir=new File(filepath);
        if(!dir.exists()){
            //创建多级目录
            dir.mkdirs();
        }
        File checkFile=new File(resourceLink);
        BufferedWriter writer=null;
        try{
            //4. 检查目标文件是否存在，不存在则创建
            if(!checkFile.exists()){
                checkFile.createNewFile();//创建目标文件
            }
            //5. 向目标文件写入内容
            OutputStream out=new FileOutputStream(checkFile);
            writer=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
            for(String str:outputAnswer){
                writer.append(str);
                writer.append(System.getProperty("line.separator"));
            }
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
