package com.lagou.edu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.lagou.edu.pojo.Resume;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:31 2020/1/17 0017
 */
public interface ResumeDao extends JpaRepository<Resume,Long>, JpaSpecificationExecutor<Resume> {

    @Query("from Resume where id = ?1")
    public Resume findByJpql(Long id);

    @Modifying
    @Transactional
    @Query("delete from Resume where id = ?1")
    public void deleteByJpql(Long id);

    @Modifying
    @Transactional
    public void deleteByAddress(String address);
}
