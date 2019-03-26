package com.example.now.entity;

import java.util.HashMap;

public class ResultMap extends HashMap<String, Object> {


    public ResultMap() {

    }

    public ResultMap success(String code) {
        this.put("code", code);
        return this;
    }

    public ResultMap success() {
        return success("200");
    }

    public ResultMap fail(String code) {
        this.put("code", code);
        return this;
    }

    public ResultMap message(String message) {
        this.put("message", message);
        return this;
    }

    public ResultMap data(String key, Object obj) {
        this.put(key, obj);
        return this;
    }

    public ResultMap token(String token) {
        this.put("X_Auth_Token", token);
        return this;
    }
}

