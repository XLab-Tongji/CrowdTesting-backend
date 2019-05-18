package com.example.now.repository;

import com.example.now.entity.TransactionInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Transaction information repository class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface TransactionInformationRepository extends JpaRepository<TransactionInformation, Integer> {
    /**
     * find transaction information by transaction information id
     *
     * @param id transaction information id
     * @return TransactionInformation
     */
    TransactionInformation findById(int id);

    /**
     * find transaction information by requester id
     *
     * @param id requester id
     * @return TransactionInformation<Answer>
     */
    List<TransactionInformation> findByRequesterId(int id);

    /**
     * find transaction information by task id
     *
     * @param id task id
     * @return TransactionInformation<Answer>
     */
    List<TransactionInformation> findByTaskId(int id);

    /**
     * delete transaction information by transaction information id
     *
     * @param id transaction information id
     */
    void deleteById(int id);
}
