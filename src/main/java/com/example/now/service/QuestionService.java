package com.example.now.service;
import com.example.now.entity.QuestionDetail;
import java.util.List;
public interface QuestionService {
    String addQuestionToTask(int taskId,String content,int resourceLoading,int type);
    String addOptionToQuestion(String content,int questionId, int openAnswerPermittion, int optionNumber);
    List<QuestionDetail> seeAllQuestion(int taskId);
    List<QuestionDetail> seeAllQuestion(int taskId,int workerId);
    String selectOne(int optionId,int workerId);
    String answerOne(int optionId,int workerId,String content);
}
