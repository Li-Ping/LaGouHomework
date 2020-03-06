package com.lagou.edu.sss_session.controller;

import com.lagou.edu.sss_session.dao.ResumeDao;
import com.lagou.edu.sss_session.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author:LiPing
 * @description：resume
 * @date:Created in 15:11 2020/3/4 0019
 */
@Controller
@RequestMapping("resume")
public class ResumeController {

    @Autowired
    private ResumeDao resumeDao;

    /**
     * 跳转到resume列表页面
     * @return
     */
    @RequestMapping("resume")
    public ModelAndView resume(){
        // 封装了数据和页面信息
        ModelAndView modelAndView = new ModelAndView();
        // 视图信息，封装跳转的页面信息
        modelAndView.setViewName("resume");
        return modelAndView;
    }

    /**
     * 异步查询resume列表
     * @return
     */
    @ResponseBody
    @RequestMapping("query")
    public List<Resume> queryAll(){
        List<Resume> all = resumeDao.findAll();
        return all;
    }

    /**
     * 根据id删除resume
     * @param id
     * @return
     */
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

    /**
     * 根据id修改resume
     * @param resume
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public Resume update(Resume resume){
        Resume save = resumeDao.save(resume);
        return save;
    }

}
