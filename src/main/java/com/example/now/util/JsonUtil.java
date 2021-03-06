package com.example.now.util;


import com.example.now.entity.Answer;
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
    //[[{"content":{"ans":0,"index":0,"isCorrect":-1},"isFinished":false}],[],[]]   单选题
    //[[{"content":{"ans":0,"index":0,"isCorrect":-1},"isFinished":false}],[],[]]   图像题
    public static String initializeAnswer(int population,int numberOfQuestions,String type){
        JSONArray answers=new JSONArray();
        JSONArray answer=new JSONArray();
       /* JSONObject content=new JSONObject();//用来存放答案
        if(type.equals("单选")){
            content.put("ans",0);
        }
        else{
            JSONArray ans=new JSONArray();
            content.put("ans",ans);
        }*/
        /*content.put("index",0);
        singleAnswer.put("content",content);
        singleAnswer.put("isFinished",false);//代表该答案是否做过*/
        //初始化一份完整答案
        for(int i=0;i<numberOfQuestions;i++){
            JSONObject singleAnswer=new JSONObject();
            JSONObject content=new JSONObject();//用来存放答案
            content.put("ans",0);
            /*if(type.equals("单选")){
                content.put("ans",0);
            }
            else{
                JSONArray ans=new JSONArray();
                content.put("ans",ans);
            }*/
            content.put("index",i+1);//从第一题开始
            content.put("isCorrect",-1);
            singleAnswer.put("content",content);//代表该答案是否正确（与第三套做对比），在判断正误时修改该值，0 为错误，1 为正确，-1 为未判断正误
            singleAnswer.put("isFinished",false);//代表该答案是否做过
            answer.put(singleAnswer);
        }
        //初始化 n 份完整答案
        for(int i=0;i<population;i++){
            answers.put(answer);
        }
        return answers.toString();
    }

    /**
     * 在图像标记类问题答案输出成 txt 文件的时候将数值归一化
     * @param content 结构为: {ans: [[{h:213,x:21,y:25}],[],[],[]], index: 1, isCorrect:-1} ver2
     *                       {ans: [[[{x:21,y:25},{}]],[],[],[]], index: 1, isCorrect:-1}  ver3
     * @return JSONObject
     */
    public static JSONObject customizeAnswer(JSONObject content,String type){
        int divisor=256;
        JSONArray ans=content.getJSONArray("ans");

        if("ver2".equals(type)) {//嵌套两层数组
            for (int i = 0; i < ans.length(); i++) {
                if (ans.getJSONArray(i).length() == 0) {
                    continue;
                }
                //遍历 length 大于 0 数组的每一个数值
                JSONObject singleAns = ans.getJSONArray(i).getJSONObject(0);
                Iterator iterator = singleAns.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Integer value = singleAns.getInt(key);
                    singleAns.put(key, (float) value / (float) divisor);
                }
                //更新
                ans.getJSONArray(i).put(singleAns);
            }
            //更新
            content.put("ans", ans);
            return content;
        }
        else if("ver3".equals(type)){//嵌套三层数组
            for(int i=0;i<ans.length();i++){
                if(ans.getJSONArray(i).length()==0){
                    continue;
                }
                //遍历 length 大于 0 数组的每一个数值
                JSONArray singleAns=ans.getJSONArray(i);
                for(int j=0;j<singleAns.length();j++){
                    if(singleAns.getJSONArray(j).length()==0){
                        continue;
                    }
                    //遍历 length 大于 0 数组的每一个数值
                    JSONArray thirdAns=singleAns.getJSONArray(j);
                    for(int index=0;index<thirdAns.length();index++){
                        JSONObject fourthAns=thirdAns.getJSONObject(index);
                        Iterator iterator=fourthAns.keys();
                        while (iterator.hasNext()){
                            String key=(String) iterator.next();
                            Integer value=fourthAns.getInt(key);
                            fourthAns.put(key,(float) value/(float) divisor);
                        }
                        thirdAns.put(index,fourthAns);
                    }
                    singleAns.put(j,thirdAns);
                }
                ans.put(i,singleAns);
            }
            content.put("ans",ans);
            return content;
        }
        else{
            return content;
        }
    }

}
