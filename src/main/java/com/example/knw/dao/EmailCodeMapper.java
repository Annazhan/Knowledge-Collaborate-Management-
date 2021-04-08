package com.example.knw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis存邮箱验证码
 *
 * @author qanna
 * @date 2021-04-07
 */
@Repository
public class EmailCodeMapper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String hashKey = "emailCode";

    public boolean saveEmailCode(String key, String code, Long expirationTime){
        try {
            stringRedisTemplate.opsForHash().put(hashKey,key,code);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getEmailCode(String key){
        try {
            if(stringRedisTemplate.opsForHash().hasKey(hashKey,key)){
                return stringRedisTemplate.opsForHash().get(hashKey,key).toString();
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
