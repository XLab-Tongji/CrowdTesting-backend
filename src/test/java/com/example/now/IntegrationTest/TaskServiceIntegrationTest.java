package com.example.now.IntegrationTest;

import com.example.now.entity.Task;
import com.example.now.service.RequesterService;
import com.example.now.service.TaskService;
import com.example.now.service.impl.RequesterServiceImpl;
import com.example.now.service.impl.TaskServiceImpl;
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
 * TaskService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceIntegrationTest {
    @TestConfiguration
    static class TaskImplIntegrationTestContextConfiguration {
        @Bean
        public TaskService taskService() {
            return new TaskServiceImpl();
        }
    }

    @Autowired
    private TaskService taskService;

    /**
     * 测试 findAllTask（）函数
     */
    @Test
    public void IT_006_TD_001_001_001(){
        List<Task> taskList=taskService.findAllTask();
        assertThat(taskList).isNotNull();
    }

    /**
     * 测试 findTaskById（）函数,task 存在的情况
     */
    @Test
    public void IT_006_TD_002_001_001(){
        Task task=taskService.findTaskById(164);
        System.out.println(task);
        assertThat(task).isNotNull();
    }

    /**
     * 测试 findTaskByName（）函数,task 存在的情况
     */
    @Test
    public void IT_006_TD_003_001_001(){
        List<Task> task=taskService.findTaskByName("边缘标注");
        System.out.println(task);
        assertThat(task).isNotNull();
    }
    /**
     * 测试 findTaskByName（）函数,task 不存在的情况
     */
    @Test
    public void IT_006_TD_003_002_001(){
        List<Task> task=taskService.findTaskByName("边缘");
        assertThat(task).isEmpty();
    }
    /**
     * 测试 findTaskByRequesterId（）函数,task 存在的情况
     */
    @Test
    public void IT_006_TD_004_001_001(){
        List<Task> task=taskService.findTaskByRequesterId(140);
        System.out.println(task);
        assertThat(task).isNotNull();
    }
    /**
     * 测试 findTaskByRequesterId（）函数,task 不存在的情况
     */
    @Test
    public void IT_006_TD_004_002_001(){
        List<Task> task=taskService.findTaskByRequesterId(1);
        assertThat(task).isEmpty();
    }
    /**
     * 测试 isFinishedForSimpleSubtasks（）函数,task 所有普通任务完成的情况
     */
    @Test
    public void IT_006_TD_014_001_001(){
        Boolean bool=taskService.isFinishedForSimpleSubtasks(173);
        assertThat(bool).isEqualTo(true);
    }
    /**
     * 测试 isFinishedForSimpleSubtasks（）函数,task 所有普通任务未完成的情况
     */
    @Test
    public void IT_006_TD_014_002_001(){
        Boolean bool=taskService.isFinishedForSimpleSubtasks(171);
        assertThat(bool).isEqualTo(false);
    }
    /**
     * 测试 isFinishedForAllSubtasks（）函数,task 所有任务完成的情况
     */
    @Test
    public void IT_006_TD_015_001_001(){
        Boolean bool=taskService.isFinishedForAllSubtasks(173);
        assertThat(bool).isEqualTo(true);
    }
    /**
     * 测试 isFinishedForAllSubtasks（）函数,task 所有任务未完成的情况
     */
    @Test
    public void IT_006_TD_015_002_001(){
        Boolean bool=taskService.isFinishedForAllSubtasks(171);
        assertThat(bool).isEqualTo(false);
    }
    /**
     * 测试 getCorrectRateForTask（）函数,task 存在的情况
     */
    @Test
    public void IT_006_TD_021_001_001(){
        String string=taskService.getCorrectRateForTask(200);
        System.out.println(string);
        assertThat(string).isNotNull();
    }
    /**
     * 测试 getCorrectRateForTask（）函数,task 不存在的情况
     */
    @Test
    public void IT_006_TD_021_002_001(){
        String string=taskService.getCorrectRateForTask(1);
        assertThat(string).isEqualTo("task does not exist");
    }
}
