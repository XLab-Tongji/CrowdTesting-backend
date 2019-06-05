package com.example.now;

import com.example.now.entity.Answer;
import com.example.now.entity.IdStore;
import com.example.now.entity.Subtask;
import com.example.now.repository.AnswerRepository;
import com.example.now.repository.SubtaskRepository;
import com.example.now.repository.TaskRepository;
import com.example.now.service.AnswerService;
import com.example.now.service.TaskService;
import com.example.now.service.impl.AnswerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class AnswerServiceImplTest {
    @TestConfiguration
    static class AnswerServiceImplTestContextConfiguration{
        @Bean
        public AnswerService answerService(){
            return new AnswerServiceImpl();
        }
    }

    @Autowired
    private AnswerService answerService;

    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private SubtaskRepository subtaskRepository;
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        Answer answer = new Answer();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        answer.setAll(1, 1, currentTime, "[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]");
        answer.setId(1);
        answer.setSubtaskId(1);
        answer.setBeginAt(1);
        answer.setEndAt(2);
        answer.setNumber(2);
        List<Answer> answerList=new ArrayList<>();
        List<Answer> emptyAnswerList=new ArrayList<>();
        answerList.add(answer);

        //创建 mock 数据
        //AnswerServiceImpl.findAnswerById()
        Mockito.when(answerRepository.findAll()).thenReturn(answerList);
        //AnswerServiceImpl.findAnswerByWorkerId() 001-005
        Mockito.when(answerRepository.findByWorkerId(-1)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByWorkerId(0)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByWorkerId(1)).thenReturn(answerList);
        Mockito.when(answerRepository.findByWorkerId(101)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByWorkerId(Integer.MAX_VALUE)).thenReturn(emptyAnswerList);
        //AnswerServiceImpl.findAnswerByTaskId() 001-005
        Mockito.when(answerRepository.findByTaskId(-1)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByTaskId(0)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByTaskId(1)).thenReturn(answerList);
        Mockito.when(answerRepository.findByTaskId(101)).thenReturn(emptyAnswerList);
        Mockito.when(answerRepository.findByTaskId(Integer.MAX_VALUE)).thenReturn(emptyAnswerList);
        //AnswerServiceImpl.addAnswer() 001-005

    }

    @Test
    public void test_findAllAnswer(){
        Integer id = 1;
        List<Answer> answers = answerService.findAllAnswer();

        assertThat(answers.get(0).getId()).isEqualTo(id);
        System.out.println("yes");
    }

    @Test
    public void test_findAnswerByWorkerId_001(){
        Integer id=-1;
        List<Answer> answers=answerService.findAnswerByWorkerId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByWorkerId_002(){
        Integer id=0;
        List<Answer> answers=answerService.findAnswerByWorkerId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByWorkerId_003(){
        Integer id=1;
        List<Answer> answers=answerService.findAnswerByWorkerId(id);

        assertThat(answers.get(0).getId()).isEqualTo(id);
    }

    @Test
    public void test_findAnswerByWorkerId_004(){
        Integer id=101;
        List<Answer> answers=answerService.findAnswerByWorkerId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByWorkerId_005(){
        Integer id=Integer.MAX_VALUE;
        List<Answer> answers=answerService.findAnswerByWorkerId(id);

        assertThat(answers.size()).isEqualTo(0);
    }
    @Test
    public void test_findAnswerByTaskId_001(){
        Integer id=-1;
        List<Answer> answers=answerService.findAnswerByTaskId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByTaskId_002(){
        Integer id=0;
        List<Answer> answers=answerService.findAnswerByTaskId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByTaskId_003(){
        Integer id=1;
        List<Answer> answers=answerService.findAnswerByTaskId(id);

        assertThat(answers.get(0).getId()).isEqualTo(id);
    }

    @Test
    public void test_findAnswerByTaskId_004(){
        Integer id=101;
        List<Answer> answers=answerService.findAnswerByTaskId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    @Test
    public void test_findAnswerByTaskId_005(){
        Integer id=Integer.MAX_VALUE;
        List<Answer> answers=answerService.findAnswerByTaskId(id);

        assertThat(answers.size()).isEqualTo(0);
    }

    /**
     * 测试第一个参数 workerId 为空值的情况
     * workerId 各种情况（null,-1,0,101（假定不存在），MAX_VALUE）
     */
    @Test
    public void test_addAnswer_001_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(any(int.class))).thenReturn(Optional.of(subtask));
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(null,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }

    /**
     * 测试第一个参数 workerId 为负数的情况
     * workerId 各种情况（null,-1,0,101（假定不存在），MAX_VALUE）
     */
    @Test
    public void test_addAnswer_001_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(any(int.class))).thenReturn(Optional.of(subtask));
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(-1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第一个参数 workerId 为 0 的情况
     * workerId 各种情况（null,-1,0,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_001_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(any(int.class))).thenReturn(Optional.of(subtask));
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(0,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第一个参数 workerId 为 MAX_VALUE 的情况
     * workerId 各种情况（null,-1,0,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_001_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(Integer.MAX_VALUE,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第二个参数 taskId 为负数的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_002_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,-1,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第二个参数 taskId 为 0 的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_002_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,0,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第二个参数 taskId 为正数且数据库中存在的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_002_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,1,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第二个参数 taskId 为正数且数据库中存在的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_addAnswer_002_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,Integer.MAX_VALUE,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第三个参数 answer 为 “” 的情况
     * answer 各种情况（“”,不符合格式，正常情况）
     */
    @Test
    public void test_addAnswer_003_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,"",currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第三个参数 answer 的正常情况
     * answer 各种情况（“”,不符合格式，正常情况）
     */
    @Test
    public void test_addAnswer_003_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第六个参数 subtaskId 为负数的情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_004_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,-1,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第六个参数 subtaskId 为 0 的情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_004_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,0,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第六个参数 subtaskId 的正常情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_004_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第六个参数 subtaskId 的边界值情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_004_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(subtaskRepository.findById(Integer.MAX_VALUE)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,Integer.MAX_VALUE,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 为负数的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_addAnswer_005_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,-1,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第七个参数 beginAt 为 0 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_addAnswer_005_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,0,endAt);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第七个参数 beginAt 的正常情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_addAnswer_005_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 为 MAX_VALUE 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_addAnswer_005_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,Integer.MAX_VALUE,Integer.MAX_VALUE);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 大于 endAt 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_addAnswer_005_005() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,2,1);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 为负数的情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_006_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,-1);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 为 0 的情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_006_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,0);
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 的正常情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_006_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt);
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第八个参数 endAt 的正常情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_addAnswer_006_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask=new Subtask();
        subtask.setEnd(2);
        subtask.setBegin(1);
        subtask.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

        //when
        String message=answerService.addAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,Integer.MAX_VALUE);
        //then
        assertThat(message).isEqualTo("succeed");
    }
}
