package com.lagou.edu.dao;

import com.lagou.edu.pojo.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:58 2020/5/3 0003
 */
public interface TokenDao extends JpaRepository<Token,Long> {
}
