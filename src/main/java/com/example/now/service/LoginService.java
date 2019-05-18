package com.example.now.service;

import com.example.now.entity.TokenDetail;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Login service class
 *
 * @author hyq
 * @date 2019/05/17
 */
public interface LoginService {
    /**
     * get user detailed information by username
     *
     * @param username username
     * @return 返回值说明：用户个人信息
     */
    UserDetails getLoginDetail(String username);

    /**
     * generate token by token detail
     *
     * @param tokenDetail token detail
     * @return 返回值说明：生成token
     */
    String generateToken(TokenDetail tokenDetail);

}
