package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;


/**
 * Requester service class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface RequesterService {
    /**
     * find requester by requester id
     *
     * @param id requester id
     * @return 返回值说明：requester信息
     */
    Requester findRequesterById(int id);

    /**
     * find requester by username
     *
     * @param username username
     * @return 返回值说明：requester信息
     */
    Requester findRequesterByUsername(String username);

    /**
     * create new requester
     *
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param institutionName institution name
     * @param address address
     * @param payMethod pay method
     * @param gender gender
     * @param age age
     * @param id get id of the new requester
     * @return 返回值说明：成功或失败信息
     */
    String addRequester(String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, int age,IdStore id);

    /**
     * create new requester
     *
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param institutionName institution name
     * @param address address
     * @param payMethod pay method
     * @param gender gender
     * @param age age
     * @param requesterId requester id
     * @return 返回值说明：成功或失败信息
     */
    String updateRequester(int requesterId,String username, String name, String teleNumber, String eMail, String institutionName, String address, String payMethod, String gender, int age);

    /**
     * delete requester by requester id
     *
     * @param id requester id
     * @return 返回值说明：成功或失败信息
     */
    String deleteRequester(int id);
}