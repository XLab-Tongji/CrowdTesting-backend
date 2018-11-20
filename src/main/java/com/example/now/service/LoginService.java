package com.example.now.service;

import com.example.now.entity.TokenDetail;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    UserDetails getLoginDetail(String username);

    String generateToken(TokenDetail tokenDetail);

}
