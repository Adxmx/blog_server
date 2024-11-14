package com.adxm.blog.service.impl;

import com.adxm.blog.entity.User;
import com.adxm.blog.mapper.UserMapper;
import com.adxm.blog.service.UserService;
import com.adxm.blog.utils.CustomCaptcha;
import com.adxm.blog.utils.JWTUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> login(String username, String password, String uuid, String code) {
        Map<String, Object> map = new HashMap<>();
        Boolean flag = null;
        String token = null;
        if(CustomCaptcha.verifyCode(redisTemplate, uuid, code)) {
            // 验证账密
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username).eq("password", password);
            User user = null;
            try {
                user = userMapper.selectOne(wrapper);
                token = JWTUtils.generateToken(user);
                flag = true;
            } catch (Exception e) {
                // TODO 日志记录登录异常
                flag = false;
                token = null;
            }
        } else {
            // 验证码错误
            flag = null; 
        }
        map.put("flag", (Object) flag);
        map.put("token", (Object) token);
        return map;
    }

    @Override
    public User userDetail(int id) {
        User user = null;
        user = userMapper.selectById(id);
        return user;
    }

    @Override
    public Map<String, String> getCaptcha() {
        Map<String, String> captcha = CustomCaptcha.getCode(redisTemplate);
        return captcha;
    }
}
