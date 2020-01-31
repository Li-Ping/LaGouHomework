package com.lagou.springboot_blog_system.repository;


import com.lagou.springboot_blog_system.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:47 2020/1/29 0029
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    //Page<Article> findAll(Specification<Article> spec, Pageable pageable);
}
