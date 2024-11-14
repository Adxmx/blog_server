/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 18/06/2024 02:22:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cover` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面地址',
  `flag` tinyint(1) NOT NULL COMMENT '博客来源: 原创，转载，翻译',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '博客名称',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '博客正文',
  `description` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客简介',
  `editor` tinyint(1) NOT NULL COMMENT '编辑器：富文本，Markdown',
  `is_recommend` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否主页推荐',
  `is_copyright` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否声明版权',
  `is_published` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否主页出版',
  `is_anon` bit(1) NOT NULL COMMENT '是否匿名发布',
  `is_comment` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否允许评论',
  `is_reward` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启打赏',
  `view` int(11) NOT NULL DEFAULT 0 COMMENT '浏览量',
  `thumb` int(11) NOT NULL DEFAULT 0 COMMENT '点赞量',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  `type_id` int(11) NOT NULL COMMENT '关联分类表',
  `user_id` int(11) NOT NULL COMMENT '关联用户表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `label` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名字',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '关联用户表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL COMMENT '主键id',
  `username` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `nickname` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像地址',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_conf`;
CREATE TABLE `t_conf` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(127) NOT NULL COMMENT '配置名称',
  `code` varchar(127) NOT NULL COMMENT '配置编码',
  `config` json NOT NULL COMMENT '配置内容',
  `description` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置描述',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  `user_id` int NOT NULL COMMENT '关联用户表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
