package com.lagou.springboot_blog_system.service;

import com.lagou.springboot_blog_system.pojo.Article;
import com.lagou.springboot_blog_system.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * @author:LiPing
 * @description：
 * @date:Created in 18:33 2020/1/31 0031
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> getArticleList(Integer pageNum){
        Pageable pageable  = PageRequest.of(pageNum - 1,3);
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles;
    }
}
