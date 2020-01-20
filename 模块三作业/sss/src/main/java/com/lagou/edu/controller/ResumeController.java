package com.lagou.edu.controller;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:11 2020/1/19 0019
 */
@Controller
@RequestMapping("resume")
public class ResumeController {

    @Autowired
    private ResumeDao resumeDao;

    @RequestMapping("resume")
    public ModelAndView resume(){
        // 封装了数据和页面信息
        ModelAndView modelAndView = new ModelAndView();
        // 视图信息，封装跳转的页面信息
        modelAndView.setViewName("resume");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("query")
    public List<Resume> queryAll(){
        List<Resume> all = resumeDao.findAll();
        return all;
    }

    @ResponseBody
    @RequestMapping("delete")
    public String delete(Long id){
        try {
            resumeDao.deleteById(id);
        }catch (Exception e){
            return "error";
        }
        return "sucess";
    }

    @ResponseBody
    @RequestMapping("update")
    public Resume update(Resume resume){
        Resume save = resumeDao.save(resume);
        return save;
    }

}
