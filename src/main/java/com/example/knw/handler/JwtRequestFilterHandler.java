package com.example.knw.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.exception.TokenExpireReturnException;
import com.example.knw.service.JoinTeamService;
import com.example.knw.utils.JsonUtils;
import com.example.knw.utils.JwtTokenUtils;
import com.example.knw.utils.RepeatableRequestWrapper;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import sun.misc.IOUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 验证是否存在token
 *
 * @author qanna
 * @date 2021-03-29
 */
@Component
public class JwtRequestFilterHandler extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtRequestFilterHandler.class);
    private final static String FORM_CONTENT_TYPE = "multipart/form-data";
;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    JsonUtils json;

    @Autowired
    JoinTeamService joinTeamService;

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
                throw new TokenExpireReturnException();
            }
        }

        Integer teamID = null;
        try{
            RepeatableRequestWrapper requestWrapper =  new RepeatableRequestWrapper (httpServletRequest);
            String jsonBody = requestWrapper.getBodyString();
            teamID = json.jsonString2Object(jsonBody, "teamId", Integer.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(userID != null && SecurityContextHolder.getContext().getAuthentication() == null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(teamID != null){
                Integer auth = joinTeamService.getUserAuth(userID, teamID);
                authorities.addAll(joinTeamService.getAuthByInteger(auth));
            }

            Authentication authentication = jwtTokenUtils.getAuthentication(userID, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info(SecurityContextHolder.getContext().getAuthentication().toString());
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }


}
