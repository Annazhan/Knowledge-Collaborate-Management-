package com.example.knw.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.exception.AuthorizeException;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.UserService;
import com.example.knw.service.impl.EmailService;
import com.example.knw.utils.EmailUtils;
import com.example.knw.utils.JsonUtils;
import com.example.knw.utils.JwtTokenUtils;
import com.example.knw.utils.enumpackage.UserStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.sql.ast.ClusteringType.Hash;

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
        //log.info("get from requestBody "+isRemember);
        log.info("convert user from json:" + user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new Result(userService.getUserByID(user.getId()));
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestHeader(value = "Authorization", required = false) String authHeader,
                        @RequestBody String jsonString) throws NoSuchUserException, JsonProcessingException {
        boolean isRemember = false;
        try {
            isRemember = json.jsonString2Object(jsonString, "isRemember", Boolean.class);
        } catch (Exception e){
            isRemember = false;
        }
        KnwUser user = json.jsonString2Object(jsonString, "user", KnwUser.class);
        log.info("get isRemember from json:" + isRemember);

        KnwUser knwUser = userService.getUserByEmailAndPassword(user);
        if(knwUser == null){
            return new Result(ResultEnum.FALSE_PASSWORD,null);
        }
        if(authHeader == null) {
            knwUser.setStatus(UserStatusEnum.ONLINE);
            userService.updateUserInfo(knwUser);
            String token = tokenUtils.createToken(knwUser.getId(), isRemember);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            char[] encrypt = new char[knwUser.getPassword().length()];
            Arrays.fill(encrypt,'*');
            knwUser.setPassword(new String(encrypt));
            map.put("user", knwUser);
            return new Result(map);
        }
        return new Result(ResultEnum.ALREADY_LOGIN,null);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody String jsonString) throws JsonProcessingException {
        String code = null;
        try{
            code = json.jsonString2Object(jsonString, "code", String.class);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(ResultEnum.CODE_INVALID, null);
        }
        KnwUser user = json.jsonString2Object(jsonString,"user", KnwUser.class);

        log.info(user.toString());
        boolean isRegister = false;
        if(emailService.checkVerifyCode(user.getEmail(), code)){
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
    public Result verifyAccount(@RequestBody(required = false) String jsonString) throws JsonProcessingException {
        String email = null;
        if(jsonString==null){
            String userID = SecurityContextHolder.getContext().getAuthentication().getName();
            KnwUser user = userService.getUserByID(Integer.valueOf(userID));
            email = user.getEmail();
        }
        else{
            email = json.jsonString2Object(jsonString, "email",String.class);
        }
        if(emailService.sendVerifyCode(email)){
            return new Result(ResultEnum.SUCCESS, "发送邮件成功");
        }
        throw new RuntimeException();
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(@RequestHeader(value = "Authorization", required = false)
                                     String tokenWithHeader){
        Integer isLogout = -1;
        if(tokenWithHeader != null && tokenWithHeader.startsWith(tokenUtils.getStart())){
            String token = tokenWithHeader.substring(tokenUtils.getStart().length());
            try{
                isLogout = tokenUtils.deleteToken(token);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(ResultEnum.ALREADY_LOGOUT,null);
            }

        }
        if(isLogout!=-1){
            KnwUser user = new KnwUser();
            user.setStatus(UserStatusEnum.OFFLINE);
            user.setId(isLogout);
            userService.updateUserInfo(user);
            return new Result(ResultEnum.SUCCESS_LOGOUT,null);
        }
        return new Result(ResultEnum.ALREADY_LOGOUT,null);
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody KnwUser user, Principal principal, HttpServletResponse response) throws IOException {
        Integer userID = Integer.valueOf(principal.getName());
        user.setId(userID);
        if(user.getPassword()!=null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        userService.updateUserInfo(user);
        return new Result(ResultEnum.SUCCESS,"信息修改成功");
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public Result changePassword(@RequestBody String jsonString, Principal principal) throws JsonProcessingException {
        String password = json.jsonString2Object(jsonString,"password",String.class);
        String code = json.jsonString2Object(jsonString, "code", String.class);
        String email = json.jsonString2Object(jsonString, "email", String.class);
        if(emailService.checkVerifyCode(email, code)){
            KnwUser user = userService.getUserByEmail(email);
            user.setPassword(password);
            userService.updateUserInfo(user);
            return new Result(ResultEnum.SUCCESS_CHANGE_PASSWORD,null);
        }
        return new Result(ResultEnum.CODE_INVALID,null);
    }

}
