package com.lagou.edu.controller;

import com.lagou.edu.pojo.User;
import com.lagou.edu.service.CodeService;
import com.lagou.edu.service.TokenService;
import com.lagou.edu.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:59 2020/5/3 0003
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /*@Autowired
    private CodeFeignClient codeFeignClient;*/

    @Reference
    private CodeService codeFeignClient;

    @Reference
    private UserService userService;

    @Reference
    private TokenService tokenService;

    /*@Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;*/
    
    /**
     * 注册接⼝，true成功，false失败
     * @param email
     * @param password
     * @param code
     * @return
     */
    @GetMapping("/register/{email}/{password}/{code}")
    public boolean register(HttpServletResponse response, @PathVariable String email,
                            @PathVariable String password, @PathVariable String code){

        // 验证码校验
        //Integer integer = codeFeignClient.checkCode(email, code);

        // 注册用户名、密码
        userService.save(email, password);

        // 生成token 存入数据库
        String token = UUID.randomUUID().toString();
        tokenService.insert(token,email);
        // 写入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    /**
     * 是否已注册，根据邮箱判断,true代表已经注册过，false代表尚未注册
     * @param email
     * @return
     */
    @GetMapping("/isRegistered/{email}")
    public boolean isRegistered(@PathVariable String email){
        // 根据邮箱判断，是否已注册
        User user = userService.getUser(email, null);
        // 未注册
        if (user == null){
            return false;
        }
        return true;
    }

    /**
     * 登录接⼝，验证⽤户名密码合法性，根据
     * ⽤户名和密码⽣成token，token存⼊数
     * 据库，并写⼊cookie中，登录成功返回邮
     * 箱地址，重定向到欢迎⻚
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login/{email}/{password}")
    public String login(HttpServletResponse response,@PathVariable String email,@PathVariable String password){

        // 验证用户名、密码
        User user = userService.getUser(email, password);
        // 生成token 存入数据库 写入cookie
        if (user != null){
            // 生成token 存入数据库
            String token = UUID.randomUUID().toString();
            tokenService.insert(token,email);
            // 写入cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return email;
        }
        return null;
    }

    /**
     * 根据token查询⽤户登录邮箱接⼝
     * @param token
     * @return
     */
    @GetMapping("/info/{token}")
    public String info(@PathVariable String token){

        // 根据token查询⽤户登录邮箱接⼝
        String email = tokenService.getEmail(token);
        return email;
    }


}
