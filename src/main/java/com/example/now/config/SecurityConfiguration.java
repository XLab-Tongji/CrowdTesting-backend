package com.example.now.config;

import com.example.now.filter.AuthenticationTokenFilter;
import com.example.now.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//指定为配置类
@EnableWebSecurity//指定为Spring Security配置类
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Bean
    UserDetailsService Service() {
        return new SecurityService();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/question/see-his-answer","/question/see-all-question","/changePassword", "/worker/find-by-username", "/requester/find-by-username", "/task/find-by-name", "/task/find-by-requester-id", "/task/find-by-reward", "/taskData/find", "/personal-task/find-task-list", "/personal-task/find-worker-list", "/personal-task/delete").authenticated()       // 需携带有效 token
                .antMatchers("/question/add-resource","/image/add-album","/image/change-album-name","/image/findImages","/image/add-image","/image/delete-image","/question/see-all-answer","/put-image","/question/add-question","/question/add-option","/requester/update", "/requester/find-myself", "/task/add", "/task/update", "/task/delete", "/task/find-my-task", "/taskData/add").hasRole("REQUESTER")
                .antMatchers("/question/answer-one","/question/select-one","/question/see-my-answer","/worker/update", "/worker/find-myself", "/personal-task/add", "/personal-task/find-my-task").hasRole("WORKER")
                .anyRequest().permitAll()       // 允许所有请求通过
                .and()
                // 配置被拦截时的处理
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)   // 添加 token 无效或者没有携带 token 时的处理
                .accessDeniedHandler(this.accessDeniedHandler)      //添加无权限时的处理
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session


        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
