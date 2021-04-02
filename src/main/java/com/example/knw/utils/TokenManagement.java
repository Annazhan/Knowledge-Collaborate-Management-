package com.example.knw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理用户Token
 *
 * @author qanna
 * @date 2021-03-19
 */
public class TokenManagement {
    private static ConcurrentHashMap<Integer, List<String>> TOKEN_POOL = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(TokenManagement.class);

    public boolean isInTokenPool(Integer userID, String token){
        if(userID != null && token != null){
            if(!TOKEN_POOL.containsKey(userID)){
                return false;
            }
            else if(TOKEN_POOL.get(userID).contains(token)){
                return true;
            }
        }
        return false;
    }


    public boolean addToken(Integer userID, String token){
        if(!TOKEN_POOL.containsKey(userID)){
            List<String> tokens = new ArrayList<>();
            tokens.add(token);
            TOKEN_POOL.put(userID, tokens);
            logger.info("add token and user to token pool");
            return true;
        }
        else if(!isInTokenPool(userID,token)){
            TOKEN_POOL.get(userID).add(token);
            logger.info("user already exists and add token to token pool");
            return true;
        }
        logger.info("user and tokenID already exist");
        return false;
    }

    public boolean remove(Integer userID, String token){
        if(isInTokenPool(userID,token)){
            TOKEN_POOL.get(userID).remove(token);
            if(TOKEN_POOL.get(userID).isEmpty()){
                TOKEN_POOL.remove(userID);
            }
            return true;
        }
        return false;
    }

    public Integer getOnLineDevice(Integer userID){
        if(TOKEN_POOL.containsKey(userID)){
            return TOKEN_POOL.get(userID).size();
        }
        else{
            return 0;
        }
    }
}
