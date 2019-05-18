package com.example.now.service;


/**
 * User service class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface UserService {
    /**
     * register
     *
     * @param username username
     * @param password password
     * @param role role
     * @return String
     */
    String register(String username, String password, String role);

    /**
     * change password
     *
     * @param name username
     * @param password password
     * @return String
     */
    String changePassword(String name, String password);
}
