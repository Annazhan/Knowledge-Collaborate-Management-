package com.example.knw.service.impl;

import com.example.knw.dao.JoinTeamMapper;
import com.example.knw.dao.KnwTeamMapper;
import com.example.knw.dao.KnwUserMapper;
import com.example.knw.dao.ProjectMapper;
import com.example.knw.exception.CreateTeamFailureException;
import com.example.knw.exception.JoinNoTeamException;
import com.example.knw.pojo.*;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.TeamService;
import com.example.knw.utils.enumpackage.JoinInTeamStatusEnum;
import com.example.knw.utils.enumpackage.PositionInTeam;
import com.example.knw.utils.enumpackage.UserLevelEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.server.HandshakeHandler;

import java.util.*;

/**
 * 团队服务接口实现
 *
 * @author qanna
 * @date 2021-04-12
 */
@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    @Autowired
    KnwTeamMapper teamMapper;

    @Autowired
    KnwUserMapper userMapper;

    @Autowired
    JoinTeamMapper joinTeamMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public void createTeam(KnwTeam team, Integer userID) {
        KnwTeamExample example = new KnwTeamExample();
        KnwTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamNameEqualTo(team.getTeamName());
        if(teamMapper.selectByExample(example).size()!= 0){
            throw new CreateTeamFailureException(ResultEnum.TEAM_ALREADY_EXIST);
        }

        Date date = new Date();
        PositionInTeam posName = new PositionInTeam();
        Integer teamID = teamMapper.selectLastTeamID() + 1;

        //设置新团队的数据
        team.setTeamIsActivate((byte)1);
        team.setLeaderId(userID);
        team.setResourceSize((float)assignSpaceBasedOnLeader(userID));
        team.setUsedSize((float)0);
        team.setCreateTime(date);
        team.setTeamId(teamID);
        team.setPositionName(posName);
        teamMapper.insertSelective(team);

        //设置团队与创建者的关系
        JoinTeam join = new JoinTeam();
        join.setJoinTime(date);
        join.setApplyTime(date);
        join.setJoinUser(userID);
        join.setPosition(posName.getLeaderPosition());
        join.setAuth(posName.getAuth(join.getPosition()));
        join.setStatus(JoinInTeamStatusEnum.IN);
        join.setTeamId(teamID);
        joinTeamMapper.insertSelective(join);
    }

    private double assignSpaceBasedOnLeader(int userID){
        UserLevelEnum level = userMapper.selectByPrimaryKey(userID).getLevel();
        return level.getTeamSpace();
    }

    @Override
    public List<KnwTeam> getAllTeamUserIn(Integer userID){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andJoinUserEqualTo(userID);
        criteria.andStatusEqualTo(JoinInTeamStatusEnum.IN.getCode());

        List<Integer> teams = joinTeamMapper.selectTeamIDByExample(example);
        if(teams.size() == 0){
            return new ArrayList<KnwTeam>(0);
        }
        KnwTeamExample teamExample = new KnwTeamExample();
        KnwTeamExample.Criteria teamCriteria = teamExample.createCriteria();
        teamCriteria.andTeamIdIn(teams);
        return teamMapper.selectByExample(teamExample);
    }

    @Override
    public List<KnwTeam> getRelatedTeams(String input){
        KnwTeamExample example = new KnwTeamExample();
        KnwTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamNameLike(input);
        List<KnwTeam> teams = teamMapper.selectByExample(example);

        return teams;
    }

    @Override
    public List<Project> getProjectsInTeam(Integer teamID){
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        criteria.andIsDeleteEqualTo(false);
        return projectMapper.selectByExample(example);
    }

    @Override
    public List<KnwUser> getAllUserInTeam(Integer teamID){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        criteria.andStatusEqualTo(JoinInTeamStatusEnum.IN.getCode());

        List<Integer> users = joinTeamMapper.selectUserIDByExample(example);

        KnwUserExample userExample = new KnwUserExample();
        KnwUserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andIdIn(users);

        return userMapper.selectByExample(userExample);
    }

    @Override
    public boolean updateTeamInfo(KnwTeam newTeam){
        try {
            teamMapper.updateByPrimaryKeySelective(newTeam);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public KnwTeam getTeamByID(Integer teamID){
        return teamMapper.selectByPrimaryKey(teamID);
    }
}
