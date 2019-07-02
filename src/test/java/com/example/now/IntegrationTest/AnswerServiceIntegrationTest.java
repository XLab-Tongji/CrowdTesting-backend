package com.example.now.IntegrationTest;

import com.example.now.entity.Answer;
import com.example.now.entity.IdStore;
import com.example.now.service.AnswerService;
import com.example.now.service.impl.AnswerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 对 AnswerServiceImpl 的集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceIntegrationTest {
    @TestConfiguration
    static class AnswerServiceImplIntegrationTestContextConfiguration{
        @Bean
        public AnswerService answerService(){
            return new AnswerServiceImpl();
        }
    }

    @Autowired
    private AnswerService answerService;

    @Before
    public void setUp() {
        //answer 的参数
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
    }
    /**
     * 测试 findAllAnswer 函数
     */
    @Test
    public void IT_002_TD_001_001_001(){
        List<Answer> answers=answerService.findAllAnswer();
        System.out.println(answers);
        assertThat(answers).isNotNull();
    }
    /**
     * 测试 findAnswerByWorkerId 函数，答案存在的情况
     */
    @Test
    public void IT_002_TD_003_001_001(){
        List<Answer> answers=answerService.findAnswerByWorkerId(69);
        System.out.println(answers);
        assertThat(answers).isNotNull();
    }
    /**
     * 测试 findAnswerByWorkerId 函数，答案不存在的情况
     */
    @Test
    public void IT_002_TD_003_002_001(){
        List<Answer> answers=answerService.findAnswerByWorkerId(2);
        System.out.println(answers);
        List<Answer> emptyAnswers=new ArrayList<>();
        assertThat(answers).isEqualTo(emptyAnswers);
    }
    /**
     * 测试 findAnswerById 函数，任务存在的情况
     */
    @Test
    public void IT_002_TD_002_001_001(){
        Answer answer=answerService.findAnswerById(137);
        System.out.println(answer);
        assertThat(answer).isNotNull();
    }
    /**
     * 测试 findAnswerById 函数，任务不存在的情况
     */
    @Test
    public void IT_002_TD_002_002_001(){
        Answer answer=answerService.findAnswerById(1);
        System.out.println(answer);
        assertThat(answer).isNull();
    }
    /**
     * 测试 findAnswerByTaskId 函数，答案存在的情况
     */
    @Test
    public void IT_002_TD_004_001_001(){
        List<Answer> answers=answerService.findAnswerByTaskId(110);
        System.out.println(answers);
        assertThat(answers).isNotNull();
    }
    /**
     * 测试 findAnswerByTaskId 函数，答案不存在的情况
     */
    @Test
    public void IT_002_TD_004_002_001(){
        List<Answer> answers=answerService.findAnswerByTaskId(1);
        System.out.println(answers);
        List<Answer> emptyAnswers=new ArrayList<>();
        assertThat(answers).isEqualTo(emptyAnswers);
    }

    @Test
    public void test_addAnswer(){

    }

    @Test
    public void test_updateAnswer(){

    }

    @Test
    public void test_deleteAnswer(){

    }

    @Test
    public void test_deleteAnswerBySubtaskId(){

    }

    /**
     * 测试 Boolean isFinished(int id) 函数，答案已完成的情况
     */
    @Test
    public void IT_002_TD_010_001_001(){
        Boolean bool=answerService.isFinished(146);
        assertThat(bool).isEqualTo(true);
    }
    /**
     * 测试 Boolean isFinished(int id) 函数，答案未完成或答案不存在的情况
     */
    @Test
    public void IT_002_TD_010_002_001(){
        Boolean bool=answerService.isFinished(137);
        assertThat(bool).isEqualTo(false);
    }
    /**
     * 测试 Boolean isFinished(Answer answer) 函数，答案已完成的情况
     */
    @Test
    public void test_isFinishedForAnswer(){
        //构造 Answer
        Answer answer = new Answer();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        answer.setAll(1, 1, currentTime, "[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]");
        answer.setId(1);
        answer.setSubtaskId(1);
        answer.setBeginAt(1);
        answer.setEndAt(2);
        answer.setNumber(2);

        Boolean bool=answerService.isFinished(answer);
        assertThat(bool).isEqualTo(true);
    }

    /**
     * 测试 findAnswerBySubtaskId 函数，答案存在的情况
     */
    @Test
    public void IT_002_TD_008_001_001(){
        String answer=answerService.findAnswerBySubtaskId(136,110);
        System.out.println(answer);
        assertThat(answer).isNotNull();
    }
}
