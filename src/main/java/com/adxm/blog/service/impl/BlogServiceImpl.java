package com.adxm.blog.service.impl;

import com.adxm.blog.entity.Blog;
import com.adxm.blog.mapper.BlogMapper;
import com.adxm.blog.service.BlogService;
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
 * @Date: 2022/10/10
 * @Description:
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map<String, Object> blogRetrieve(int userId, Page<Blog> page, Blog blog, String sorter) {
        // 插入登录者ID
        blog.setUserId(userId);
        Wrapper wrapper = CustomWrapper.generateWrapper("b", blog, sorter, null, null);
        List<Blog> blogs = blogMapper.queryBlogList(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("blogs", blogs);

        return map;
    }

    @Override
    public Blog blogDetail(int id) {
        // 添加查询条件
        Blog blog = new Blog();
        blog.setId(id);
        // TODO 判断是否登录ID
        if (true) {
            blog.setUserId(1);
        }
        Wrapper wrapper = CustomWrapper.generateWrapper("b", blog, null, null, null);
        return blogMapper.queryBlogById(wrapper);
    }

    // 插入操作
    @Override
    public boolean isBlogCreate(int userId, Blog blog) {
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();
        wrapper.eq("title", blog.getTitle());
        // 查重，若重复存在则不插入
        if(blogMapper.selectOne(wrapper) == null) {
            // 填充操作人用户的id
            blog.setUserId(userId);
            blogMapper.insert(blog);
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlogUpdate(int userId, int id,  Blog blog) {
        // 判断要修改的和准备修改的博客是否一致
        if (blog.getId() != null) {
            if (id != blog.getId()) {
                return false;
            }
        } else {
            // 补充更新的博客id
            blog.setId(id);
        }
        // 查询要修改的实体是否存在
        Blog origBlog = blogMapper.selectById(id);
        if (origBlog == null) {
            return false;
        }
        // 校验是否用户的博客
        if (!this.isUserSelfBlog(userId, origBlog.getUserId())) {
            return false;
        }
        // 查重博客名字是否重复
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();
        wrapper.ne("id", blog.getId());
        wrapper.eq("title", blog.getTitle());
        wrapper.eq("user_id", blog.getUserId());
        if(blogMapper.selectOne(wrapper) != null) {
            return false;
        }
        // 记录操作人
        blog.setUserId(userId);
        blogMapper.updateById(blog);
        return true;
    }

    @Override
    public boolean isBlogDelete(int userId, int id) {
        Blog blog = blogMapper.selectById(id);
        // 校验是否用户的博客
        if (blog == null) {
            return false;
        }
        // 校验是否用户的博客
        if (!this.isUserSelfBlog(userId, blog.getUserId())) {
            return false;
        }
        blogMapper.deleteById(id);
        return true;
    }

    // 检查是否是用户的博客
    public boolean isUserSelfBlog(int userId, int blogUserId) {
        return userId == blogUserId;
    }
}
