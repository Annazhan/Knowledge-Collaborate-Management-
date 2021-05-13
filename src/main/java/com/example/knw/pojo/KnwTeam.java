package com.example.knw.pojo;

import com.example.knw.utils.enumpackage.PositionInTeam;

import java.io.Serializable;
import java.util.Date;

public class KnwTeam implements Serializable {
    private Integer teamId;

    private String teamName;

    private Byte teamIsActivate;

    private Date createTime;

    private Integer leaderId;

    private String brief;

    private String institute;

    private Float resourceSize;

    private Float usedSize;

    private PositionInTeam positionName;

    private static final long serialVersionUID = 1L;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public Byte getTeamIsActivate() {
        return teamIsActivate;
    }

    public void setTeamIsActivate(Byte teamIsActivate) {
        this.teamIsActivate = teamIsActivate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute == null ? null : institute.trim();
    }

    public Float getResourceSize() {
        return resourceSize;
    }

    public void setResourceSize(Float resourceSize) {
        this.resourceSize = resourceSize;
    }

    public Float getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(Float usedSize) {
        this.usedSize = usedSize;
    }

    public PositionInTeam getPositionName() {
        return positionName;
    }

    public void setPositionName(PositionInTeam positionName) {
        this.positionName = positionName == null ? null : positionName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teamId=").append(teamId);
        sb.append(", teamName=").append(teamName);
        sb.append(", teamIsActivate=").append(teamIsActivate);
        sb.append(", createTime=").append(createTime);
        sb.append(", leaderId=").append(leaderId);
        sb.append(", brief=").append(brief);
        sb.append(", institute=").append(institute);
        sb.append(", resourceSize=").append(resourceSize);
        sb.append(", usedSize=").append(usedSize);
        sb.append(", positionName=").append(positionName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}