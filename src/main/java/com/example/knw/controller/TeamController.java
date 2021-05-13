package com.example.knw.controller;

import com.example.knw.pojo.JoinTeam;
import com.example.knw.pojo.KnwTeam;
import com.example.knw.pojo.KnwUser;
import com.example.knw.pojo.Project;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.example.knw.service.*;
import com.example.knw.utils.JsonUtils;
import com.example.knw.utils.enumpackage.JoinInTeamStatusEnum;
import com.example.knw.utils.enumpackage.PeopleAuthEnum;
import com.example.knw.utils.enumpackage.PositionInTeam;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 团队控制器
 *
 * @author qanna
 * @date 2021-03-23
 */
@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    JoinTeamService joinTeamService;

    @Autowired
    UserService userService;

    @Autowired
    MsgService msgService;

    @Autowired
    ProjectService projectService;

    JsonUtils json = new JsonUtils();

    @PostMapping("/createTeam")
    public Result createTeam(@RequestBody KnwTeam team, Principal principal){
        Integer userID = Integer.valueOf(principal.getName());
        teamService.createTeam(team,userID);
        return new Result(ResultEnum.SUCCESS,null);
    }

    @GetMapping("/getTeams")
    public Result getTeams(@RequestBody String json, Principal principal){
        log.info("getTeams: "+ json);
        Integer userID = Integer.valueOf(principal.getName());
        List<KnwTeam> teams = teamService.getAllTeamUserIn(userID);
        return new Result(ResultEnum.SUCCESS, teams);
    }

    @PostMapping("/addMember")
    public Result addMember(@RequestBody String jsonString, Principal principal) throws JsonProcessingException {
        Integer senderID = Integer.valueOf(principal.getName());
        Integer[] receiverID = json.jsonString2Object(jsonString, "userIds", Integer[].class);
        Integer teamID = json.jsonString2Object(jsonString, "teamId", Integer.class);

        joinTeamService.addMemberToTeam(receiverID, teamID);

        /**
         * 这里的通知形式需要换一个
         * 直接从joinTeam中获取信息就可以了
         */
        //msgService.sendTeamInvitationByGroup(receiverID, teamID, senderID);

        return new Result(ResultEnum.SUCCESS,null);
    }

    @PostMapping("/searchUser")
    public Result searchMember(@RequestBody String jsonString) throws JsonProcessingException {
        String email = json.jsonString2Object(jsonString, "email", String.class);
        List<KnwUser> relatedUser = userService.relativeKnwUser(email);
        return new Result(ResultEnum.SUCCESS, relatedUser);
    }

    @PostMapping("/searchTeams")
    public Result searchTeams(@RequestBody String jsonString) throws JsonProcessingException {
        String input = json.jsonString2Object(jsonString, "input", String.class);
        log.info("input");
        List<Map<String, Object>> teamInfo = new ArrayList<>();
        List<KnwTeam> teams = teamService.getRelatedTeams(input);
        for(KnwTeam t: teams){
            Map<String, Object> map = new HashMap<>();
            map.put("teamName", t.getTeamName());
            map.put("teamId", t.getTeamId());
            map.put("leaderName", userService.getUserName(t.getLeaderId()));
            teamInfo.add(map);
        }
        return new Result(ResultEnum.SUCCESS,teamInfo);
    }

    @PostMapping("/joinTeam")
    public Result joinTeam(@RequestBody String jsonString, Principal principal) throws JsonProcessingException {
        String reason = null;
        try {
            reason = json.jsonString2Object(jsonString, "reason", String.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        Integer teamID = json.jsonString2Object(jsonString, "teamId",Integer.class);
        Integer userID = Integer.valueOf(principal.getName());
        joinTeamService.applyToAddTeam(userID, teamID, reason);

        /**
         * 这里的通知形式需要换一个
         */
        //msgService.sendApplyMsg(userID, teamID);

        return new Result(ResultEnum.SUCCESS,null);
    }

    @PostMapping("/createProject")
    public Result createProject(@RequestBody Project project, Principal principal){
        Integer userID = Integer.valueOf(principal.getName());
        if(!projectService.addProjectToTeam(project,userID)){
            return new Result(ResultEnum.CREATE_PROJECT_FAILURE, null);
        }
        return new Result(ResultEnum.SUCCESS, null);
    }

    @PostMapping("/getTeamInfo")
    public Result getTeamInfo(@RequestBody JsonNode node, Principal principal){
        Integer userID = Integer.valueOf(principal.getName());
        Integer teamID = node.get("teamId").asInt();
        List<Project> projects = teamService.getProjectsInTeam(teamID);
        List<KnwUser> users = teamService.getAllUserInTeam(teamID);

        List<Map<String, Object>> list = new ArrayList<>();
        for(KnwUser user:users){
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", user.getId());
            map.put("actualName", user.getActualName());
            list.add(map);
        }

        Map<String, Object> result = new HashMap<>(3);
        result.put("projects",projects);
        result.put("teamMembers", list);

        Integer auth = joinTeamService.getUserAuth(userID, teamID);
        result.put("authority", Integer.toBinaryString(auth));

        return new Result(ResultEnum.SUCCESS, result);
    }

    @PostMapping("/editTeam")
    public Result updateTeam(@RequestBody String jsonString, Principal principal, HttpServletResponse response) throws IOException {
        KnwTeam team = null;
        Integer[] removeUsers = null;
        Integer teamID = json.jsonString2Object(jsonString, "teamId", Integer.class);
        Integer userID = Integer.valueOf(principal.getName());
        try {
            team = json.jsonString2Object(jsonString, "team", KnwTeam.class);
            team.setLeaderId(null);
            team.setTeamId(teamID);
            removeUsers = json.jsonString2Object(jsonString, "removedMembers", Integer[].class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(team != null && !teamService.updateTeamInfo(team)){
            return new Result(ResultEnum.UPDATE_TEAM_INFO_FAILURE, null);
        }
        if(removeUsers != null){
            for(Integer i: removeUsers){
                if(joinTeamService.haveSameAuth(teamID, i, userID, PeopleAuthEnum.TEAM)){
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "user "+ i +" have same authority");
                }
                if(!joinTeamService.changeTeamMemberStatus(teamID, i, JoinInTeamStatusEnum.OUT)){
                    return new Result(ResultEnum.TEAMMATES_STATUS_IS_CHANGED, i+" 之后的队员全部踢出失败");
                }
            }

        }
        return new Result(ResultEnum.SUCCESS, null);
    }

    @PostMapping("/getAuthority")
    public Result getTeamMemberAuthority(@RequestBody JsonNode node){
        Integer teamID = node.get("teamId").asInt();
        List<Object[]> AuthorityInfo = joinTeamService.getAllUserInTeamWithAuthority(teamID);
        List<Map<String, Object>>  result = new ArrayList<>();
        for(Object[] list: AuthorityInfo){
            Map<String, Object> map = new HashMap<>();
            KnwUser user = (KnwUser) list[0];
            JoinTeam join = (JoinTeam)list[1];
            map.put("id", user.getId());
            map.put("actualName", user.getActualName());
            map.put("sex", user.getSex());
            map.put("email", user.getEmail());
            map.put("enterTime", join.getJoinTime());
            map.put("authority", Integer.toBinaryString(join.getAuth()));
            result.add(map);
        }
        return new Result(ResultEnum.SUCCESS, result);
    }

    @PostMapping("/editAuthority")
    public Result changeAuthority(@RequestBody String jsonString, Principal principal, HttpServletResponse response) throws IOException {
        Integer modifier = Integer.valueOf(principal.getName());
        JsonNode[] changes = json.jsonString2Object(jsonString, "changes", JsonNode[].class);
        Integer teamID = json.jsonString2Object(jsonString, "teamId", Integer.class);
        for(JsonNode n:changes) {
            Integer userID = n.get("id").asInt();
            if (joinTeamService.haveSameAuth(teamID, modifier, userID, PeopleAuthEnum.AUTH)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            Integer value = Integer.parseInt(n.get("authority").asText(), 2);
            joinTeamService.changeUserAuthority(teamID, userID, value);
        }
        return new Result(ResultEnum.SUCCESS, null);
    }

    @PostMapping("/quit")
    public Result quitTeam(@RequestBody JsonNode node, Principal principal){
        Integer userID = Integer.valueOf(principal.getName());
        Integer teamID = node.get("teamId").asInt();
        if(joinTeamService.changeTeamMemberStatus(teamID, userID, JoinInTeamStatusEnum.OUT)){
            return new Result(ResultEnum.SUCCESS, null);
        }
        return new Result(ResultEnum.TEAMMATES_STATUS_IS_CHANGED, null);
    }

    @PostMapping("/transfer")
    public Result transferTeamToOther(@RequestBody String jsonString, Principal principal) throws JsonProcessingException {
        Integer origin = Integer.valueOf(principal.getName());
        Integer newLeader = json.jsonString2Object(jsonString, "userId", Integer.class);
        Integer teamID = json.jsonString2Object(jsonString, "teamId", Integer.class);
        joinTeamService.changeUserAuthority(teamID, origin, PeopleAuthEnum.getMember());
        joinTeamService.changeUserAuthority(teamID, newLeader, PeopleAuthEnum.getLeader());
        return new Result(ResultEnum.SUCCESS, null);
    }

    @PostMapping("/createRole")
    public Result createRole(@RequestBody String jsonString) throws JsonProcessingException {
        JsonNode[] roles = json.jsonString2Object(jsonString, "roles", JsonNode[].class);

        Integer teamID = json.jsonString2Object(jsonString, "teamId", Integer.class);
        KnwTeam team = teamService.getTeamByID(teamID);
        PositionInTeam position = team.getPositionName();

        for(JsonNode n: roles){
            String role = n.get("role").asText();
            Integer authority = Integer.parseInt(n.get("authority").asText(),2);
            position.addPositionName(role, authority);
        }

        team.setPositionName(position);
        boolean isUpdate = teamService.updateTeamInfo(team);
        log.info("isUpdate:" + isUpdate);

        return new Result(ResultEnum.SUCCESS, null);
    }

}
