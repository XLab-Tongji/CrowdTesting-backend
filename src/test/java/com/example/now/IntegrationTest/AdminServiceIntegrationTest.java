package com.example.now.IntegrationTest;

import com.example.now.service.AdminService;
import com.example.now.service.AnswerService;
import com.example.now.service.impl.AdminServiceImpl;
import com.example.now.service.impl.AnswerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceIntegrationTest {
    @TestConfiguration
    static class AdminServiceImplIntegrationTestContextConfiguration{
        @Bean
        public AdminService adminService(){
            return new AdminServiceImpl();
        }
    }

    @Autowired
    private AdminService adminService;

    /**
     * 测试 reviewTask 函数的正常流，任务的 type 为 ver1-ver4
     */
    @Test
    public void IT_001_TD_001_002_001(){
        boolean bool=adminService.reviewTask(120);
        assertThat(bool).isEqualTo(true);
    }
    /**
     * 测试 reviewTask 函数的正常流，任务的 type 为 ver6
     */
    @Test
    public void IT_001_TD_001_001_001(){
        boolean bool=adminService.reviewTask(170);
        assertThat(bool).isEqualTo(true);
    }
}
