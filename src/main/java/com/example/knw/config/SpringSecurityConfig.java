package com.example.knw.config;

import com.example.knw.handler.AuthenticationFailureHandler;
import com.example.knw.handler.JwtAccessDeniedHandler;
import com.example.knw.handler.JwtRequestFilterHandler;
import com.example.knw.service.impl.UserServiceImpl;
import com.example.knw.utils.TokenManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的设置
 *
 * @author qanna
 * @date 2021-03-31
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${spring.security.secret}")
    private String secret;

    @Autowired
    AuthenticationFailureHandler auEntryPoint;

    @Autowired
    JwtAccessDeniedHandler adHandler;

    @Autowired
    JwtRequestFilterHandler jwtRequestFilterHandler;

    @Autowired
    UserServiceImpl userService;

    TokenManagement tokenM = new TokenManagement();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //设置跨域策略
        httpSecurity
                .csrf().disable()

                //登录界面和注册界面可以访问
                .authorizeRequests()

                .antMatchers(
                        "/user/login",
                        "/user/register",
                        "/user/verify",
                        "/user/changePassword")
                .permitAll()


                .antMatchers(
                        "/team/addMember",
                        "/team/editTeam").hasAuthority("TEAM")

                .antMatchers("/team/createProject").hasAuthority("PROGRAM")

                .antMatchers(
                        "/team/getAuthority",
                        "/team/editAuthority",
                        "/team/createRole").hasAuthority("AUTH")

                .antMatchers("/team/transfer").hasAuthority("KPI")

                //其他所有都需要权限
                .anyRequest().authenticated()

                //加入异常处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(auEntryPoint)
                .accessDeniedHandler(adHandler)

                //设置session为无状态，不储存用户状态
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //设置jwt过滤器，使得每一次请求都经过token验证
                .and().addFilterBefore(jwtRequestFilterHandler, UsernamePasswordAuthenticationFilter.class);


    }



}
