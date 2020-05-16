package com.lagou.edu.service.impl;

import com.lagou.edu.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:03 2020/5/3 0003
 */
@Service
public class EmailServiceImpl implements EmailService {

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 简单文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

/*  ## QQ邮箱配置
    mail:
    host: ${mail.host} #发送邮件服务器 smtp.qq.com
    username: ${mail.username} #发送邮件的邮箱地址
    password: ${mail.password} #客户端授权码，不是邮箱密码，这个在qq邮箱设置里面自动生成的
    properties.mail.smtp.port: ${mail.properties.mail.smtp.port} #端口号465或587
    properties.mail.smtp.auth: ${mail.properties.mail.smtp.auth}
    properties.mail.smtp.starttls.enable: ${mail.properties.mail.smtp.starttls.enable}
    properties.mail.smtp.starttls.required: ${mail.properties.mail.smtp.starttls.required}
    properties.mail.smtp.ssl.enable: ${mail.properties.mail.smtp.ssl.enable}
    default-encoding: utf-8
    protocol: ${mail.protocol}*/

}
