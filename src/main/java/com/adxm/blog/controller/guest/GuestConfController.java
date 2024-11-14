package com.adxm.blog.controller.guest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adxm.blog.entity.Conf;
import com.adxm.blog.service.ConfService;
import com.adxm.blog.utils.JsonUtils;
import com.adxm.blog.utils.R;

@RestController
@RequestMapping("/api")
public class GuestConfController {
  
   @Autowired
    private ConfService confService;

    /******************************配置详情查询********************************************/
    @GetMapping("/conf/{code:[a-zA-Z_]+}")
    public R ConfDetail(@PathVariable(value = "code", required = true) String code) {
        R r = null;
        Conf conf = confService.confDetail(code);
        if (conf == null) {
            r = R.fail().msg("配置不存在");
        } else {
            String json = (String) conf.getConfig();
            Object o = JsonUtils.deserialize(json);
            r = R.succeed().msg("查询成功").data("config", o);
        }
        return r;
    }
}
