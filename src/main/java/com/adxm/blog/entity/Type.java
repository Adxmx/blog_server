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
@TableName("t_type")
public class Type {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String label;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    private Integer userId;

    // 实体关系字段
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<Blog> blogs;
}
