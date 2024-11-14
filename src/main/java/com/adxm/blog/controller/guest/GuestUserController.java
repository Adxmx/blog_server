package com.adxm.blog.controller.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adxm.blog.entity.User;
import com.adxm.blog.service.UserService;
import com.adxm.blog.utils.R;

@RestController
@RequestMapping("/api")
public class GuestUserController {

    @Autowired
    private UserService userService;

    /******************************用户详情查询********************************************/
    @GetMapping("/user/{id}")
    public R UserDetail(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        User user = userService.userDetail(id);
        if (user == null) {
            r = R.fail().msg("用户不存在");
        } else {
            r = R.succeed().msg("查询成功").data("user", user);
        }
        return r;
    }

}
