package com.adxm.blog.utils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;

/**
 * @Author: Adxm
 * @Date: 2022/10/9
 * @Description:
 */
public class CustomWrapper {
    // 自动生成查询条件wrapper
    public static <T> Wrapper generateWrapper(String tableAlias, T t, String sorterField, String[] searchFields, String searchValue, Integer limit) {
        try {
            QueryWrapper<T> wrapper = new QueryWrapper<>();

            // 处理表别名
            tableAlias = tableAlias == null ? "" : tableAlias + ".";

            // 处理sorterField字段
            // false为降序，true为升序
            Boolean sorterFlag = null;
            if (sorterField != null) {
                if (sorterField.startsWith("-")) {
                    sorterField = FieldFormat.humpToUnderline(sorterField.substring(1));
                    sorterFlag = false;
                } else {
                    sorterField = FieldFormat.humpToUnderline(sorterField);
                    sorterFlag = true;
                }
            }

            // 遍历对象属性
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                // 自动生成wrapper中的列名
                String underlineName = FieldFormat.humpToUnderline(field.getName());
                String columnFields = tableAlias + underlineName;
                // 自动过滤条件
                wrapper.eq(field.get(t) != null, columnFields, field.get(t));
                // 自动化排序条件
                wrapper.orderByAsc(sorterFlag != null && sorterFlag && sorterField.equals(underlineName), columnFields);
                wrapper.orderByDesc(sorterFlag != null && !sorterFlag && sorterField.equals(underlineName), columnFields);
            }

            // 长度限制
            if (limit != null) {
                wrapper.last("limit " + limit);
            }

            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
