package com.adxm.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Adxm
 * @Date: 2022/9/19
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("t_blog")
public class Blog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String cover;
    private Byte flag;
    private String title;
    private String content;
    private String description;
    private Byte editor;
    private Boolean isRecommend;
    private Boolean isCopyright;
    private Boolean isPublished;
    private Boolean isAnon;
    private Boolean isComment;
    private Boolean isReward;
    private Integer view;
    private Integer thumb;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    private Integer typeId;
    private Integer userId;

    // 实体关系字段
    @TableField(exist = false)
    private Type type;
    @TableField(exist = false)
    private User user;
}

