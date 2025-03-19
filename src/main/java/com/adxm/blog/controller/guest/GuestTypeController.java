package com.adxm.blog.controller.guest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adxm.blog.entity.Blog;
import com.adxm.blog.entity.Type;
import com.adxm.blog.service.TypeService;
import com.adxm.blog.utils.CustomWrapper;
import com.adxm.blog.utils.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@RestController
@RequestMapping("/api")
public class GuestTypeController {

  @Autowired
  private  TypeService typeService;

    /******************************标签条目查询********************************************/
    @GetMapping("/type/item")
    public R typeItem(@RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit,
                      @RequestParam(value = "ids", required = false) Integer[] ids,
                      Type type) {
        Wrapper wrapper = CustomWrapper.generateWrapper(null, type, "-createdTime", null, null, limit);
        QueryWrapper queryWrapper = (QueryWrapper) wrapper;

        if (ids != null && ids.length > 0) {
            queryWrapper.in("id", ids);
        }

        queryWrapper.select("id", "label");
        List<Type> types = typeService.typeRetrieve(queryWrapper);
        R r = R.succeed().msg("查询成功").data("types", types);
        return r;
    }

    /******************************标签详情查询********************************************/
    @GetMapping("/type/{id}")
    public R typeDetail(@PathVariable(value = "id", required = true) Integer id) {
      R r = null;
      Type type = typeService.typeDetail(id);
      if (type == null) {
        r = R.fail().msg("标签不存在");
      } else {
        r = R.succeed().msg("查询成功").data("type", type);
      }
      return r;
    }
    
}
