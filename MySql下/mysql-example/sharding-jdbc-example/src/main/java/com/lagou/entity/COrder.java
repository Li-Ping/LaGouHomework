package com.lagou.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:54 2020/6/9 0009
 */
@Entity
@Table(name = "c_order")
public class COrder implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_del")
    private boolean isDel;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "publish_user_id")
    private Integer publishUserId;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "resume_type")
    private Integer resumeType;

    @Column(name = "status")
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return "COrder{" +
                "id=" + id +
                ", isDel=" + isDel +
                ", userId=" + userId +
                ", companyId=" + companyId +
                ", publishUserId=" + publishUserId +
                ", positionId=" + positionId +
                ", resumeType=" + resumeType +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Integer publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getResumeType() {
        return resumeType;
    }

    public void setResumeType(Integer resumeType) {
        this.resumeType = resumeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
