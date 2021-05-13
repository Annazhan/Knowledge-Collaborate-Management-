package com.example.knw.pojo;

import java.io.Serializable;
import java.util.Date;

public class Msg implements Serializable {
    private Integer msgId;

    private String content;

    private String type;

    private Integer sender;

    private String reciever;

    private Date sendTime;

    private Boolean isread;

    private static final long serialVersionUID = 1L;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever == null ? null : reciever.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgId=").append(msgId);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", sender=").append(sender);
        sb.append(", reciever=").append(reciever);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", isread=").append(isread);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}