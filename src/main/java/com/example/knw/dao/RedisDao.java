package com.example.knw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis存邮箱验证码
 *
 * @author qanna
 * @date 2021-04-07
 */
@Repository
public class RedisDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public boolean saveCode(String hashKey, String key, String code, Long expirationTime){
        try {
            stringRedisTemplate.opsForValue().set(hashKey+"_"+key,code,expirationTime,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getCode(String hashKey, String key){
        try {
            if(stringRedisTemplate.hasKey(hashKey+"_"+key)){
                return stringRedisTemplate.opsForValue().get(hashKey+"_"+key);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveHashField(String hashKey, String key, String field, Long ExpireTime){
        return false;
    }
}
