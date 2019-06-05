package com.example.now;

import com.example.now.controller.AnswerController;
import com.example.now.entity.Answer;
import com.example.now.entity.ResultMap;
import com.example.now.repository.AnswerRepository;
import com.example.now.service.AnswerService;
import com.example.now.service.RequesterService;
import com.example.now.service.WorkerService;
import com.example.now.util.TokenUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = AnswerController.class)
//@WebMvcTest(controllers = AnswerController.class,secure=false)
//@AutoConfigureMockMvc()
public class AnswerControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession session;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private TokenUtils tokenUtils;
    @MockBean
    private RequesterService requesterService;
    @MockBean
    private WorkerService workerService;
    @MockBean
    private AnswerRepository answerRepository;

    @Before
    public void setUp(){
        //初始化MockMvc对象
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test_answerFindByTaskId_ifTaskIdIsNull() throws Exception {
            String taskId = null;

            ResultMap resultMap=new ResultMap().fail("400").message("empty input");

            Answer answer = new Answer();
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            answer.setAll(1, 1, currentTime, "[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]");
            answer.setId(1);
            List<Answer> answerList=new ArrayList<>();
            answerList.add(answer);
            //given(answerService.findAnswerByTaskId(taskId)).willReturn(answerList);

            this.mockMvc.perform(MockMvcRequestBuilders.get("/answer/find-by-task-id").param("taskId",taskId).accept(MediaType.APPLICATION_JSON)).andDo(print());

            //Assert.assertEquals(resultMap.toString(),result.getResponse().getContentAsString());
    }

    @Test
    public void test_answerFindByTaskId_ifTaskIdIsValid() throws Exception {
        String taskId = "5";



        Answer answer = new Answer();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        answer.setAll(1, 1, currentTime, "[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]");
        answer.setId(1);
        List<Answer> answerList=new ArrayList<>();
        answerList.add(answer);
        given(answerService.findAnswerByTaskId(Integer.parseInt(taskId))).willReturn(answerList);

        ResultMap resultMap=new ResultMap().success().data("Answers", answerList);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/answer/find-by-task-id")
                .param("taskId",taskId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //Assert.assertEquals(resultMap.toString(),result.getResponse().getContentAsString());
    }

    @Test
    public void test_answerFindAll() throws Exception{
        //given
        Answer answer = new Answer();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        answer.setAll(1, 1, currentTime, "[{\"ans\": 1, \"index\": 1}, {\"ans\": 1, \"index\": 2}, {\"ans\": 1, \"index\": 3}, {\"ans\": 1, \"index\": 4}]");
        answer.setId(1);
        answer.setSubtaskId(1);
        answer.setBeginAt(1);
        answer.setEndAt(2);
        answer.setNumber(2);
        List<Answer> answerList=new ArrayList<>();
        answerList.add(answer);
        when(answerService.findAllAnswer()).thenReturn(answerList);
        ResultMap resultMap=new ResultMap().success().data("Answers", answerList);

        //when
         mockMvc.perform(MockMvcRequestBuilders
                 .get("/answer/find-all")
                 .accept(MediaType.APPLICATION_JSON)
                 .characterEncoding("UTF-8")
                 .header("Content-Type","application/json;charset=UTF-8"))
                 .andDo(print())
                /*.andExpect(status().isOk())*/;
        //then
    }

}
