package com.adxm.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "t_conf")
public class Conf {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String code;
    private Object config;
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    private Integer userId;

    // 实体关系字段
    @TableField(exist = false)
    private User user;
}
