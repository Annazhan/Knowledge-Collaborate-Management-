package com.example.knw.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.exception.AuthorizeException;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.UserService;
import com.example.knw.utils.JwtTokenUtils;
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
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtils tokenUtils;

    @RequestMapping("/getUser")
    @ResponseBody
    public Result getUser(@RequestParam Integer id) throws NoSuchUserException {
        return new Result(userService.getUserByID(id));
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestHeader("Authorization") String authHeader,
                        @RequestBody KnwUser user,
                        boolean isRemember) throws NoSuchUserException{
        logger.info("get isRemember from json:" + isRemember);
        //logger.info(user.getEmail());
        KnwUser knwUser = userService.getUserByObject(user);
        //logger.info(knwUser.getId().toString());
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
    public Result register(@RequestBody KnwUser user){
        logger.info(user.toString());
        boolean isRegister = userService.addUserToSystem(user);
        if(isRegister){
            return new Result(ResultEnum.ALREADY_REGISTER, null);
        }
        else {
            return new Result(ResultEnum.SUCCESS_REGISTER, null);
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(HttpServletRequest httpServletRequest){
        String tokenWithHeader = httpServletRequest.getHeader(tokenUtils.getHeader());
        boolean isLogout = false;
        if(tokenWithHeader != null && tokenWithHeader.startsWith(tokenUtils.getStart())){
            String token = tokenWithHeader.substring(tokenUtils.getStart().length());
            try{
                isLogout = tokenUtils.deleteToken(token);
            }catch (Exception e){
                return new Result(ResultEnum.ALREADY_LOGOUT,null);
            }

        }
        if(isLogout){
            return new Result(ResultEnum.SUCCESS_LOGOUT,null);
        }
        return new Result(ResultEnum.ALREADY_LOGOUT,null);
    }
}
