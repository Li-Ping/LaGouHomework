package com.lagou.edu.service;

/**
 * @author:LiPing
 * @description：邮件发送接口
 * @date:Created in 15:03 2020/5/3 0003
 */
public interface EmailServiceFeign {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);
}
