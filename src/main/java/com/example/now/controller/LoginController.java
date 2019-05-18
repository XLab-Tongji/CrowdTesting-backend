package com.example.now.controller;

import com.example.now.service.LoginService;
import com.example.now.entity.ResultMap;
import com.example.now.util.MD5Util;
import com.example.now.entity.TokenDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.now.repository.UserRepository;
import javax.validation.Valid;


/**
 * Login controller class
 *
 * @author hyq
 * @date 2019/05/17
 */
@RestController
public class LoginController {
    private final LoginService loginService;

    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    private ResultMap checkAccount(String username, String password, UserDetails loginDetail,String role) {
        if (username == null || password == null || role == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        if (loginDetail == null||!userRepository.findByUsername(username).getRole().equals(role)) {
            return new ResultMap().fail("404").message("账号不存在！");
        } else {
            String encodePassword = MD5Util.encode(password);
            if (!loginDetail.getPassword().equals(encodePassword)) {
                return new ResultMap().fail("401").message("密码错误！");
            }
        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultMap login(String username, String password,String role) {
        if (username == null || password == null || role == null) {
            return new ResultMap().fail("400").message("empty input");
        }
        UserDetails loginDetail = loginService.getLoginDetail(username);
        ResultMap ifLoginFail = checkAccount(username, password, loginDetail,role);
        if (ifLoginFail != null) {
            return ifLoginFail;
        }

        return new ResultMap().success().message("").token(loginService.generateToken((TokenDetail) loginDetail));
    }
}
