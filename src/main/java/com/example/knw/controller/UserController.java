package com.example.knw.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.exception.AuthorizeException;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.UserService;
import com.example.knw.service.impl.EmailService;
import com.example.knw.utils.JsonUtils;
import com.example.knw.utils.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 *
 * @author qanna
 * @date 2021-03-15
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    //private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtils tokenUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    JsonUtils json;

    @RequestMapping("/getUser")
    @ResponseBody
    public Result getUser(@RequestBody String jsonString)
            throws NoSuchUserException, JsonProcessingException {
        boolean isRemember = json.jsonString2Object(jsonString, "isRemember", Boolean.class);
        KnwUser user = json.jsonString2Object(jsonString, "user", KnwUser.class);
        log.info("get from requestBody "+isRemember);
        return new Result(userService.getUserByID(user.getId()));
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestHeader(value = "Authorization", required = false) String authHeader,
                        @RequestBody String jsonString) throws NoSuchUserException, JsonProcessingException {
        boolean isRemember = json.jsonString2Object(jsonString, "isRemember", Boolean.class);
        KnwUser user = json.jsonString2Object(jsonString, "user", KnwUser.class);
        log.info("get isRemember from json:" + isRemember);

        KnwUser knwUser = userService.getUserByObject(user);
        if(knwUser == null){
            return new Result(ResultEnum.FALSE_PASSWORD,null);
        }
        if(authHeader == null) {
            String token = tokenUtils.createToken(knwUser.getId(), isRemember);
            return new Result(token);
        }
        return new Result(ResultEnum.ALREADY_LOGIN,null);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody String jsonString) throws JsonProcessingException {
        String code = json.jsonString2Object(jsonString, "code", String.class);
        KnwUser user = json.jsonString2Object(jsonString,"user", KnwUser.class);

        log.info(user.toString());
        boolean isRegister = false;
        if(emailService.checkVerifyCode(user.getEmail(), code)){
            user.setIsActive((byte)1);
            isRegister = userService.addUserToSystem(user);
            return new Result(ResultEnum.SUCCESS_REGISTER, null);

        }
        if(isRegister){
            return new Result(ResultEnum.ALREADY_REGISTER, null);
        }
        return new Result(ResultEnum.CODE_INVALID, null);
    }

    @PostMapping("/verify")
    @ResponseBody
    public Result verifyAccount(@RequestBody String jsonString) throws JsonProcessingException {
        String email = json.jsonString2Object(jsonString, "email",String.class);

        if(emailService.sendVerifyCode(email)){
            return new Result(ResultEnum.SUCCESS, "发送邮件成功");
        }
        throw new RuntimeException();
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(@RequestHeader(value = "Authorization", required = false)
                                     String tokenWithHeader){
        boolean isLogout = false;
        if(tokenWithHeader != null && tokenWithHeader.startsWith(tokenUtils.getStart())){
            String token = tokenWithHeader.substring(tokenUtils.getStart().length());
            try{
                isLogout = tokenUtils.deleteToken(token);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(ResultEnum.ALREADY_LOGOUT,null);
            }

        }
        if(isLogout){
            return new Result(ResultEnum.SUCCESS_LOGOUT,null);
        }
        return new Result(ResultEnum.ALREADY_LOGOUT,null);
    }
}
