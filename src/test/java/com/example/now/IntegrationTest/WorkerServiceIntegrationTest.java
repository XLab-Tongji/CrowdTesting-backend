package com.example.now.IntegrationTest;

import com.example.now.entity.Worker;
import com.example.now.entity.WithdrawalInformation;
import com.example.now.service.TaskService;
import com.example.now.service.WorkerService;
import com.example.now.service.impl.TaskServiceImpl;
import com.example.now.service.impl.WorkerServiceImpl;
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
 * WorkerService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerServiceIntegrationTest {

    @TestConfiguration
    static class WorkerImplIntegrationTestContextConfiguration {
        @Bean
        public WorkerService workerService() {
            return new WorkerServiceImpl();
        }
    }

    @Autowired
    private WorkerService workerService;

    /**
     * 测试 findWorkerById 函数，worker 存在的情况
     */
    @Test
    public void IT_007_TD_001_001_001(){
        Worker worker=workerService.findWorkerById(1);
        System.out.println(worker);
        assertThat(worker).isNotNull();
    }

    /**
     * 测试 findWorkerByUsername 函数，worker 存在的情况
     */
    @Test
    public void IT_007_TD_002_001_001(){
        Worker worker=workerService.findWorkerByUsername("zqy@gmail.com");
        System.out.println(worker);
        assertThat(worker).isNotNull();
    }

    /**
     * 测试 findWithdrawalInformationByWorkerId 函数，withdrawalInformation 存在的情况
     */
    @Test
    public void IT_007_TD_007_001_001(){
        List<WithdrawalInformation> withdrawalInformations=workerService.findWithdrawalInformationByWorkerId(62);
        System.out.println(withdrawalInformations);
        assertThat(withdrawalInformations).isNotNull();
    }
    /**
     * 测试 findWithdrawalInformationByWorkerId 函数，withdrawalInformation 不存在的情况
     */
    @Test
    public void IT_007_TD_007_002_001(){
        List<WithdrawalInformation> withdrawalInformations=workerService.findWithdrawalInformationByWorkerId(1);
        assertThat(withdrawalInformations).isEmpty();
    }

}
