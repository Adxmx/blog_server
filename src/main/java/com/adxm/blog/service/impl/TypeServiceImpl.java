package com.adxm.blog.service.impl;

import com.adxm.blog.entity.Blog;
import com.adxm.blog.entity.Type;
import com.adxm.blog.mapper.BlogMapper;
import com.adxm.blog.mapper.TypeMapper;
import com.adxm.blog.service.TypeService;
import com.adxm.blog.utils.CustomWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/10/9
 * @Description:
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    // 分页查询
    @Override
    public Map<String, Object> typeRetrieve(Page<Type> page, Type type, String sorter) {
        Wrapper wrapper = CustomWrapper.generateWrapper("tp", type, sorter, null, null);
        List<Type> types = typeMapper.queryTypeList(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("types", types);

        return map;
    }

    @Override
    public Type typeDetail(int id) {
        Type type = typeMapper.selectById(id);
        return type;
    }

    // 插入操作
    @Override
    public boolean isTypeCreate(int userId, Type type) {
        QueryWrapper<Type> wrapper =new QueryWrapper<>();
        wrapper.eq("label", type.getLabel());
        // 查重，若重复存在则不插入
        if(typeMapper.selectOne(wrapper) == null) {
            // 填充操作人用户的id
            type.setUserId(userId);
            typeMapper.insert(type);
            return true;
        }

        return false;
    }

    @Override
    public boolean isTypeDelete(int id) {
        // 查询要删除的实体是否存在
        if (typeMapper.selectById(id) == null) {
            return false;
        }
        // 查询该实体是否关联其他实体
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();
        wrapper.eq("type_id", id);
        if(blogMapper.selectOne(wrapper) != null) {
            return false;
        }
        typeMapper.deleteById(id);
        return true;
    }

    @Override
    public boolean isTypeUpdate(int userId, Type type) {
        // 查询要修改的实体是否存在
        if (typeMapper.selectById(type.getId()) == null) {
            return false;
        }
        // 查重分类名字是否重复
        QueryWrapper<Type> wrapper =new QueryWrapper<>();
        wrapper.eq("label", type.getLabel());
        if(typeMapper.selectOne(wrapper) != null) {
            return false;
        }
        // 记录操作人
        type.setUserId(userId);
        typeMapper.updateById(type);
        return true;
    }
}
