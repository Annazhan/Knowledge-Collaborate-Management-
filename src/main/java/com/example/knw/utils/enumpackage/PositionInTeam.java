package com.example.knw.utils.enumpackage;

import com.example.knw.exception.PositionNameExistException;
import com.example.knw.exception.PositionNameNoExistException;
import com.example.knw.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;

import java.util.*;

/**
 * 描述在团队中用户职位
 *
 * @author qanna
 * @date 2021-04-14
 */
@Data
public class PositionInTeam {

    private Map<String, Integer> position;

    public PositionInTeam(){
        position = new HashMap<>();
        for(DefaultPosition positionName: DefaultPosition.values()){
            position.put(positionName.getDescription(),positionName.getAuth());
        }
    }

    public PositionInTeam(String jsonString) throws JsonProcessingException {
        JsonUtils json = new JsonUtils();
        position = json.jsonString2Object(jsonString,null, Map.class);
    }

    public void addPositionName(String s, int a){
        if(position.containsKey(s)){
            throw new PositionNameExistException();
        }
        else{
            position.put(s,a);
        }
    }

    public String getPositionNameAsJson() throws JsonProcessingException {
        JsonUtils json = new JsonUtils();
        return json.object2Json(position);
    }

    public String getLeaderPosition(){
        return DefaultPosition.SUPERVISOR.getDescription();
    }

    public String getPosition(String pos){
        if(pos == null){
            return DefaultPosition.MEMBER.getDescription();
        }
        else if(position.containsKey(pos)){
            return pos;
        }
        throw new PositionNameNoExistException();
    }

    public int getAuth(String pos){
        if(pos == null){
            return DefaultPosition.MEMBER.getAuth();
        }
        else if(position.containsKey(pos)){
            return position.get(pos);
        }
        throw new PositionNameNoExistException();
    }

    public String getMemberPosition(){
        return DefaultPosition.MEMBER.getDescription();
    }


    enum DefaultPosition{
        SUPERVISOR("团长",PeopleAuthEnum.getLeader()),
        ADMIN("管理员",PeopleAuthEnum.getAdmin()),
        LEADER("项目组长", PeopleAuthEnum.getAdmin()),
        CHARGE("项目负责人", PeopleAuthEnum.getAdmin()),
        MEMBER("成员",PeopleAuthEnum.getMember()),

        ;

        String description;
        Integer auth;

        DefaultPosition(String s, Integer i) {
            this.auth = i;
            this.description = s;
        }

        protected String getDescription(){
            return description;
        }

        protected Integer getAuth(){
            return auth;
        }
    }
}
