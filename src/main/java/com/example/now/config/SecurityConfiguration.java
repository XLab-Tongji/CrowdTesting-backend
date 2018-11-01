package com.example.now.config;
import com.example.now.service.SecurityService;
import com.example.now.util.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration//指定为配置类
@EnableWebSecurity//指定为Spring Security配置类
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Bean
    UserDetailsService Service(){
        return new SecurityService();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(Service()).passwordEncoder(new PasswordEncoder() {

            //用户加密配置
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.encode((String)charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(MD5Util.encode((String)charSequence));
            }
        });
    }

    /*
    通过 authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护
    通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/addUser","/select","/task_datafind","/task_dataadd","/taskfindall","/taskfind/id","/taskfind/name","/taskfind/requesterid","/taskfind/reward","/taskadd","/taskdelete").permitAll()
                .antMatchers("/requesterinfo").hasRole("REQUESTER")
                .antMatchers("/workerinfo").hasRole("WORKER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/select")
                .permitAll()
                .and()
                .csrf().disable();
    }
}
