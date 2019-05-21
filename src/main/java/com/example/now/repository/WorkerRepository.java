package com.example.now.repository;

import com.example.now.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Worker repository class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    /**
     * find worker by e-mail
     *
     * @param eMail e-mail
     * @return 返回值说明：worker
     */
    Worker findByEMail(String eMail);

    /**
     * find worker by worker id
     *
     * @param id worker id
     * @return 返回值说明：worker
     */
    Worker findById(int id);
}
