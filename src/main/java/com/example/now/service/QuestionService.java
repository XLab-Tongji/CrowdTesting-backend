package com.example.now.service;
import com.example.now.entity.QuestionDetail;
import com.example.now.entity.Question;
import com.example.now.entity.Option;
import java.util.List;
import com.example.now.entity.IdStore;
public interface QuestionService {
    String addQuestionToTask(int taskId,String content,int resourceLoading,int type,int compulsory,int questionNumber,IdStore made);
    String addOptionToQuestion(String content,Integer questionId, Integer openAnswerPermission , Integer optionNumber,IdStore made);
    List<QuestionDetail> seeAllQuestion(int taskId);
    List<QuestionDetail> seeAllQuestion(int taskId,int workerId);
    List<QuestionDetail> seeAllAnswer(int taskId);
    String selectOne(int optionId,int workerId);
    String answerOne(int optionId,int workerId,String content);
    int addResource(int questionId,int resourceId);
    String answer(int questionId,int workerId,String content);
}
