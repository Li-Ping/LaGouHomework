package com.lagou.edu.sss_session.dao;

import com.lagou.edu.sss_session.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:31 2020/3/4 0017
 */
public interface ResumeDao extends JpaRepository<Resume,Long>, JpaSpecificationExecutor<Resume> {

    /**
     * 根据id查询resume
     * @param id
     * @return
     */
    @Query("from Resume where id = ?1")
    public Resume findByJpql(Long id);

    /**
     * 删除
     * @param id
     */
    @Modifying
    @Transactional
    @Query("delete from Resume where id = ?1")
    public void deleteByJpql(Long id);

    /**
     * 修改
     * @param address
     */
    @Modifying
    @Transactional
    public void deleteByAddress(String address);
}
