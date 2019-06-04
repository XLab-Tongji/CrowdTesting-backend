package com.example.now.repository;

import com.example.now.entity.WithdrawalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Withdrawal Information Repository class
 *
 * @author jjc
 * @date 2019/06/05
 */
public interface WithdrawalInformationRepository extends JpaRepository<WithdrawalInformation,Integer> {

    /**
     * find withdrawalInformation by workerId
     * @param workerId worker id
     * @return worker 提现信息的集合
     */
    List<WithdrawalInformation> findByWorkerId(int workerId);

    /**
     * find withdrawalInformation by requesterId
     * @param requesterId requester id
     * @return requester 提现信息的集合
     */
    List<WithdrawalInformation> findByRequesterId(int requesterId);
}
