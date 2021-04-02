package com.example.knw.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证是否存在token
 *
 * @author qanna
 * @date 2021-03-29
 */
@Component
public class JwtRequestHandler extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtRequestHandler.class);

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String tokenWithHeader = httpServletRequest.getHeader(jwtTokenUtils.getHeader());
        Integer userID = null;
        if(tokenWithHeader != null && tokenWithHeader.startsWith(jwtTokenUtils.getStart())){
            String token = tokenWithHeader.substring(jwtTokenUtils.getStart().length());
            try{
                userID = jwtTokenUtils.verify(token);
                logger.info("userID: "+userID);
            }catch (TokenExpiredException e){
                logger.info("Token已经过期");
                throw e;
            }

        }
        if(userID != null && SecurityContextHolder.getContext().getAuthentication() == null){
            Authentication authentication = jwtTokenUtils.getAuthentication(userID);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
