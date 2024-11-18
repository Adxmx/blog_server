package com.adxm.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @TableField(select = false)
    private String password;
    private String nickname;
    private String avatar;
    private String signature;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    // 实体关系字段
    @TableField(exist = false)
    private List<Type> types;
    @TableField(exist = false)
    private List<Blog> blogs;
}
