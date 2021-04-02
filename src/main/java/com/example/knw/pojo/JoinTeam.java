package com.example.knw.pojo;

import java.io.Serializable;
import java.util.Date;

public class JoinTeam implements Serializable {
    private Integer joinId;

    private Integer joinUser;

    private Integer teamId;

    private Date joinTime;

    private Date applyTime;

    private Integer auth;

    private Integer status;

    private String position;

    private static final long serialVersionUID = 1L;

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public Integer getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(Integer joinUser) {
        this.joinUser = joinUser;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", joinId=").append(joinId);
        sb.append(", joinUser=").append(joinUser);
        sb.append(", teamId=").append(teamId);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", auth=").append(auth);
        sb.append(", status=").append(status);
        sb.append(", position=").append(position);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}