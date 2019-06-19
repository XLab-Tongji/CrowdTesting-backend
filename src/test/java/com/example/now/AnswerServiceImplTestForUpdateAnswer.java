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
import com.example.now.util.JsonUtil;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class AnswerServiceImplTestForUpdateAnswer {
    @TestConfiguration
    static class AnswerServiceImplTestForUpdateAnswerContextConfiguration{
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
    public void setUp(){
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        IdStore id=new IdStore();
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        Subtask subtask_simple=new Subtask();
        subtask_simple.setEnd(2);
        subtask_simple.setBegin(1);
        subtask_simple.setId(1);
        Subtask subtask_forBeginAtIsMAXVALUE=new Subtask();
        subtask_forBeginAtIsMAXVALUE.setEnd(Integer.MAX_VALUE-1);
        subtask_forBeginAtIsMAXVALUE.setBegin(1);
        subtask_forBeginAtIsMAXVALUE.setId(1);
        //返回的 Answer
        Answer answerEntity = new Answer();
        answerEntity.setAll(workerId,taskId,currentTime,answer);
        answerEntity.setId(1);
        answerEntity.setSubtaskId(subtaskId);
        answerEntity.setEndAt(endAt);
        answerEntity.setBeginAt(beginAt);
        answerEntity.setNumber(1);
        Answer answerEntityForEndAtIsMaxValue =new Answer();
        answerEntityForEndAtIsMaxValue.setAll(workerId,taskId,currentTime,answer);
        answerEntityForEndAtIsMaxValue.setId(1);
        answerEntityForEndAtIsMaxValue.setSubtaskId(subtaskId);
        answerEntityForEndAtIsMaxValue.setEndAt(Integer.MAX_VALUE-1);
        answerEntityForEndAtIsMaxValue.setBeginAt(beginAt);
        answerEntityForEndAtIsMaxValue.setNumber(1);

        Mockito.when(subtaskRepository.findById(1)).thenReturn(subtask_simple);
        Mockito.when(subtaskRepository.findById(Integer.MAX_VALUE)).thenReturn(subtask_simple);
        Mockito.when(answerRepository.findById(1)).thenReturn(answerEntity);
        Mockito.when(answerRepository.findById(Integer.MAX_VALUE-1)).thenReturn(answerEntityForEndAtIsMaxValue);
        Mockito.when(answerRepository.findById(Integer.MAX_VALUE)).thenReturn(answerEntity);
        Mockito.when(answerRepository.saveAndFlush(any(Answer.class))).thenReturn(answerEntity);
        Mockito.when(subtaskRepository.saveAndFlush(any(Subtask.class))).thenReturn(subtask_simple);
        Mockito.when(taskService.updateAnswer(Mockito.any(int.class),Mockito.any(String.class),Mockito.any(int.class))).thenReturn(true);

    }
    /**
     * 测试第一个参数 workerId 为空值的情况
     * workerId 各种情况（null,-1,0,101（假定不存在），MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_001_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(null,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }

    /**
     * 测试第一个参数 workerId 为负数的情况
     * workerId 各种情况（null,-1,0,101（假定不存在），MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_001_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(-1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第一个参数 workerId 为 0 的情况
     * workerId 各种情况（null,-1,0,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_001_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(0,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第一个参数 workerId 为 MAX_VALUE 的情况
     * workerId 各种情况（null,-1,0,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_001_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(Integer.MAX_VALUE,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第二个参数 taskId 为负数的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_002_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,-1,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第二个参数 taskId 为 0 的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_002_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,0,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第二个参数 taskId 为正数且数据库中存在的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_002_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,1,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第二个参数 taskId 为边界值的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_002_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,Integer.MAX_VALUE,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第三个参数 answer 为 “” 的情况
     * answer 各种情况（“”,不符合格式，正常情况）
     */
    @Test
    public void test_updateAnswer_003_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,taskId,"",currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第三个参数 answer 的正常情况
     * answer 各种情况（“”,不符合格式，正常情况）
     */
    @Test
    public void test_updateAnswer_003_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第六个参数 subtaskId 为负数的情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_004_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,-1,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第六个参数 subtaskId 为 0 的情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_004_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,0,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第六个参数 subtaskId 的正常情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_004_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第六个参数 subtaskId 的边界值情况
     * subtaskId 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_004_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,Integer.MAX_VALUE,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 为负数的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_updateAnswer_005_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,-1,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第七个参数 beginAt 为 0 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_updateAnswer_005_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,0,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第七个参数 beginAt 的正常情况
     * beginAt 各种情况（-1,0,2，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_updateAnswer_005_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 为 MAX_VALUE 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_updateAnswer_005_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=Integer.MAX_VALUE-1;
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,Integer.MAX_VALUE,Integer.MAX_VALUE,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第七个参数 beginAt 大于 endAt 的情况
     * beginAt 各种情况（-1,0,1，MAX_VALUE,大于 endAt）
     */
    @Test
    public void test_updateAnswer_005_005() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=1;
        Integer endAt=1;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,2,1,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 为负数的情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_006_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=1;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,-1,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 为 0 的情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_006_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=1;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,0,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第八个参数 endAt 的正常情况
     * endAt 各种情况（-1,0,2，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_006_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第八个参数 endAt 的正常情况
     * endAt 各种情况（-1,0,1，MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_006_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=Integer.MAX_VALUE-1;
        int subtaskId=1;
        Integer beginAt=Integer.MAX_VALUE;
        Integer endAt=Integer.MAX_VALUE;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,Integer.MAX_VALUE,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第五个参数 id 为负数的情况
     * id 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_007_001() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=-1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第五个参数 id 为 0 的情况
     * id 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_007_002() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=0;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("invalid parameter");
    }
    /**
     * 测试第二个参数 taskId 为正数且数据库中存在的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_007_003() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=1;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;

        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
    /**
     * 测试第二个参数 taskId 为边界值的情况
     * taskId 各种情况（-1,0,1,MAX_VALUE）
     */
    @Test
    public void test_updateAnswer_007_004() {
        //given
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int workerId=1;
        int taskId=1;
        String answer="[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]";
        int id=Integer.MAX_VALUE;
        int subtaskId=1;
        Integer beginAt=2;
        Integer endAt=2;
        //when
        String message=answerService.updateAnswer(1,taskId,answer,currentTime,id,subtaskId,beginAt,endAt,"");
        //then
        assertThat(message).isEqualTo("succeed");
    }
}
