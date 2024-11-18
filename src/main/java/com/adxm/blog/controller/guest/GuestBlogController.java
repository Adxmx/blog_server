package com.adxm.blog.controller.guest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adxm.blog.async.BlogAsync;
import com.adxm.blog.entity.Blog;
import com.adxm.blog.service.BlogService;
import com.adxm.blog.utils.CustomWrapper;
import com.adxm.blog.utils.FieldFormat;
import com.adxm.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.netty.util.internal.StringUtil;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class GuestBlogController {
    
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogAsync blogAsync;

    /******************************博客详情查询********************************************/
    @GetMapping("/blog/{id}")
    public R blogDetail(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        // 异步调用博客浏览+1方法
        blogAsync.viewIncrement(id);
        Blog blog = blogService.blogDetail(null, id);
        if (blog == null) {
            r = R.fail().msg("博客不存在");
        } else {
            r = R.succeed().msg("查询成功").data("blog", blog);
        }
        return r;
    }

    /******************************博客条目查询********************************************/
    @GetMapping("/blog/item")
    public R blogItem(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                      @RequestParam(value = "sorter", required = false) String sorter,
                      @RequestParam(value = "typeIds", required = false) Integer[] typeIds,
                      Blog blog) {
        QueryWrapper queryWrapper = null;
        // 查询分类列表目录下的博客列表
        if (typeIds != null && typeIds.length > 0) {
            queryWrapper = new QueryWrapper(); 
            queryWrapper.in("type_id", typeIds);
            // 补充查询排序
            if (!StringUtil.isNullOrEmpty(sorter)) {
                String field = FieldFormat.humpToUnderline(sorter);
                if (field.startsWith("-")) {
                    queryWrapper.orderByDesc(field.substring(1));
                } else {
                    queryWrapper.orderByAsc(field);
                }
            }
            // 补充查询长度
            if (limit != null) {
                queryWrapper.last("limit " + limit);
            }
        // 查询指定条件下的博客列表
        } else {
            // 游客查询指定用户下博客列表，则只查询非匿名博客
            if (blog.getUserId() != null) {
                blog.setIsAnon(false);
            }
            Wrapper wrapper = CustomWrapper.generateWrapper(null, blog, sorter, null, null, limit);
            queryWrapper = (QueryWrapper) wrapper;
        }
        queryWrapper.eq("is_published", true);
        queryWrapper.select("id", "title", "cover", "description", "created_time");
        List<Blog> blogs = blogService.blogRetrieve(queryWrapper);
        R r = R.succeed().msg("查询成功").data("blogItems", blogs);
        return r;
    }

    /******************************博客分页查询********************************************/
    @GetMapping("/blog/list")
    public R blogList(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                      @RequestParam(value = "sorter", required = false) String sorter,
                      Blog blog) {
        // 限制查询已发布博客
        blog.setIsPublished(true);
        // 游客查询指定用户下博客列表，则只查询非匿名博客
        if (blog.getUserId() != null) {
            blog.setIsAnon(false);
        }
        // 创建page对象
        Page<Blog> page = new Page<>(current, pageSize);
        Map<String, Object> map = blogService.blogRetrieve(null, page, blog, sorter);
        R r = R.succeed().msg("查询成功").data(map);
        return r;
    }

    /******************************博客点赞接口********************************************/
    @GetMapping("/blog/thumb/{id}")
    public R giveThumb(@PathVariable(value = "id", required = true) Integer id) {
        // 异步调用博客点赞+1方法
        blogAsync.thumbIncrement(id);
        return R.succeed().msg("点赞成功");
    }
    
}
