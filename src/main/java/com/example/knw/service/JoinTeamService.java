package com.example.knw.service;

import com.example.knw.pojo.JoinTeam;
import com.example.knw.utils.enumpackage.PeopleAuthEnum;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 加入团队以及团队权限操作
 *
 * @author qanna
 * @date 2021-03-23
 */
public interface JoinTeamService {
    void addMemberToTeam(Integer[] members, Integer teamID);
    void applyToAddTeam(Integer userID, Integer teamID,String reason);
    Integer getUserAuth(Integer userID, Integer teamID);
    List<GrantedAuthority> getAuthByInteger(Integer auth);
    boolean changeTeamMemberToOutStatus(Integer[] users);
    List<Object[]> getAllUserInTeamWithAuthority(Integer teamID);
    void changeUserAuthority(Integer teamID, Integer userID, Integer auth);
    boolean haveSameAuth(Integer teamID, Integer modifier, Integer userID, PeopleAuthEnum peopleAuth);
}
