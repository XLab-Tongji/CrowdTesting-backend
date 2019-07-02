package com.example.now.IntegrationTest;
import com.example.now.service.UserService;
import com.example.now.service.WorkerService;
import com.example.now.service.impl.UserServiceImpl;
import com.example.now.service.impl.WorkerServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {
    @TestConfiguration
    static class UserImplIntegrationTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;


}
