package com.example.knw.service;

import com.example.knw.pojo.Msg;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * 聊天信息处理
 *
 * @author qanna
 * @date 2021-04-08
 */
public interface MsgService {
    void sendTextToReceiver(Msg msg) throws IOException;
    void sendTeamInvitationByGroup(Integer[] receivers, Integer teamID, Integer senders);
    void sendApplyMsg(Integer sender, Integer teamID);
}
