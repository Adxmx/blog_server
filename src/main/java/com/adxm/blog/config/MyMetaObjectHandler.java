package com.adxm.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时自动填充创建和更新时间
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", () -> new Date() , Date.class);
        this.strictInsertFill(metaObject, "modifiedTime", () -> new Date() , Date.class);
    }

    // 更新时自动填充更新时间
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "modifiedTime", () -> new Date() , Date.class);
    }
}
