package com.example.now.IntegrationTest;

import com.example.now.entity.Requester;
import com.example.now.entity.WithdrawalInformation;
import com.example.now.repository.UserRepository;
import com.example.now.service.LoginService;
import com.example.now.service.RequesterService;
import com.example.now.service.impl.LoginServiceImpl;
import com.example.now.service.impl.RequesterServiceImpl;
import com.example.now.util.TokenUtils;
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
 * RequesterService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequesterServiceIntegrationTest {
    @TestConfiguration
    static class RequesterImplIntegrationTestContextConfiguration {
        @Bean
        public RequesterService requesterService() {
            return new RequesterServiceImpl();
        }
    }

    @Autowired
    private RequesterService requesterService;

    /**
     * 测试 findRequesterById 函数，requester 存在的情况
     */
    @Test
    public void IT_003_TD_001_001_001(){
        Requester requester=requesterService.findRequesterById(35);
        System.out.println(requester);
        assertThat(requester).isNotNull();
    }
    /**
     * 测试 findRequesterById 函数，requester 不存在的情况
     */
    @Test
    public void IT_003_TD_001_002_001(){
        Requester requester=requesterService.findRequesterById(1);
        assertThat(requester).isNull();
    }

    /**
     * 测试 findRequesterByUsername 函数，requester 存在的情况
     */
    @Test
    public void IT_003_TD_002_001_001(){
        Requester requester=requesterService.findRequesterByUsername("Humbaba64@gmail.com");
        System.out.println(requester);
        assertThat(requester).isNotNull();
    }

    /**
     * 测试 findRequesterByUsername 函数，requester 不存在的情况
     */
    @Test
    public void IT_003_TD_002_002_001(){
        Requester requester=requesterService.findRequesterByUsername("zqy123");
        assertThat(requester).isNull();
    }

    /**
     * 测试 findWithdrawalInformationByRequesterId 函数，withdrawalInformation 存在的情况
     */
    @Test
    public void IT_003_TD_006_001_001(){
        List<WithdrawalInformation> withdrawalInformations=requesterService.findWithdrawalInformationByRequesterId(59);
        System.out.println(withdrawalInformations);
        assertThat(withdrawalInformations).isNotNull();
    }
    /**
     * 测试 findWithdrawalInformationByRequesterId 函数，withdrawalInformation 不存在的情况
     */
    @Test
    public void IT_003_TD_006_002_001(){
        List<WithdrawalInformation> withdrawalInformations=requesterService.findWithdrawalInformationByRequesterId(58);
        assertThat(withdrawalInformations).isEmpty();
    }
}
