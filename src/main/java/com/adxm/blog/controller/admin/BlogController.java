package com.adxm.blog.controller.admin;

import com.adxm.blog.entity.Blog;
import com.adxm.blog.service.BlogService;
import com.adxm.blog.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@RestController
@RequestMapping("/api/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /******************************分页查询博客********************************************/
    @GetMapping("/blog")
    public R blogList(HttpServletRequest request,
                      @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                      @RequestParam(value = "sorter", required = false) String sorter,
                      Blog blog) {
        int userId = (int) request.getAttribute("userId");
        // 创建page对象
        Page<Blog> page = new Page<>(current, pageSize);
        Map<String, Object> map = blogService.blogRetrieve(userId, page, blog, sorter);
        R r = R.succeed().msg("查询成功").data(map);
        return r;
    }

    /******************************单个博客查询********************************************/
    @GetMapping("/blog/{id}")
    public R blogDetail(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        Blog blog = blogService.blogDetail(id);
        if (blog == null) {
            r = R.fail().msg("博客不存在");
        } else {
            r = R.succeed().msg("查询成功").data("blog", blog);
        }
        return r;
    }

    /******************************新增博客********************************************/
    @PostMapping("/blog")
    public R blogCreate(HttpServletRequest request, @RequestBody Blog blog) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (blogService.isBlogCreate(userId, blog)) {
            r = R.succeed().msg("新增成功");
        } else {
            r = R.fail().msg("存在相同标题");
        }
        return r;
    }

    /******************************编辑博客********************************************/
    @PutMapping("/blog/{id}")
    public R blogUpdate(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id, @RequestBody Blog blog) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (blogService.isBlogUpdate(userId, id, blog)) {
            r = R.succeed().msg("编辑成功");
        } else {
            r = R.fail().msg("存在相同标题");
        }
        return r;
    }

    /******************************删除博客********************************************/
    @DeleteMapping("/blog/{id}")
    public R blogUpdate(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (blogService.isBlogDelete(userId, id)) {
            r = R.succeed().msg("删除成功");
        } else {
            r = R.fail().msg("删除失败");
        }
        return r;
    }

}
