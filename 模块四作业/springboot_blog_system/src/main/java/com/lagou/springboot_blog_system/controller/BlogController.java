package com.lagou.springboot_blog_system.controller;

import com.lagou.springboot_blog_system.pojo.Article;
import com.lagou.springboot_blog_system.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:00 2020/1/31 0031
 */
@Controller
public class BlogController {

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/getArticleList")
    public Map<String, Object> getArticleList(int pageNum){
        Map<String, Object> data = new HashMap<String, Object>();
        Page<Article> articleList = articleService.getArticleList(pageNum);
        data.put("currentPage", pageNum);
        data.put("acticles", articleList);
        data.put("pageSize", articleList.getTotalPages());
        return data;
    }

    @RequestMapping("/getArticles")
    public String getArticles(@RequestParam(value = "pageNum",required = false) Integer pageNum, Model model){
        if (pageNum == null){
            pageNum = 1;
        }
        Page<Article> articleList = articleService.getArticleList(pageNum);
        model.addAttribute("articleList",articleList);
        model.addAttribute("totalNum",articleList.getTotalPages());
        model.addAttribute("pageNum",pageNum);
        return "client/index";
    }
}
