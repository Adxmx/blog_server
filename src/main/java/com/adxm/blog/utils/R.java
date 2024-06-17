package com.adxm.blog.utils;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@Data
@ToString
public class R {
    // 自定义常用状态码
    public static final Integer SUCCEED = 20000;
    public static final Integer FAILED = 50000;
    public static final Integer UN_AUTHENTICATED = 40001;
    public static final Integer UN_AUTHORIZED = 40003;

    // 返回码
    private Integer code;
    // 返回信息
    private String msg;
    // 返回数据
    private Map<String, Object> data = new HashMap<>();

    // 私有化构造函数单例
    private R () {}

    public static R succeed() {
        R r = new R();
        r.setCode(R.SUCCEED);
        r.setMsg("请求成功");
        return r;
    }

    public static R fail() {
        R r = new R();
        r.setCode(R.FAILED);
        r.setMsg("请求失败");
        return r;
    }

    public static R unAuthenticate() {
        R r = new R();
        r.setCode(R.UN_AUTHENTICATED);
        r.setMsg("认证失败");
        return r;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public R data(String k, Object v) {
        this.data.put(k, v);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
