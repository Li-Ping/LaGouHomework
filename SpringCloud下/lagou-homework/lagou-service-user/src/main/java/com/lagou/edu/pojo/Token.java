package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:56 2020/5/3 0003
 */
@Data
@Entity
@Table(name="lagou_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
