package com.example.knw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
    private Integer projectId;

    private String projectName;

    private Boolean isFinish;

    private Boolean isDelete;

    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date preTime;

    private Date finTime;

    private Integer chargeId;

    private Integer teamId;

    private String pDescription;

    private static final long serialVersionUID = 1L;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPreTime() {
        return preTime;
    }

    public void setPreTime(Date preTime) {
        this.preTime = preTime;
    }

    public Date getFinTime() {
        return finTime;
    }

    public void setFinTime(Date finTime) {
        this.finTime = finTime;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription == null ? null : pDescription.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName=").append(projectName);
        sb.append(", isFinish=").append(isFinish);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", preTime=").append(preTime);
        sb.append(", finTime=").append(finTime);
        sb.append(", chargeId=").append(chargeId);
        sb.append(", teamId=").append(teamId);
        sb.append(", pDescription=").append(pDescription);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}