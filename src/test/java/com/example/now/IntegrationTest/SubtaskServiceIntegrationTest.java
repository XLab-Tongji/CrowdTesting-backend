package com.example.now.IntegrationTest;

import com.example.now.entity.Subtask;
import com.example.now.service.RequesterService;
import com.example.now.service.SubtaskService;
import com.example.now.service.impl.RequesterServiceImpl;
import com.example.now.service.impl.SubtaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SubtaskService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubtaskServiceIntegrationTest {
    @TestConfiguration
    static class SubtaskImplIntegrationTestContextConfiguration {
        @Bean
        public SubtaskService subtaskService() {
            return new SubtaskServiceImpl();
        }
    }

    @Autowired
    private SubtaskService subtaskService;

    /**
     * 测试 findSubtaskById 函数，subtask 存在的情况
     */
    @Test
    public void IT_005_TD_001_001_001(){
        Subtask subtask=subtaskService.findSubtaskById(136);
        System.out.println(subtask);
        assertThat(subtask).isNotNull();
    }
    /**
     * 测试 findSubtaskById 函数，subtask 不存在的情况
    @Test
    public void IT_005_TD_001_002_001(){
        Subtask subtask=subtaskService.findSubtaskById(1);
        System.out.println(subtask);
        assertThat(subtask).isNull();
    }*/
    /**
     * 测试 findSubtaskByWorkerId 函数，subtask 存在的情况
     */
    @Test
    public void IT_005_TD_002_001_001(){
        List<Subtask> subtasks=subtaskService.findSubtaskByWorkerId(69);
        System.out.println(subtasks);
        assertThat(subtasks).isNotNull();
    }
    /**
     * 测试 findSubtaskByWorkerId 函数，subtask 不存在的情况
     */
    @Test
    public void IT_005_TD_002_002_001(){
        List<Subtask> subtasks=subtaskService.findSubtaskByWorkerId(2);
        System.out.println(subtasks);
        assertThat(subtasks).isEmpty();
    }
    /**
     * 测试 findSubtaskByTaskId 函数，subtask 存在的情况
     */
    @Test
    public void IT_005_TD_003_001_001(){
        List<Subtask> subtasks=subtaskService.findSubtaskByTaskId(110);
        System.out.println(subtasks);
        assertThat(subtasks).isNotNull();
    }
    /**
     * 测试 findSubtaskByTaskId 函数，subtask 不存在的情况
     */
    @Test
    public void IT_005_TD_003_002_001(){
        List<Subtask> subtasks=subtaskService.findSubtaskByTaskId(1);
        System.out.println(subtasks);
        assertThat(subtasks).isEmpty();
    }
}
