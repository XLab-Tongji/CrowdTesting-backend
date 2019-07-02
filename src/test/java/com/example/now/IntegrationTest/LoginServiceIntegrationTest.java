package com.example.now.IntegrationTest;

import com.example.now.repository.UserRepository;
import com.example.now.service.AnswerService;
import com.example.now.service.LoginService;
import com.example.now.service.impl.AnswerServiceImpl;
import com.example.now.service.impl.LoginServiceImpl;
import com.example.now.util.TokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 对 AnswerServiceImpl 的集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceIntegrationTest {
    @TestConfiguration
    static class LoginServiceImplIntegrationTestContextConfiguration {
        public UserRepository userRepository;
        public TokenUtils tokenUtils;
        @Bean
        public LoginService loginService() {
            return new LoginServiceImpl(userRepository,tokenUtils);
        }
    }

    @Autowired
    private LoginService loginService;

    /**
     * 测 getLoginDetail 函数，用户名存在
     */
    @Test
    public void IT_003_TD_001_001_001(){

    }

    /**
     * 测 getLoginDetail 函数，用户名不存在
     */
    @Test
    public void IT_003_TD_001_002_001(){
    }
}
