package com.adxm.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adxm.blog.entity.Conf;
import com.adxm.blog.mapper.ConfMapper;
import com.adxm.blog.service.ConfService;
import com.adxm.blog.utils.CustomWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfServiceImpl extends ServiceImpl<ConfMapper, Conf> implements ConfService {

    @Autowired
    private ConfMapper confMapper;

    @Override
    public Map<String, Object> confRetrieve(Page<Conf> page, Conf conf, String sorter) {
        Wrapper wrapper = CustomWrapper.generateWrapper("tc", conf, sorter, null, null, null);
        List<Conf> confs = confMapper.queryConfList(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("confs", confs);

        return map;
    }

    @Override
    public Conf confDetail(int id) {
        Conf conf = confMapper.selectById(id);
        return conf;
    }

    @Override
    public Conf confDetail(String code) {
        QueryWrapper<Conf> wrapper = new QueryWrapper<>();
        wrapper.select("config");
        wrapper.eq("code", code);
        Conf conf = confMapper.selectOne(wrapper);
        return conf;
        // try {
        //     config = new ObjectMapper().readValue((String) conf.getConfig(), new TypeReference<Map<String, Object>>() {});
        // } catch (JsonProcessingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // return config;
    }

    @Override
    public boolean isConfCreate(int userId, Conf conf) {
        QueryWrapper<Conf> wrapper =new QueryWrapper<>();
        wrapper.eq("code", conf.getCode());
        // 查重，若重复存在则不插入
        if(confMapper.selectOne(wrapper) == null) {
            // 填充操作人用户的id
            conf.setUserId(userId);
            confMapper.insert(conf);
            return true;
        }

        return false;
    }

    @Override
    public boolean isConfDelete(int id) {
        // 查询要删除的实体是否存在
        if (confMapper.selectById(id) == null) {
            return false;
        }
        confMapper.deleteById(id);
        return true;
    }

    @Override
    public boolean isConfUpdate(int userId, Conf conf) {
        // 查询要修改的实体是否存在
        if (confMapper.selectById(conf.getId()) == null) {
            return false;
        }
        // 查重配置编码是否重复
        QueryWrapper<Conf> wrapper =new QueryWrapper<>();
        wrapper.ne("id", conf.getId());
        wrapper.eq("code", conf.getCode());
        if(confMapper.selectOne(wrapper) != null) {
            return false;
        }
        // 记录操作人
        conf.setUserId(userId);
        confMapper.updateById(conf);
        return true;
    }
}