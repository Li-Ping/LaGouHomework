package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:44 2020/5/3 0003
 */
@Data
@Entity
@Table(name="lagou_auth_code")
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String code;

    private Timestamp createtime;

    private Timestamp expiretime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Timestamp expiretime) {
        this.expiretime = expiretime;
    }
}
