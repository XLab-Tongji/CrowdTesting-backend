package com.example.now.controller;

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

import static org.junit.Assert.*;

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
        //SubtaskController.subtaskFindById 001-004
        //Mockito.when(subtaskService.findSubtaskById(nullInt)).thenReturn("");
        //Mockito.when(subtaskService.findSubtaskById(0)).thenReturn(emptyAnswerList);
        //Mockito.when(subtaskService.findSubtaskById(1)).thenReturn(subtask);
        //Mockito.when(subtaskService.findSubtaskById(136)).thenReturn(subtask);
        //Mockito.when(subtaskService.findByWorkerId(Integer.MAX_VALUE)).thenReturn(emptyAnswerList);

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
        Mockito.when(subtaskService.findSubtaskByTaskId(101)).thenReturn(subtaskList);
        Mockito.when(subtaskService.findSubtaskByTaskId(Integer.MAX_VALUE)).thenReturn(emptySubtaskList);
    }


    @Test
    public void subtaskFindById() throws Exception{
//        String example = "{\"id\":136}";
//        //mock 行为
//        Mockito.doReturn("200").when(subtaskService).findSubtaskById(Mockito.any());
//        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id?id=136")
//                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void subtaskFindByTaskId() throws Exception{
        String json="{\"taskId\":\"-1\"}";
        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-task-id")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes())//传json参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void subtaskFindByWorkerId() throws Exception{
        String json="{\"workerId\":\"-1\"}";
        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-worker-id")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes())//传json参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void subtaskResourceFind() throws Exception{
//        String json="{\"subtaskId\":\"null\"}";
//        mvc.perform(MockMvcRequestBuilders.get("/sub-task/read-subtask-resource")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .content(json.getBytes())//传json参数
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
    }

    /*@Test
    public void subtaskAdd() throws Exception{
        String example = "{\"taskId\":1}";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/sub-task/add/");
        request.param("taskId","1");
        request.param("numberWanted","1");
        request.param("createdTime","2019-06-03 14:40:40");
        request.param("deadline","2019-06-03 14:40:40");
        mvc.perform(request
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }*/

    @Test
    public void subtaskUpdate() throws Exception{
    }

    @Test
    public void subtaskDelete() throws Exception{
    }
}