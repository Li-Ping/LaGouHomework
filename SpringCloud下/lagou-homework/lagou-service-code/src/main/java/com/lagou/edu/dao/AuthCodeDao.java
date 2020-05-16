package com.lagou.edu.dao;

import com.lagou.edu.pojo.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:48 2020/5/3 0003
 */
public interface AuthCodeDao extends JpaRepository<AuthCode,Long> {

    @Query(value = "SELECT * FROM lagou_auth_code WHERE email = ?1 ORDER BY createtime DESC LIMIT 1",nativeQuery = true)
    public AuthCode findBySql(String email);
}
