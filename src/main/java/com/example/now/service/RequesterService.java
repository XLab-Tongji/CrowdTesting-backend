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
     * @return Requester
     */
    Requester findRequesterById(int id);

    /**
     * find requester by username
     *
     * @param username username
     * @return Requester
     */
    Requester findRequesterByUsername(String username);

    /**
     * create new requester
     *
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param researchField research field
     * @param institutionName institution name
     * @param address address
     * @param payMethod pay method
     * @param gender gender
     * @param age age
     * @param id get id of the new requester
     * @return String
     */
    String addRequester(String username, String name, String teleNumber, String eMail, String researchField, String institutionName, String address, String payMethod, String gender, int age,IdStore id);

    /**
     * create new requester
     *
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param researchField research field
     * @param institutionName institution name
     * @param address address
     * @param payMethod pay method
     * @param gender gender
     * @param age age
     * @param requesterId requester id
     * @return String
     */
    String updateRequester(int requesterId,String username, String name, String teleNumber, String eMail, String researchField, String institutionName, String address, String payMethod, String gender, int age);

    /**
     * delete requester by requester id
     *
     * @param id requester id
     * @return String
     */
    String deleteRequester(int id);
}