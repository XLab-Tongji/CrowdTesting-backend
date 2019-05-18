package com.example.now.entity;


/**
 * The implementation class of token detail interface class
 *
 * @author hyq
 * @date 2019/05/17
 */
public class TokenDetailImpl implements TokenDetail {

    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}

