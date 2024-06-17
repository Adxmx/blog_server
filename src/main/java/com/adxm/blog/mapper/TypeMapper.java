package com.adxm.blog.mapper;

import com.adxm.blog.entity.Type;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
//@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    List<Type> queryTypeList(Page<Type> page, @Param(Constants.WRAPPER) Wrapper<Type> wrapper);
}
