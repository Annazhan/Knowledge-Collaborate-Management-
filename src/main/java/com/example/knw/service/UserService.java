package com.example.knw.service;

import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 用户的逻辑服务
 *
 * @author qanna
 * @date 2021-03-14
 */
public interface UserService {
    KnwUser getUserByID(Integer id) throws NoSuchUserException;
    KnwUser getUserByEmailAndPassword(KnwUser user);
    KnwUser getUserByEmail(String email);
    boolean addUserToSystem(KnwUser user);
    void updateUserInfo(KnwUser user) throws NoSuchUserException;
    List<KnwUser> relativeKnwUser(String email);
    String getUserName(Integer leaderID);
    UserDetails loadUserByUsername(String s);
}
