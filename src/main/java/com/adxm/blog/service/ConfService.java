package com.adxm.blog.service;

import java.util.Map;

import com.adxm.blog.entity.Conf;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ConfService extends IService<Conf> {

    public Map<String, Object> confRetrieve(Page<Conf> page, Conf conf, String sorter);

    public Conf confDetail(int id);

    public Conf confDetail(String code);

    public boolean isConfCreate(int userId, Conf conf);

    public boolean isConfDelete(int id);

    public boolean isConfUpdate(int userId, Conf conf);
}