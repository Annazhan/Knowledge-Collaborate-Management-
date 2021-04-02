package com.example.knw.service;

import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;

/**
 * 用户的逻辑服务
 *
 * @author qanna
 * @date 2021-03-14
 */
public interface UserService {
    KnwUser getUserByID(Integer id) throws NoSuchUserException;
    KnwUser getUserByObject(KnwUser user);
    boolean addUserToSystem(KnwUser user);
}
