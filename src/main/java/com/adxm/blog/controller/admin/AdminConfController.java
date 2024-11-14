package com.adxm.blog.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adxm.blog.entity.Conf;
import com.adxm.blog.service.ConfService;
import com.adxm.blog.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin")
public class AdminConfController {

    @Autowired
    private ConfService confService;

    /******************************分页查询配置********************************************/
    @GetMapping("/conf")
    public R confList(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                      @RequestParam(value = "sorter", required = false) String sorter,
                      Conf conf) {
        // 创建page对象
        Page<Conf> page = new Page<>(current, pageSize);
        Map<String, Object> map = confService.confRetrieve(page, conf, sorter);
        R r = R.succeed().msg("查询成功").data(map);
        return r;
    }

    /******************************单个配置查询********************************************/
    @GetMapping("/conf/{id:[0-9]+}")
    public R confDetail(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        Conf conf = confService.confDetail(id);
        if (conf == null) {
            r = R.fail().msg("配置不存在");
        } else {
            r = R.succeed().msg("查询成功").data("conf", conf);
        }
        return r;
    }

    /******************************新增配置********************************************/
    @PostMapping("/conf")
    public R confCreate(HttpServletRequest request, @RequestBody Conf conf) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (confService.isConfCreate(userId, conf)) {
            r = R.succeed().msg("新增成功");
        } else {
            r = R.fail().msg("存在相同code");
        }
        return r;
    }

    /******************************编辑配置********************************************/
    @PutMapping("/conf/{id}")
    public R confUpdate(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id, @RequestBody Conf conf) {
        int userId = (int) request.getAttribute("userId");
        R r = null;
        if (confService.isConfUpdate(userId, conf)) {
            r = R.succeed().msg("编辑成功");
        } else {
            r = R.fail().msg("存在相同编码");
        }
        return r;
    }

    /******************************删除配置********************************************/
    @DeleteMapping("/conf/{id}")
    public R confDelete(@PathVariable(value = "id", required = true) Integer id) {
        R r = null;
        if (confService.isConfDelete(id)) {
            r = R.succeed().msg("删除成功");
        } else {
            r = R.fail().msg("删除失败");
        }
        return r;
    }
    
}
