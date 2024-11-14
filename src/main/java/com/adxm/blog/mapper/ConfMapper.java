package com.adxm.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

// import org.apache.ibatis.annotations.Mapper;

import com.adxm.blog.entity.Conf;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

// @Mapper
public interface ConfMapper extends BaseMapper<Conf>{
    List<Conf> queryConfList(Page<Conf> page, @Param(Constants.WRAPPER) Wrapper<Conf> wrapper);
}
