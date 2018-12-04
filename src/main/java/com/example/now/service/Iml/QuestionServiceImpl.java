package com.example.now.service.Iml;
import com.example.now.entity.*;
import com.example.now.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.now.service.QuestionService;

import java.util.List;
import java.util.ArrayList;
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionSelectedRepository optionSelectedRepository;
    @Autowired
    private OpenAnswerRepository openAnswerRepository;
    @Autowired
    private QuestionResourceRepository questionResourceRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Override
    public String addQuestionToTask(int taskId,String content,int resourceLoading,int type){
        Question question=new Question(content,resourceLoading,type,taskId);
        questionRepository.saveAndFlush(question);
        return "succeed";
    }

    @Override
    public String addOptionToQuestion(String content,int questionId, int openAnswerPermittion, int optionNumber){
        Option option=new Option(content,questionId,openAnswerPermittion,optionNumber);
        optionRepository.saveAndFlush(option);
        return "succeed";
    }

    @Override
    public List<QuestionDetail> seeAllQuestion(int taskId){
        List<QuestionDetail> questionDetails=new ArrayList<QuestionDetail>();
        List<Question> questions=questionRepository.findByTaskId(taskId);
        for (Question question:questions){
            QuestionDetail questionDetail=new QuestionDetail(question,optionRepository.findByQuestionId(question.getId()));
            if(question.getResource_loading()==1){
                List<Resource> resources=new ArrayList<Resource>();
                for(QuestionResource questionResource:questionResourceRepository.findByQuestionId(question.getId())){
                    resources.add(resourceRepository.findById(questionResource.getResourceId()));
                }
                questionDetail.setResources(resources);
            }
            questionDetails.add(questionDetail);
        }
        return questionDetails;
    }
    @Override
    public List<QuestionDetail> seeAllQuestion(int taskId,int workerId){
        List<QuestionDetail> questionDetails=new ArrayList<QuestionDetail>();
        List<Question> questions=questionRepository.findByTaskId(taskId);
        for (Question question:questions){
            QuestionDetail questionDetail=new QuestionDetail(question,optionRepository.findByQuestionId(question.getId()));
            if(question.getResource_loading()==1){
                List<Resource> resources=new ArrayList<Resource>();
                for(QuestionResource questionResource:questionResourceRepository.findByQuestionId(question.getId())){
                    resources.add(resourceRepository.findById(questionResource.getResourceId()));
                }
                questionDetail.setResources(resources);
            }
            List<OptionSelected> optionSelecteds=new ArrayList<OptionSelected>();
            for(Option option:optionRepository.findByQuestionId(question.getId())){
                OptionSelected optionSelected=optionSelectedRepository.findByOptionIdAndWorkerId(option.getId(),workerId);
                if(optionSelected!=null){
                    optionSelecteds.add(optionSelectedRepository.findByOptionIdAndWorkerId(option.getId(),workerId));
                }
            }
            questionDetail.setAnswers(openAnswerRepository.findByQuestionIdAndWorkerId(question.getId(),workerId));
            questionDetail.setSelecteds(optionSelecteds);
            questionDetails.add(questionDetail);
        }
        return questionDetails;
    }
    @Override
    public String selectOne(int optionId,int workerId){
        OptionSelected optionSelected=new OptionSelected(optionId,workerId);
        optionSelectedRepository.saveAndFlush(optionSelected);
        return "success";
    }
    @Override
    public String answerOne(int optionId,int workerId,String content){
        OpenAnswer openAnswer=new OpenAnswer(content,optionRepository.findById(optionId).getQuestionId(),workerId,optionId);
        openAnswerRepository.saveAndFlush(openAnswer);
        return "success";
    }
}
