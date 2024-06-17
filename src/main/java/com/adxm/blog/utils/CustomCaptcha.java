package com.adxm.blog.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

public class CustomCaptcha {
    
    // 生成验证码
    public static Map<String, String> getCode(RedisTemplate redisTemplate) {
        // 生成与验证码绑定的uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 设置验证码
        SpecCaptcha captcha = new SpecCaptcha(140, 32);
        captcha.setLen(4);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        // 将验证码和uuid缓存到数据库(有效期2分钟)
        redisTemplate.opsForValue().set(uuid, captcha.text().toLowerCase(), 2, TimeUnit.MINUTES);
        // 返回uuid和验证码(base64)
        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("base64", captcha.toBase64());
        return map;
    }

    // 校验验证码
    public static boolean verifyCode(RedisTemplate redisTemplate, String uuid, String userCode) {
        String captchaCode = (String) redisTemplate.opsForValue().get(uuid);
        return captchaCode != null && captchaCode.equals(userCode.toLowerCase());
    }
}
