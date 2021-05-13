package com.example.knw.service.impl;


import com.example.knw.dao.JoinTeamMapper;
import com.example.knw.dao.KnwTeamMapper;
import com.example.knw.exception.AlreadyInTeam;
import com.example.knw.exception.AuthorizeException;
import com.example.knw.exception.NoSuchUserException;
import com.example.knw.pojo.JoinTeam;
import com.example.knw.pojo.JoinTeamExample;
import com.example.knw.pojo.KnwTeam;
import com.example.knw.pojo.KnwUser;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.JoinTeamService;
import com.example.knw.service.UserService;
import com.example.knw.utils.enumpackage.JoinInTeamStatusEnum;
import com.example.knw.utils.enumpackage.PeopleAuthEnum;
import com.example.knw.utils.enumpackage.PositionInTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 团队中用户详情
 *
 * @author qanna
 * @date 2021-03-23
 */
@Service
public class JoinTeamServiceImpl implements JoinTeamService{

    @Autowired
    KnwTeamMapper teamMapper;

    @Autowired
    JoinTeamMapper joinTeamMapper;

    @Autowired
    UserService userService;

    @Override
    public void addMemberToTeam(Integer[] members, Integer teamID){
        KnwTeam team = teamMapper.selectByPrimaryKey(teamID);

        for(Integer i: members){
            JoinTeam join = isInTeam(i,teamID);
            if(join == null){
                JoinTeam joinTeam = new JoinTeam();
                joinTeam.setAuth(PeopleAuthEnum.getMember());
                joinTeam.setPosition(team.getPositionName().getPosition(null));
                joinTeam.setTeamId(teamID);
                joinTeam.setJoinUser(i);
                joinTeam.setApplyTime(new Date());
                joinTeam.setStatus(JoinInTeamStatusEnum.INVITED);
                joinTeamMapper.insertSelective(joinTeam);
            }
            else{
                join.setStatus(JoinInTeamStatusEnum.INVITED);
                joinTeamMapper.updateByPrimaryKeySelective(join);
            }
        }
    }

    @Override
    public void applyToAddTeam(Integer userID, Integer teamID, String reason){
        JoinTeam join = isInTeam(userID,teamID);
        if(join != null){
            join.setStatus(JoinInTeamStatusEnum.SUBMIT);
            joinTeamMapper.updateByPrimaryKeySelective(join);
            return;
        }
        JoinTeam joinTeam = isInTeam(userID, teamID);
        if(joinTeam == null) {
            joinTeam = new JoinTeam();
            joinTeam.setJoinUser(userID);
            joinTeam.setTeamId(teamID);
        }
        if(reason != null){
            joinTeam.setJoinTeamReason(reason);
        }
        joinTeam.setApplyTime(new Date());
        joinTeam.setStatus(JoinInTeamStatusEnum.SUBMIT);
        joinTeamMapper.insertSelective(joinTeam);
    }


    private JoinTeam isInTeam(Integer userID, Integer teamID){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        criteria.andJoinUserEqualTo(userID);

        List<JoinTeam> probable = joinTeamMapper.selectByExample(example);
        if(probable.size()!=0){
            JoinTeam join = probable.get(0);
            if(join.getStatus().equals(JoinInTeamStatusEnum.IN)){
                throw new AlreadyInTeam(ResultEnum.ALREADY_IN_TEAM);
            }
            else if(join.getStatus().equals(JoinInTeamStatusEnum.OUT)){
                return join;
            }
            else{
                long day = ((new Date()).getTime() - join.getApplyTime().getTime())/(24*60*60*1000);
                if(day >= JoinTeam.EXPIRE_DAY){
                    joinTeamMapper.deleteByPrimaryKey(join.getJoinId());
                }
                else if(join.getStatus().equals(JoinInTeamStatusEnum.INVITED)){
                    throw new AlreadyInTeam(ResultEnum.ALREADY_GET_INVITATION);
                }
                else if(join.getStatus().equals(JoinInTeamStatusEnum.SUBMIT)){
                    throw new AlreadyInTeam(ResultEnum.ALREADY_SUBMIT_APPLICATION);
                }
                else{
                    return join;
                }
            }
        }
        return null;
    }

    @Override
    public Integer getUserAuth(Integer userID, Integer teamID){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andJoinUserEqualTo(userID);
        criteria.andTeamIdEqualTo(teamID);
        criteria.andStatusEqualTo(JoinInTeamStatusEnum.IN.getCode());

        List<JoinTeam> joins = joinTeamMapper.selectByExample(example);
        if(joins.size() != 0){
            return joins.get(0).getAuth();
        }
        return 0;
    }

    @Override
    public List<GrantedAuthority> getAuthByInteger(Integer auth){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(PeopleAuthEnum p: PeopleAuthEnum.values()){
            if((auth & p.getI()) != 0){
                authorities.add(new SimpleGrantedAuthority(p.toString()));
            }
        }
        return authorities;
    }

    @Override
    public boolean changeTeamMemberToOutStatus(Integer[] users){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andJoinUserIn(Arrays.asList(users));

        JoinTeam join = new JoinTeam();
        join.setStatus(JoinInTeamStatusEnum.OUT);

        try {
            joinTeamMapper.updateByExampleSelective(join, example);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Object[]> getAllUserInTeamWithAuthority(Integer teamID){
        List<Object[]> result = new ArrayList<>();
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        example.setOrderByClause("auth DESC");
        List<JoinTeam> joins = joinTeamMapper.selectByExample(example);

        for(JoinTeam j: joins){
            Object[] internal = new Object[2];
            try {
                internal[0] = userService.getUserByID(j.getJoinUser());
            }catch (Exception e){
                throw new NoSuchUserException();
            }
            internal[1] = j;
            result.add(internal);
        }
        return result;
    }

    @Override
    public void changeUserAuthority(Integer teamID, Integer userID, Integer auth){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        criteria.andJoinUserEqualTo(userID);

        List<JoinTeam> joins = joinTeamMapper.selectByExample(example);
        if(joins.size() == 0){
            throw new NoSuchUserException();
        }

        KnwTeam team = teamMapper.selectByPrimaryKey(teamID);
        JoinTeam join = new JoinTeam();
        join.setJoinId(joins.get(0).getJoinId());
        join.setAuth(auth);
        join.setPosition(team.getPositionName().getMemberPosition());

        if(auth == PeopleAuthEnum.getLeader()){
            if(team.getLeaderId().equals(userID)){
                throw new AuthorizeException(ResultEnum.ALREADY_TEAM_LEADER);
            }
            team.setLeaderId(userID);
            join.setPosition(team.getPositionName().getLeaderPosition());
            teamMapper.updateByPrimaryKeySelective(team);
        }

        joinTeamMapper.updateByPrimaryKeySelective(join);

    }

    @Override
    public boolean haveSameAuth(Integer teamID, Integer modifier, Integer userID, PeopleAuthEnum peopleAuth){
        int authMod = this.getUserAuth(modifier, teamID);
        int authUser = this.getUserAuth(userID, teamID);
        if((authMod & peopleAuth.getI()) != 0 && (authUser & peopleAuth.getI())!= 0){
            return true;
        }
        return false;
    }

}
