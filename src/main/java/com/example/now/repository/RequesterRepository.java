package com.example.now.repository;

import com.example.now.entity.Requester;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Requester repository class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface RequesterRepository extends JpaRepository<Requester, Integer> {
    /**
     * find requester by e-mail
     *
     * @param eMail e-mail
     * @return 返回值说明：requester
     */
    Requester findByEMail(String eMail);

    /**
     * find requester by requester id
     *
     * @param id requester id
     * @return 返回值说明：requester
     */
    Requester findById(int id);
}
