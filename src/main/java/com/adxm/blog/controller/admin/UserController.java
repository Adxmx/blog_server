package com.adxm.blog.controller.admin;

import com.adxm.blog.entity.User;
import com.adxm.blog.service.UserService;
import com.adxm.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String uuid = map.get("uuid");
        String code = map.get("code");
        R r = null;
        Map<String, Object> result = userService.login(username, password, uuid, code);
        Boolean flag = (Boolean) result.get("flag");
        String token = (String) result.get("token");

        if (flag == null) {
            r = R.fail().msg("验证码校验失败！").data(result);
        } else if (flag) {
            r = R.succeed().msg("登录成功！").data(result);
        } else {
            r = R.fail().msg("用户名或密码错误！").data(result);
        }
        return r;
    }

    @GetMapping("/user")
    public R userDetail(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        User user = userService.userDetail(userId);
        R r = null;
        if (user != null) {
            r = R.succeed().msg("查询成功").data("user", user);
        } else {
            r = R.fail().msg("查询失败");
        }
        return r;
    }

    @GetMapping("/captcha")
    public R getCaptcha() {
        R r = null;
        Map<String, String> captcha = userService.getCaptcha();
        r = R.succeed().msg("获取成功").data("captcha", captcha);
        return r;
    }
    
}

