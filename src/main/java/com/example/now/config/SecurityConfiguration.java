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
                .antMatchers("/answer/delete","/task/read-resource","/answer/find-all","/answer/find-by-answer-id","/answer/find-by-task-id","/answer/find-by-worker-id","/changePassword", "/worker/find-by-username", "/requester/find-by-username", "/task/find-by-name", "/task/find-by-requester-id", "/task/find-by-reward", "/taskData/find", "/personal-task/find-task-list", "/personal-task/find-worker-list", "/personal-task/delete").authenticated()       // 需携带有效 token
                .antMatchers("/task/add-resource","/task/add-resource-no-options","/task/add-resource-no-file","/requester/update", "/requester/find-myself", "/task/add", "/task/update", "/task/delete", "/task/find-my-task", "/taskData/add").hasRole("REQUESTER")
                .antMatchers("/answer/find-my-answer","/answer/add","/answer/update","/worker/update", "/worker/find-myself", "/personal-task/add", "/personal-task/find-my-task","/question/finish").hasRole("WORKER")
				.antMatchers("/admin","/task/delete","/worker/delete","/requester/delete").hasRole("ADMIN")
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
