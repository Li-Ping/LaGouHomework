package com.lagou.edu.controller;

import com.lagou.edu.controller.service.EmailFeignClient;
import com.lagou.edu.pojo.AuthCode;
import com.lagou.edu.service.AuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:29 2020/5/3 0003
 */
@RestController
@RequestMapping("/code")
public class CodeContoller {

    @Autowired
    private AuthCodeService authCodeService;

    @Autowired
    private EmailFeignClient emailFeignClient;

    /**
     * ⽣成验证码并发送到对应邮箱，成功 true，失败 false
     * @param email
     * @return
     */
    @GetMapping("/create/{email}")
    public boolean createCode(@PathVariable String email){
        try {
            // 生成随机六位数字验证码
            String code = randomCode();
            // 存入数据库
            authCodeService.createCode(email, code);
            // 调用发送邮件的接口
            emailFeignClient.sendCode(email, code);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 校验验证码是否正确，0正确1错误2超时
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/validate/{email}/{code}")
    public Integer checkCode(@PathVariable String email,@PathVariable String code){
        // 根据邮箱查询验证码信息
        AuthCode authCode = authCodeService.checkCode(email);
        // 未查询到验证码
        if (authCode == null){
            return 1;
        }else {
            String codeData = authCode.getCode();
            // 验证码正确
            if (codeData.equals(code)){
                // 超时
                if (System.currentTimeMillis() > authCode.getExpiretime().getTime()){
                    return 2;
                }else {
                    return 0;
                }
            }else {
                // 验证码不正确
                return 1;
            }
        }
    }

    /**
     * ⽣成验证码
     * @return
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


}
