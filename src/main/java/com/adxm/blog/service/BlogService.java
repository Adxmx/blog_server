package com.adxm.blog.service;

import com.adxm.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
public interface BlogService extends IService<Blog> {

    public Map<String, Object> blogRetrieve(int userId, Page<Blog> page, Blog blog, String sorter);

    public Blog blogDetail(int id);

    public boolean isBlogCreate(int userId, Blog blog);

    public boolean isBlogUpdate(int userId, int id, Blog blog);

    public boolean isBlogDelete(int userId, int id);
}
