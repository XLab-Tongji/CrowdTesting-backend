package com.example.now.service;

import com.example.now.entity.IdStore;
import com.example.now.entity.Worker;

import java.util.List;


/**
 * Worker service class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface WorkerService {
    /**
     * find all worker
     *
     * @return 返回值说明：worker列表
     */
    List<Worker> findAllWorker();

    /**
     * find worker by worker id
     *
     * @param id worker id
     * @return 返回值说明：worker
     */
    Worker findWorkerById(int id);

    /**
     * find worker by username
     *
     * @param username username
     * @return 返回值说明：worker
     */
    Worker findWorkerByUsername(String username);

    /**
     * create new worker
     *
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param withdrawnMethod withdrawn method
     * @param education education
     * @param workArea work area
     * @param age age
     * @param gender gender
     * @param major major
     * @param id get id of the new worker
     * @param school school
     * @return 返回值说明：成功或失败信息
     */
    String addWorker(String username, String name,String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, IdStore id, String school);

    /**
     * update worker information
     *
     * @param workerId worker id
     * @param username username
     * @param name name
     * @param teleNumber telephone number
     * @param eMail e-mail
     * @param withdrawnMethod withdrawn method
     * @param education education
     * @param workArea work area
     * @param age age
     * @param gender gender
     * @param major major
     * @param school school
     * @param correctNumberAnswered correct number answered
     * @param allNumberAnswered all number answered
     * @param overtimeNumber overtime number
     * @param balance balance
     * @return 返回值说明：成功或失败信息
     */
    String updateWorker(int workerId,String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String school, int correctNumberAnswered, int allNumberAnswered, int overtimeNumber, float balance);

    /**
     * update worker directly
     *
     * @param worker worker
     * @return 返回值说明：成功或失败信息
     */
    String updateWorkerDirectly(Worker worker);

    /**
     * delete worker by worker id
     *
     * @param id worker id
     * @return 返回值说明：成功或失败信息
     */
    String deleteWorker(int id);
}
