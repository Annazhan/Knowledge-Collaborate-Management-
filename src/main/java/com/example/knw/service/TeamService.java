package com.example.knw.service;

import com.example.knw.pojo.KnwTeam;
import com.example.knw.pojo.KnwUser;
import com.example.knw.pojo.Project;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

/**
 * 团队服务接口
 *
 * @author qanna
 * @date 2021-03-23
 */
public interface TeamService {
    void createTeam(KnwTeam team, Integer userID);
    List<KnwTeam> getAllTeamUserIn(Integer userID);
    List<KnwTeam> getRelatedTeams(String input);
    List<Project> getProjectsInTeam(Integer teamID);
    List<KnwUser> getAllUserInTeam(Integer teamID);
    boolean updateTeamInfo(KnwTeam newTeam);
   KnwTeam getTeamByID(Integer teamID);
}
