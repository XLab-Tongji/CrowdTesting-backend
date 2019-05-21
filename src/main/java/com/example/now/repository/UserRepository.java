package com.example.now.repository;

import com.example.now.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * User repository class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * find user by username
     *
     * @param username username
     * @return 返回值说明：user
     */
    User findByUsername(String username);
}
