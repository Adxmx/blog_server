package com.adxm.blog.service;

import com.adxm.blog.entity.Type;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
public interface TypeService extends IService<Type> {

    public Map<String, Object> typeRetrieve(Page<Type> page, Type type, String sorter);

    public Type typeDetail(int id);

    public boolean isTypeCreate(int userId, Type type);

    public boolean isTypeDelete(int id);

    public boolean isTypeUpdate(int userId, Type type);



}
