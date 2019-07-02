package com.example.now.IntegrationTest;

import com.example.now.service.RequesterService;
import com.example.now.service.SecurityService;
import com.example.now.service.impl.RequesterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SecurityService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServiceIntegrationTest {

    @Autowired
    private SecurityService securityService;

    /**
     * 测试 loadUserByUsername 函数，用户存在的情况
     */
    @Test
    public void IT_004_TD_001_001_001(){
        UserDetails userDetails=securityService.loadUserByUsername("314279802@qq.com");
        System.out.println(userDetails);
    }
}
