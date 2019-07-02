package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;
import com.example.now.entity.Subtask;
import com.example.now.entity.Task;
import com.example.now.repository.RequesterRepository;
import com.example.now.repository.SubtaskRepository;
import com.example.now.repository.TaskRepository;
import com.example.now.service.impl.SubtaskServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.Console;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubtaskServiceTest {

    @TestConfiguration
    static class SubtaskServiceImplTestContextConfiguration{
        @Bean
        public SubtaskService subtaskService(){
            return new SubtaskServiceImpl();
        }
    }

    @Autowired
    private SubtaskService subtaskService;

    @MockBean
    private SubtaskRepository subtaskRepository;
    @MockBean
    private TaskService taskService;

    @Before
    public void setUp() {
        Subtask subtask = new Subtask();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        subtask.setAll(1, 3, currentTime, currentTime, currentTime, 0 ,0,101, 110, 1 ,1);
        subtask.setId(136);
        subtask.setUsername("hyq");
        subtask.setTitle("猫和狗的图片标记问题");
        subtask.setTaskType("图片标记");
        List<Subtask> subtaskList=new ArrayList<>();
        List<Subtask> emptySubtaskList=new ArrayList<>();
        subtaskList.add(subtask);

        //创建 mock 数据
        //构建stub
        //SubtaskController.subtaskFindById UT_005_001 001-002
        Mockito.when(subtaskRepository.findById(1)).thenReturn(null);
        Mockito.when(subtaskRepository.findById(136)).thenReturn(subtask);

        //SubtaskController.subtaskFindByWorkerId 001-002
        Mockito.when(subtaskRepository.findByWorkerId(1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskRepository.findByWorkerId(101)).thenReturn(subtaskList);

        //SubtaskController.subtaskFindByTaskId 001-002
        Mockito.when(subtaskRepository.findByTaskId(1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskRepository.findByTaskId(110)).thenReturn(subtaskList);
    }

    @Test
    public void ut_004_001_001_001(){
        Integer id = 1;
        Subtask subtask = subtaskService.findSubtaskById(id);
        assertThat(subtask).isEqualTo(null);
    }

    @Test
    public void ut_004_001_002_001(){
        Integer id = 136;
        Subtask subtask = subtaskService.findSubtaskById(id);
        System.out.print(subtask);
    }

    @Test
    public void ut_004_002_001_001(){
        Integer id = 1;
        List<Subtask> subtasks = subtaskService.findSubtaskByWorkerId(id);
        System.out.print(subtasks);
    }

    @Test
    public void ut_004_002_002_001(){
        Integer id = 101;
        List<Subtask> subtasks = subtaskService.findSubtaskByWorkerId(id);
        System.out.print(subtasks);
    }

    @Test
    public void ut_004_003_001_001(){
        Integer id = 1;
        List<Subtask> subtasks = subtaskService.findSubtaskByTaskId(id);
        System.out.print(subtasks);
    }

    @Test
    public void ut_004_003_002_001(){
        Integer id = 110;
        List<Subtask> subtasks = subtaskService.findSubtaskByTaskId(id);
        System.out.print(subtasks);
    }
}