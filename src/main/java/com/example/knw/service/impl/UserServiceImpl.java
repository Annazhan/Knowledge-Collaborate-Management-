package com.example.knw.service.impl;

import com.example.knw.dao.KnwUserMapper;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.KnwUser;
import com.example.knw.pojo.KnwUserExample;
import com.example.knw.service.UserService;
import com.example.knw.utils.JwtTokenUtils;
import com.example.knw.utils.enumpackage.UserLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户逻辑实现
 *
 * @author qanna
 * @date 2021-03-14
 */

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    KnwUserMapper userMapper;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public KnwUser getUserByID(Integer id) throws NoSuchUserException{
        KnwUser user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            throw new NoSuchUserException();
        }

        return user;
    }

    @Override
    public KnwUser getUserByEmailAndPassword(KnwUser user) throws NoSuchUserException{
        KnwUserExample example = new KnwUserExample();
        KnwUserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        List<KnwUser> list = userMapper.selectByExample(example);
        if(list.size() == 0) {
            throw new NoSuchUserException();
        }
        criteria.andPasswordEqualTo(user.getPassword());
        list = userMapper.selectByExample(example);
        if(list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public KnwUser getUserByEmail(String email){
        KnwUserExample example = new KnwUserExample();
        KnwUserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        List<KnwUser> list = userMapper.selectByExample(example);
        if(list.size() == 0) {
            throw new NoSuchUserException();
        }
        return list.get(0);
    }

    @Override
    public boolean addUserToSystem(KnwUser user){
        KnwUserExample example = new KnwUserExample();
        KnwUserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        List<KnwUser> list = userMapper.selectByExample(example);
        if(list.size() != 0){
            return true;
        }
        user.setRegisterTime(new Date());
        user.setLevel(UserLevelEnum.INDIVIDUAL);
        user.setIsActive((byte)1);
        userMapper.insertSelective(user);
        return false;
    }

    @Override
    public void updateUserInfo(KnwUser user) throws NoSuchUserException{
        KnwUser knwUser = null;
        if(user.getId()==null && user.getEmail()!=null){
            knwUser = getUserByEmail(user.getEmail());
        }
        else if(user.getId()!=null){
            knwUser = userMapper.selectByPrimaryKey(user.getId());
        }
        if(knwUser==null){
            throw new NoSuchUserException();
        }
        else{
            user.setId(knwUser.getId());
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        KnwUser user = getUserByID(Integer.valueOf(s));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getId().toString(), user.getPassword(),authorities);
    }

    @Override
    public List<KnwUser> relativeKnwUser(String email){
        KnwUserExample example = new KnwUserExample();
        KnwUserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailLike(email);
        List<KnwUser> list= userMapper.selectByExample(example);

        for(KnwUser user : list){
            user.setPassword(null);
        }
        return list;
    }

    @Override
    public String getUserName(Integer leaderID){
        return userMapper.selectByPrimaryKey(leaderID).getActualName();
    }

}
