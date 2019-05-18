package com.example.now.entity;


/**
 * IdStore entity class
 *
 * @author jjc
 * @date 2019/05/17
 */
public class IdStore {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IdStore() {
        id=0;
    }

    public IdStore(int id) {
        this.id = id;
    }
}
