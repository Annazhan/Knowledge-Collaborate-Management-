package com.example.knw.service.impl;

import com.example.knw.dao.JoinTeamMapper;
import com.example.knw.dao.KnwTeamMapper;
import com.example.knw.dao.MsgMapper;
import com.example.knw.exception.SendMsgFailureException;
import com.example.knw.pojo.JoinTeam;
import com.example.knw.pojo.JoinTeamExample;
import com.example.knw.pojo.Msg;
import com.example.knw.service.MsgService;
import com.example.knw.utils.JsonUtils;
import com.example.knw.utils.enumpackage.MsgEnum;
import com.example.knw.utils.enumpackage.PeopleAuthEnum;
import com.example.knw.websocket.WsSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

/**
 * 聊天信息处理的实现
 *
 * @author qanna
 * @date 2021-04-08
 */
@Service
@Slf4j
public class MsgServiceImpl implements MsgService {
    @Autowired
    MsgMapper msgMapper;

    @Autowired
    JoinTeamMapper joinTeamMapper;

    @Autowired
    KnwTeamMapper teamMapper;

    @Autowired
    JsonUtils json;

    private String invitationPhrase = "邀请您加入团队 ";
    private String applyingPhrase = "申请加入团队 ";


//    public Msg buildMsg(char type, WebSocketMessage payload, Integer userID){
//        Msg msg = new Msg();
//        msg.setContent(dealWithDifferentType(type,content));
//        msg.setReciever(receiver);
//        msg.setSender(Integer.valueOf(sender));
//        msg.setType(String.valueOf(type));
//        msg.setIsread(false);
//        msg.setSendTime(sendTime);
//        msgMapper.insertSelective(msg);
//        return msg;
//    }

    @Override
    public void sendTeamInvitationByGroup(Integer[] receivers, Integer teamID, Integer sender){
        for(Integer i: receivers){
            String content = sender+invitationPhrase+teamMapper.selectByPrimaryKey(teamID).getTeamName();

            Msg msg = new Msg();
            msg.setSendTime(new Date());
            msg.setSender(sender);
            msg.setReciever(i.toString());
            msg.setIsread(false);
            msg.setContent(content);
            msg.setType(MsgEnum.NOTIFY.getC());

            sendTextToReceiver(msg);
        }
    }

    @Override
    public void sendTextToReceiver(Msg msg) {
        String receiver = msg.getReciever();
        try {
//            if(WsSessionManager.isInSession(receiver)){
//                WsSessionManager.get(receiver).sendMessage(new TextMessage(json.object2Json(msg)));
//            }
            msgMapper.insertSelective(msg);
        }catch (Exception e){
            e.printStackTrace();
            throw new SendMsgFailureException();
        }
    }

    @Override
    public void sendApplyMsg(Integer sender, Integer teamID){
        JoinTeamExample example = new JoinTeamExample();
        JoinTeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(teamID);
        example.setOrderByClause("auth DESC");
        List<JoinTeam> receiverList = joinTeamMapper.selectByExample(example);

        int auth = PeopleAuthEnum.TEAM.getI();
        for(JoinTeam r:receiverList){
            if((r.getAuth() & auth) != 0){
                Msg msg = new Msg();
                msg.setSender(sender);
                msg.setReciever(r.getJoinUser().toString());
                msg.setSendTime(new Date());
                msg.setType(MsgEnum.NOTIFY.getC());
                msg.setIsread(false);
                msg.setContent(applyingPhrase+teamMapper.selectByPrimaryKey(teamID).getTeamName());
                sendTextToReceiver(msg);
            }
        }
    }

//    private <T> String dealWithDifferentType(char type, T content){
//        if(type == MsgEnum.TEXT.getC()){
//            return content.toString();
//        }
//        else if(type == MsgEnum.FILE.getC()){
//            return UUID.randomUUID().toString()+"-"+((MultipartFile)content).getOriginalFilename();
//        }
//        return null;
//    }
//
//    public MsgEnum getDictionary(char type){
//        if(type == MsgEnum.FILE.getC()){
//            return MsgEnum.FILE;
//        }
//        else if(type == MsgEnum.TEXT.getC()){
//            return MsgEnum.TEXT;
//        }
//        return MsgEnum.DELETE;
//    }

}
