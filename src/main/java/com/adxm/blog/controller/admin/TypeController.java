package com.adxm.blog.controller.admin;

import com.adxm.blog.entity.Type;
import com.adxm.blog.service.TypeService;
import com.adxm.blog.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/10/8
 * @Description:
 */

@RestController
@RequestMapping("/api/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /******************************分页查询分类********************************************/
    @GetMapping("/type")
    public R typeList(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                      @RequestParam(value = "sorter", required = false) String sorter,
                      Type type) {
        // 创建page对象
        Page<Type> page = new Page<>(current, pageSize);
        Map<String, Object> map = typeService.typeRetrieve(page, type, sorter);
        R r = R.succeed().msg("查询成功").data(map);
        return r;
    }

    /******************************单个分类查询********************************************/
    @GetMapping("/type/{id}")
    public R typeDetail(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        Type type = typeService.typeDetail(id);
        if (type == null) {
            r = R.fail().msg("分类不存在");
        } else {
            r = R.succeed().msg("查询成功").data("type", type);
        }
        return r;
    }

    /******************************新增分类********************************************/
    @PostMapping("/type")
    public R typeCreate(HttpServletRequest request, @RequestBody Type type) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (typeService.isTypeCreate(userId, type)) {
            r = R.succeed().msg("新增成功");
        } else {
            r = R.fail().msg("存在相同分类");
        }
        return r;
    }

    /******************************编辑分类********************************************/
    @PutMapping("/type/{id}")
    public R typeUpdate(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id, @RequestBody Type type) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (typeService.isTypeUpdate(userId, type)) {
            r = R.succeed().msg("编辑成功");
        } else {
            r = R.fail().msg("存在相同分类");
        }
        return r;
    }

    /******************************删除分类********************************************/
    @DeleteMapping("/type/{id}")
    public R typeDelete(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        if (typeService.isTypeDelete(id)) {
            r = R.succeed().msg("删除成功");
        } else {
            r = R.fail().msg("该分类已关联博客");
        }
        return r;
    }
}
