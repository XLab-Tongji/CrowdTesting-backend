package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.ResultMap;
import com.example.now.entity.Subtask;
import com.example.now.service.SubtaskService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubtaskControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private SubtaskService subtaskService;

    @InjectMocks
    private SubtaskController subtaskController;


    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUp() {
        //初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

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
        //SubtaskController.subtaskFindById UT_005_001 001-004
        Mockito.when(subtaskService.findSubtaskById(136)).thenReturn(subtask);

        //SubtaskController.subtaskFindByWorkerId 001-004
        Mockito.when(subtaskService.findSubtaskByWorkerId(-1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByWorkerId(0)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByWorkerId(1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByWorkerId(101)).thenReturn(subtaskList);
        Mockito.when(subtaskService.findSubtaskByWorkerId(Integer.MAX_VALUE)).thenReturn(emptySubtaskList);

        //SubtaskController.subtaskFindByTaskId 001-004
        Mockito.when(subtaskService.findSubtaskByTaskId(-1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByTaskId(0)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByTaskId(1)).thenReturn(emptySubtaskList);
        Mockito.when(subtaskService.findSubtaskByTaskId(110)).thenReturn(subtaskList);
        Mockito.when(subtaskService.findSubtaskByTaskId(Integer.MAX_VALUE)).thenReturn(emptySubtaskList);


    }

    @Test
    public void ut_005_001_001_001() throws Exception{
        String id = null;

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_001_002_001() throws Exception{
        String id = "-1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_001_002_002() throws Exception{
        String id = "0";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_001_002_003() throws Exception{
        String id = "20000000";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_001_003_001() throws Exception{
        String id = "1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_001_004_001() throws Exception{
        String id = "136";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id").param("id",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }


    @Test
    public void ut_005_002_001_001() throws Exception{
        String id = null;

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_002_002_001() throws Exception{
        String id = "-1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_002_002_002() throws Exception{
        String id = "0";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_002_002_003() throws Exception{
        String id = "20000000";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_002_003_001() throws Exception{
        String id = "1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_002_004_001() throws Exception{
        String id = "101";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id").param("workerId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_001_001() throws Exception{
        String id = null;

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_002_001() throws Exception{
        String id = "-1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_002_002() throws Exception{
        String id = "0";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_002_003() throws Exception{
        String id = "20000000";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_003_001() throws Exception{
        String id = "1";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }

    @Test
    public void ut_005_003_004_001() throws Exception{
        String id = "110";

        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id").param("taskId",id)
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andDo(print());
    }


    @Test
    public void ut_005_004_001_001() throws Exception{
//        String workerId = "null";
//        String taskId = "110";
//        String createdTime = "2019-5-20 20:00:00";
//        String deadline = "2019-5-20 20:00:00";
//        String updatedTime = "2019-5-20 20:00:00";
//        String isFinished = "0";
//        String type = "0";
//        String numberOfTask = "1";
//        String nowBegin = "1";
//        String id = new IdStore();
//        String begin = "1";
//        String end=1

//        String id = "null";
//        String createdTime = "2019-5-20 20:00:00";
//        String deadline = "2019-5-20 20:00:00";
//
//        mvc.perform(MockMvcRequestBuilders.get("/sub-task/update").param("id",id).param("createdTime",createdTime).param("deadline",deadline)
//                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
//                .andDo(print());
    }
}