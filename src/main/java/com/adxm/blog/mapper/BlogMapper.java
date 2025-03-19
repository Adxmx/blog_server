package com.adxm.blog.mapper;

import com.adxm.blog.entity.Blog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
//@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    public List<Blog> queryBlogList(Page<Blog> page, @Param(Constants.WRAPPER) Wrapper<Blog> wrapper);

    public Blog queryBlogById(@Param(Constants.WRAPPER) Wrapper<Blog> wrapper);

    public Map<String, Object> queryBlogOverview(@Param(Constants.WRAPPER) Wrapper<Blog> wrapper);
}
