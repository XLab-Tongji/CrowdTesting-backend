package com.example.now.service;


/**
 * Admin service class
 *
 * @author jjc
 * @date 2019/05/17
 */
public interface AdminService {
    /**
     * check task reviewed or not
     *
     * @param id task id
     * @return boolean
     */
     boolean reviewTask(int id);
}