package com.lagou.repository;

import com.lagou.entity.COrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 23:05 2020/6/9 0009
 */
public interface COrderRepository extends JpaRepository<COrder,Long> {
}
