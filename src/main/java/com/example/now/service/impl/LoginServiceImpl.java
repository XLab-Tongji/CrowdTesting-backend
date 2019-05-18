package com.example.now.service.impl;

import com.example.now.repository.UserRepository;
import com.example.now.service.LoginService;
import com.example.now.util.TokenUtils;
import com.example.now.entity.TokenDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


/**
 * Login service implementation class
 *
 * @author hyq
 * @date 2019/05/17
 */
@Service
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;
    private TokenUtils tokenUtils;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public UserDetails getLoginDetail(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String generateToken(TokenDetail tokenDetail) {
        return tokenUtils.generateToken(tokenDetail);
    }
}
