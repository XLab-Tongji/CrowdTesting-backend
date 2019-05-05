package com.example.now.util;


import com.example.now.entity.Answer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class JsonUtil {

    /*//将两个 json 答案合并（针对单选题）
    public static String mergeJsonForMultipleChoiceAnswer(String oldAnswer,String appendedAnswer){
        Gson gson=new Gson();
        List<MultipleChoiceAnswer> oldAnswers=gson.fromJson(oldAnswer,new TypeToken<List<MultipleChoiceAnswer>>(){}.getType());
        List<MultipleChoiceAnswer> appendedAnswers=gson.fromJson(appendedAnswer,new TypeToken<List<MultipleChoiceAnswer>>(){}.getType());
        oldAnswers.addAll(appendedAnswers);//将新答案加在现有答案之后
        return gson.toJson(oldAnswers,new TypeToken<List<MultipleChoiceAnswer>>(){}.getType());
    }*/

    //将一个json加到另一个json末尾（通用）
    public static String appendJson(String oldAnswer,String appendedAnswer){
        JSONArray oldAnswerJson=new JSONArray(oldAnswer);
        JSONArray appendedAnswerJson=new JSONArray(appendedAnswer);
        for(int i=0;i<appendedAnswerJson.length();i++){
            oldAnswerJson.put(appendedAnswerJson.getJSONObject(i));
        }
        return oldAnswerJson.toString();
    }


    //（已弃用）合并一套答案的N个Json
    public static String mergeJson(List<Answer> answers,String oldAnswer, Integer oldAnswerEndAt, Integer numberOfQuestions){
        if(numberOfQuestions==null)
            return "failed";
        //合并答案的末尾题数等于总题数时，返回答案
        if(oldAnswerEndAt.equals(numberOfQuestions))
            return oldAnswer;
        for(Iterator<Answer> it=answers.iterator();it.hasNext();){
            Answer answer=it.next();
            if(answer.getBeginAt()==oldAnswerEndAt+1) {
                mergeJson(answers, appendJson(oldAnswer, answer.getAnswer()), answer.getEndAt(), numberOfQuestions);
                it.remove();
                break;
            }
        }
        return "failed";
    }

    //初始化 task 中的 answer 字段
    //格式（以 population=3 为例）
    //[[{"content":{"ans":{},"index":0},"isFinished":false}],[],[]]   单选题
    //[[{"content":{"ans":[],"index":0},"isFinished":false}],[],[]]   图像题
    public static String initializeAnswer(int population,int allNumber,String type){
        int numberOfQuestions=allNumber/population;
        JSONArray answers=new JSONArray();
        JSONArray answer=new JSONArray();
        JSONObject singleAnswer=new JSONObject();
        JSONObject content=new JSONObject();//用来存放答案
        if(type.equals("单选")){
            JSONObject ans=new JSONObject();
            content.append("ans",ans);
        }
        else{
            JSONArray ans=new JSONArray();
            content.append("ans",ans);
        }
        content.append("index",0);
        singleAnswer.append("content",content);
        singleAnswer.append("isFinished",false);//代表该答案是否做过
        //初始化一份完整答案
        for(int i=0;i<numberOfQuestions;i++){
            content.put("index",i+1);//从第一题开始
            answer.put(singleAnswer);
        }
        //初始化 n 份完整答案
        for(int i=0;i<population;i++){
            answers.put(answer);
        }
        return answers.toString();
    }

}
