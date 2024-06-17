package com.adxm.blog.service;

import java.util.Map;

import com.adxm.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
public interface UserService extends IService<User> {
    
    public Map<String, Object> login(String username, String password, String uuid, String code);

    public User userDetail(int id);

    public Map<String, String> getCaptcha();
}
