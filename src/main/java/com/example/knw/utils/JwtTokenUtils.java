package com.example.knw.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.auth0.jwt.interfaces.Verification;
import com.example.knw.exception.AuthorizeException;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.service.impl.UserServiceImpl;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;

/**
 * jwt生成验证token工具类
 *
 * @author qanna
 * @date 2021-03-24
 */
@Component
@Getter
public class JwtTokenUtils  {

    private Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.token-start-with} ")
    private String start;

    @Value("${jwt.token-validity-in-seconds}")
    private Integer validateTime;

    private TokenManagement tokenM = new TokenManagement();

    @Autowired
    private UserServiceImpl userService;

    private String setIssuer(){
        try {
            return InetAddress.getLocalHost().getHostAddress()+InetAddress.getLocalHost().getHostName();
        }catch (Exception e){
            e.printStackTrace();
            return "unknown issuer";
        }
    }

    private Date setExpireTime(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND,this.validateTime);
        return calendar.getTime();
    }

    private String setJwtID(Integer userID){
        String tokeID = UUID.randomUUID().toString()+ "-" +tokenM.getOnLineDevice(userID);
        boolean isAdd = tokenM.addToken(userID,tokeID);
        logger.info("add token ID to token pool: "+isAdd);
        return tokeID;
    }

    /**
     *
     * @param userID 用户名
     * @Param isRemember 用户是否记得登录状态
     * @return
     */
    public String createToken(Integer userID, boolean isRemember){
        Map<String, Object> map= new HashMap<>();
        map.put("alg","HS256");
        map.put("type","jwt");
        Date now = new Date();
        return this.start + JWT.create().withHeader(map)
                .withIssuer(setIssuer())
                .withSubject(userID.toString())
                //.withAudience(claim.get("device").toString())
                .withExpiresAt(setExpireTime(now))
                .withIssuedAt(now)
                .withClaim("isRemember",isRemember)
                //在创建token的时候将token加入token_pool中
                .withJWTId(setJwtID(userID))
                .sign(Algorithm.HMAC256(this.secret));
    }

    /**
     *
     * @param token 前端传token
     * @return
     * @throws AlgorithmMismatchException
     * @throws SignatureVerificationException
     * @throws TokenExpiredException
     * @throws InvalidClaimException
     * @throws JWTVerificationException
     */
    public DecodedJWT decode(String token)
            throws AlgorithmMismatchException, SignatureVerificationException, TokenExpiredException,
            InvalidClaimException, JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .acceptLeeway(1000)
                .build();
        return verifier.verify(token);
    }

    /**
     *
     * @param token
     * @return null表示验证失败
     * @throws NoSuchUserException
     * @throws AlgorithmMismatchException
     * @throws AuthorizeException
     * @throws TokenExpiredException
     * @throws JWTVerificationException
     */
    public Integer verify(String token)
            throws NoSuchUserException, AlgorithmMismatchException, AuthorizeException,
            TokenExpiredException, JWTVerificationException {
        String tokenID = null;
        Integer userID = null;
        boolean isRemember = false;
        try{
            DecodedJWT jwt = decode(token);
            tokenID = jwt.getId();
            userID = Integer.valueOf(jwt.getSubject());
            isRemember = Boolean.valueOf(String.valueOf(jwt.getClaim("isRemember")));
            logger.info("decoded tokenID: "+tokenID+"   decoded userID: "+ userID);
            if(tokenM.isInTokenPool(userID,tokenID)){
                logger.info("token is in token pool");
                return userID;
            }
            else{
                return null;
            }
        }catch (SignatureVerificationException e) {
            throw new AuthorizeException();
        }catch (TokenExpiredException e){
            if(!isRemember){
                tokenM.remove(userID, tokenID);
                throw e;
            }
            return userID;
        }catch (InvalidClaimException e){
            throw new AuthorizeException();
        }

    }

    /**
     * 获取Security使用的Authentication
     * @param userID
     * @return
     */
    public Authentication getAuthentication(Integer userID){
        UserDetails userDetails = userService.loadUserByUsername(userID.toString());
        return new UsernamePasswordAuthenticationToken(userID,
                userDetails.getPassword(),userDetails.getAuthorities());
    }

    /**
     *
     * @param token
     * @return true表示成功删除
     * @throws NoSuchUserException
     * @throws AlgorithmMismatchException
     * @throws AuthorizeException
     * @throws TokenExpiredException
     * @throws JWTVerificationException
     */
    public boolean deleteToken(String token)
            throws NoSuchUserException, AlgorithmMismatchException, AuthorizeException,
            TokenExpiredException, JWTVerificationException {
        String tokenID = null;
        Integer userID = null;
        try{
            DecodedJWT jwt = decode(token);
            tokenID = jwt.getId();
            userID = Integer.valueOf(jwt.getSubject());
            return tokenM.remove(userID,tokenID);
        }catch (SignatureVerificationException e) {
            throw new AuthorizeException();
        }catch (TokenExpiredException e){
            tokenM.remove(userID, tokenID);
            throw e;
        }catch (InvalidClaimException e){
            throw new AuthorizeException();
        }

    }


}
