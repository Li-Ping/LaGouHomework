package com.lagou.springboot_blog_system;

import com.lagou.springboot_blog_system.pojo.Article;
import com.lagou.springboot_blog_system.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
class SpringbootBlogSystemApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Test
    void contextLoads() {
        Page<Article> articleList = articleService.getArticleList(2);
        System.out.println(articleList.getTotalPages());
        for (Article article : articleList) {
            System.out.println(article);
        }
    }

}
