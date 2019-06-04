package com.example.now.controller;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubtaskControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }


    @Test
    public void subtaskFindById() throws Exception{
        String example = "{\"id\":136}";
        mvc.perform(MockMvcRequestBuilders.get("/sub-task/find-by-sub-task-id?id=136")
                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
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
        String json="{\"subtaskId\":\"null\"}";
        mvc.perform(MockMvcRequestBuilders.get("/sub-task/read-subtask-resource")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes())//传json参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
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
    }

    @Test
    public void subtaskUpdate() throws Exception{
    }

    @Test
    public void subtaskDelete() throws Exception{
    }
}