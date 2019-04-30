package com.example.now.util;


import com.example.now.entity.Answer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;

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

    //合并一套答案的N个Json
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
}
