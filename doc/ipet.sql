/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : ipet

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 19/10/2018 14:55:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户表PK',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LDAP密码',
  `name` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留默认空',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱默认空',
  `type` int(1) NULL DEFAULT NULL COMMENT '2为LDAP账号，1为本地账号',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '区域id 0为全部区域',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `create_time` timestamp(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '0表示系统、账户id',
  `enabled` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_area_fk`(`area_id`) USING BTREE,
  CONSTRAINT `user_area_fk` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'admin', 'oJV4KspKOuRtsZD2SXHQ7w==', '超级管理员', '', 1, 2, 1, '2018-07-31 10:03:24.000000', '2018-07-31 10:03:28.000000', 1);
INSERT INTO `account` VALUES (2, 'test_pm', 'oJV4KspKOuRtsZD2SXHQ7w==', '测试项目经理', '', 1, 1, 1, '2018-10-17 14:23:48.193319', '2018-10-17 14:23:48.193319', 1);
INSERT INTO `account` VALUES (3, 'test_manager', 'oJV4KspKOuRtsZD2SXHQ7w==', '测试管理员', '', 1, 1, 1, '2018-10-17 14:25:56.869740', '2018-10-17 14:25:56.869740', 1);
INSERT INTO `account` VALUES (8, 'yangcheng', NULL, '杨成', 'yangcheng@sensetime.com', 2, 1, 1, '2018-10-09 17:04:27.751883', '2018-10-09 17:04:27.751883', 1);
INSERT INTO `account` VALUES (9, 'daijiangzhe', NULL, '代江哲', 'daijiangzhe@sensetime.com', 2, 1, 1, '2018-10-09 17:04:27.757071', '2018-10-09 17:04:27.757071', 1);
INSERT INTO `account` VALUES (10, 'xiangtao', NULL, '项韬', 'xiangtao@sensetime.com', 2, 1, 1, '2018-10-09 17:04:27.759175', '2018-10-09 17:04:27.759175', 1);
INSERT INTO `account` VALUES (11, 'cuibinbin', NULL, '崔彬彬', 'cuibinbin@sensetime.com', 2, 4, 1, '2018-10-09 17:04:27.760588', '2018-10-09 17:04:27.760588', 1);
INSERT INTO `account` VALUES (12, 'zuochangming', NULL, '左昌明', 'zuochangming@sensetime.com', 2, 2, 1, '2018-10-09 17:04:27.761991', '2018-10-09 17:04:27.761991', 1);
INSERT INTO `account` VALUES (13, 'hudongli', NULL, '胡东利', 'hudongli@sensetime.com', 2, 3, 1, '2018-10-09 17:04:27.763237', '2018-10-09 17:04:27.763237', 1);
INSERT INTO `account` VALUES (14, 'xiahaibo', NULL, '夏海波', 'xiahaibo@sensetime.com', 2, 3, 1, '2018-10-09 17:04:27.764564', '2018-10-09 17:04:27.764564', 1);
INSERT INTO `account` VALUES (15, 'gaozhonghu', NULL, '高中虎', 'gaozhonghu@sensetime.com', 2, 2, 1, '2018-10-09 17:04:27.766000', '2018-10-09 17:04:27.766000', 1);
INSERT INTO `account` VALUES (16, 'xurui', NULL, '徐锐', 'xurui@sensetime.com', 2, 4, 1, '2018-10-09 17:04:27.767580', '2018-10-09 17:04:27.767580', 1);
INSERT INTO `account` VALUES (17, 'wujianwei', NULL, '吴健伟', 'wujianwei@sensetime.com', 2, 4, 1, '2018-10-09 17:04:27.769867', '2018-10-09 17:04:27.769867', 1);
INSERT INTO `account` VALUES (18, 'wangzhihao', NULL, '王志豪', 'wangzhihao@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.772198', '2018-10-09 17:04:27.772198', 1);
INSERT INTO `account` VALUES (19, 'gejiafeng', NULL, '葛佳枫', 'gejiafeng@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.773585', '2018-10-09 17:04:27.773585', 1);
INSERT INTO `account` VALUES (20, 'lijun', NULL, '李军', 'lijun@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.775005', '2018-10-09 17:04:27.775005', 1);
INSERT INTO `account` VALUES (21, 'lijiangjiang', NULL, '李江江', 'lijiangjiang@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.776244', '2018-10-09 17:04:27.776244', 1);
INSERT INTO `account` VALUES (22, 'wujie', NULL, '邬劼', 'wujie@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.777440', '2018-10-09 17:04:27.777440', 1);
INSERT INTO `account` VALUES (23, 'duguxuexian', NULL, '独孤学衔', 'duguxuexian@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.778913', '2018-10-09 17:04:27.778913', 1);
INSERT INTO `account` VALUES (24, 'fengyanan', NULL, '冯亚楠', 'fengyanan@sensetime.com', 2, 5, 1, '2018-10-09 17:04:27.780360', '2018-10-09 17:04:27.780360', 1);
INSERT INTO `account` VALUES (25, 'liyanhe', NULL, '李彦鹤', 'liyanhe@sensetime.com', 2, 6, 1, '2018-10-09 17:04:27.781929', '2018-10-09 17:04:27.781929', 1);
INSERT INTO `account` VALUES (27, 'yangfan', NULL, '杨帆', 'yangfan@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.963606', '2018-10-09 17:07:38.963606', 1);
INSERT INTO `account` VALUES (28, 'hanxinyao', NULL, '韩昕瑶', 'hanxinyao@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.969138', '2018-10-09 17:07:38.969138', 1);
INSERT INTO `account` VALUES (29, 'jiahaigang', NULL, '贾海刚', 'jiahaigang@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.970753', '2018-10-09 17:07:38.970753', 1);
INSERT INTO `account` VALUES (30, 'aichunling', NULL, '艾春伶', 'aichunling@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.972379', '2018-10-09 17:07:38.972379', 1);
INSERT INTO `account` VALUES (31, 'wuyong', NULL, '吴勇', 'wuyong@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.974255', '2018-10-09 17:07:38.974255', 1);
INSERT INTO `account` VALUES (32, 'zhaozhanpeng', NULL, '赵展鹏', 'zhaozhanpeng@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.976247', '2018-10-09 17:07:38.976247', 1);
INSERT INTO `account` VALUES (33, 'xujingxian', NULL, '徐静娴', 'xujingxian@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.978649', '2018-10-09 17:07:38.978649', 1);
INSERT INTO `account` VALUES (34, 'shilijun', NULL, '施丽君', 'shilijun@sensetime.com\r\nshilijun@sensetime.com\r\nshilijun@sensetime.com', 2, NULL, 1, '2018-10-09 17:07:38.980116', '2018-10-09 17:07:38.980116', 1);
INSERT INTO `account` VALUES (35, 'zhoujing_venodr', NULL, '周静', 'zhoujing_vendor@sensetime.com', 2, 5, 1, '2018-10-15 12:30:35.482722', '2018-10-15 12:30:35.482722', 1);
INSERT INTO `account` VALUES (36, 'fanjiamin', NULL, '范佳敏', 'fanjiamin@sensetime.com', 2, 1, 1, '2018-10-16 10:55:59.599515', '2018-10-16 10:55:59.599515', 1);
INSERT INTO `account` VALUES (37, 'linyuanhong', NULL, '林远宏', 'linyuahong@sensetime.com', 2, 1, 1, '2018-10-16 10:55:58.074388', '2018-10-16 10:55:58.074388', 1);
INSERT INTO `account` VALUES (38, 'lvyichen', NULL, '吕小花', 'lvyichen@sensetime.com', 2, 2, 1, '2018-10-16 14:07:26.229333', '2018-10-16 14:07:26.229333', 1);

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account_id` int(10) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `account_role_account_key` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `account_role_role_key` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES (1, 1, 3);
INSERT INTO `account_role` VALUES (2, 2, 3);
INSERT INTO `account_role` VALUES (3, 3, 2);
INSERT INTO `account_role` VALUES (4, 8, 3);
INSERT INTO `account_role` VALUES (5, 9, 3);
INSERT INTO `account_role` VALUES (6, 10, 3);
INSERT INTO `account_role` VALUES (7, 11, 3);
INSERT INTO `account_role` VALUES (8, 12, 3);
INSERT INTO `account_role` VALUES (9, 13, 3);
INSERT INTO `account_role` VALUES (10, 14, 3);
INSERT INTO `account_role` VALUES (11, 15, 3);
INSERT INTO `account_role` VALUES (12, 16, 3);
INSERT INTO `account_role` VALUES (13, 17, 3);
INSERT INTO `account_role` VALUES (14, 18, 3);
INSERT INTO `account_role` VALUES (15, 19, 3);
INSERT INTO `account_role` VALUES (16, 20, 3);
INSERT INTO `account_role` VALUES (17, 21, 3);
INSERT INTO `account_role` VALUES (18, 22, 3);
INSERT INTO `account_role` VALUES (19, 23, 3);
INSERT INTO `account_role` VALUES (20, 24, 3);
INSERT INTO `account_role` VALUES (21, 25, 3);
INSERT INTO `account_role` VALUES (22, 27, 2);
INSERT INTO `account_role` VALUES (23, 28, 2);
INSERT INTO `account_role` VALUES (24, 29, 2);
INSERT INTO `account_role` VALUES (25, 30, 2);
INSERT INTO `account_role` VALUES (26, 31, 2);
INSERT INTO `account_role` VALUES (27, 32, 2);
INSERT INTO `account_role` VALUES (28, 33, 2);
INSERT INTO `account_role` VALUES (29, 34, 2);
INSERT INTO `account_role` VALUES (30, 35, 3);
INSERT INTO `account_role` VALUES (31, 36, 3);
INSERT INTO `account_role` VALUES (32, 37, 3);
INSERT INTO `account_role` VALUES (33, 38, 3);

-- ----------------------------
-- Table structure for apply_list
-- ----------------------------
DROP TABLE IF EXISTS `apply_list`;
CREATE TABLE `apply_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实施清单表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `stage` int(2) NULL DEFAULT NULL COMMENT '阶段',
  `task_list` int(2) NULL DEFAULT NULL COMMENT '任务项',
  `detail_job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '具体工作',
  `workload` float(5, 2) NULL DEFAULT NULL COMMENT '工作量(人天)',
  `execute_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
  `problem_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题、风险、备注',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `apply_list_project_fk`(`project_id`) USING BTREE,
  CONSTRAINT `apply_list_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域表PK',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `note` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认空',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (1, '南一区', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', '南一区');
INSERT INTO `area` VALUES (2, '南二区+海外', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', '南二区+海外');
INSERT INTO `area` VALUES (3, '中西区', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', '中西区');
INSERT INTO `area` VALUES (4, '北区', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', '北区');
INSERT INTO `area` VALUES (5, '上海特区', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', '上海特区');
INSERT INTO `area` VALUES (6, 'PK', '2018-07-31 15:35:11.139000', '2018-07-31 15:35:11.139000', 'PK');

-- ----------------------------
-- Table structure for business_system
-- ----------------------------
DROP TABLE IF EXISTS `business_system`;
CREATE TABLE `business_system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业务系统表PK',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统名称',
  `create_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business_system
-- ----------------------------
INSERT INTO `business_system` VALUES (1, '无需', '2018-08-17 01:12:25', '2018-08-16 16:18:52');
INSERT INTO `business_system` VALUES (2, 'SenseFace', '2018-08-17 01:12:25', '2018-08-16 16:18:52');
INSERT INTO `business_system` VALUES (3, 'SenseTotem', '2018-08-17 01:12:25', '2018-08-16 16:18:52');
INSERT INTO `business_system` VALUES (4, 'SenseCity', '2018-08-17 01:12:25', '2018-08-16 16:18:52');
INSERT INTO `business_system` VALUES (5, 'SenseCrowd', '2018-08-17 01:12:25', '2018-08-16 16:18:52');
INSERT INTO `business_system` VALUES (6, '其他', '2018-08-17 01:12:25', '2018-08-16 16:18:52');

-- ----------------------------
-- Table structure for business_system_platform
-- ----------------------------
DROP TABLE IF EXISTS `business_system_platform`;
CREATE TABLE `business_system_platform`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '平台表PK',
  `business_system_id` int(11) NULL DEFAULT NULL COMMENT '系统平台id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台名称',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `platform_business_system_id_fk`(`business_system_id`) USING BTREE,
  CONSTRAINT `platform_business_system_id_fk` FOREIGN KEY (`business_system_id`) REFERENCES `business_system` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business_system_platform
-- ----------------------------
INSERT INTO `business_system_platform` VALUES (1, 1, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (2, 1, 'SenseFace单机版服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (3, 1, 'SenseUnity', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (4, 1, 'SenseFoundry', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (5, 1, '静态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (6, 1, '动态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (7, 1, '结构化服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (8, 1, 'demo小盒子（演示版）', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (9, 1, 'SenseCity标准服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (10, 1, '人群分析系统', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (11, 1, 'PK服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (12, 1, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (13, 2, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (14, 2, 'SenseFace单机版服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (15, 2, '动态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (16, 2, 'SenseUnity', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (17, 2, 'SenseFoundry', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (18, 2, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (19, 3, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (20, 3, '静态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (21, 3, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (22, 4, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (23, 4, 'demo小盒子（演示版）', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (24, 4, 'SenseCity标准服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (25, 4, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (26, 5, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (27, 5, '人群分析系统', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (28, 5, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (29, 6, '无需', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (30, 6, 'SenseFace单机版服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (31, 6, 'SenseUnity', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (32, 6, 'SenseFoundry', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (33, 6, '静态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (34, 6, '动态比对服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (35, 6, '结构化服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (36, 6, 'demo小盒子（演示版）', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (37, 6, 'SenseCity标准服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (38, 6, '人群分析系统', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (39, 6, 'PK服务器', '2018-08-16 16:18:52', '2018-08-16 16:18:52');
INSERT INTO `business_system_platform` VALUES (40, 6, '其他', '2018-08-16 16:18:52', '2018-08-16 16:18:52');

-- ----------------------------
-- Table structure for business_trip
-- ----------------------------
DROP TABLE IF EXISTS `business_trip`;
CREATE TABLE `business_trip`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '差旅表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '立项id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出差人员',
  `type` int(1) NULL DEFAULT NULL COMMENT '出差类型(1:短途 2：长途)',
  `destination` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出差地',
  `start_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出发日期',
  `end_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '到达日期',
  `accommodation` float(14, 2) NULL DEFAULT NULL COMMENT '住宿费用RMB',
  `traffic` float(14, 2) NULL DEFAULT NULL COMMENT '交通费用RMB',
  `other` float(14, 2) NULL DEFAULT NULL COMMENT '其他RMB',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `work_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职能',
  `total` float(14, 2) NULL DEFAULT NULL COMMENT '总费用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `trip_project_approval_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `trip_project_approval_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for compare_date
-- ----------------------------
DROP TABLE IF EXISTS `compare_date`;
CREATE TABLE `compare_date`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of compare_date
-- ----------------------------
INSERT INTO compare_date VALUES (null,DATE_FORMAT(SYSDATE(),'%Y-%m'),SYSDATE(),SYSDATE());
-- ----------------------------
-- Table structure for deliver_list
-- ----------------------------
DROP TABLE IF EXISTS `deliver_list`;
CREATE TABLE `deliver_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交接清单表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交付物名称',
  `target` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交付对象',
  `type` int(2) NULL DEFAULT NULL COMMENT '交付物类型',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交付物路径',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '立项id',
  `pro_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品类型',
  `software_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件版本',
  `device_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `device_num` int(11) NULL DEFAULT NULL COMMENT '设备数量',
  `graphics_card_serial` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显卡编号',
  `graphics_card_num` int(11) NULL DEFAULT NULL COMMENT '显卡数量',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `equipmemt_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `equipmemt_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `upload_time` timestamp(0) NULL DEFAULT NULL COMMENT '上传时间',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '周报id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `file_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 266 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login_info
-- ----------------------------
DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆用户账号',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '登陆时间',
  `type` int(1) NULL DEFAULT NULL COMMENT '1：login  2：remember-me',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `content` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `event` int(2) NULL DEFAULT NULL COMMENT '事件',
  `send` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息发送者',
  `receiver_id` int(10) NULL DEFAULT NULL COMMENT '消息接收者id',
  `handle` tinyint(1) NULL DEFAULT NULL COMMENT '是否处理',
  `param1` int(10) NULL DEFAULT NULL COMMENT '事件相关参数1',
  `param2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件相关参数2',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('test_pm', '++OcmJKi5M1snBXrhjAm7g==', 'YuQDAB5ua8XVinhEbsItxg==', '2018-10-18 03:10:22');
INSERT INTO `persistent_logins` VALUES ('test_manager', '+73w4ZcOO895Qblb2ZAHvA==', 'gFNMNVxAiXWNao8MjO/IGw==', '2018-10-18 02:51:48');
INSERT INTO `persistent_logins` VALUES ('test_pm', '+8h41A4kGB5t9fbgTpx3jA==', '34Z2nRIZ+Ss+DtxQ/6V4VA==', '2018-10-17 08:18:23');
INSERT INTO `persistent_logins` VALUES ('test_pm', '/izyAyHGnyRzEAfQn7Q/Wg==', 'LWjuZ3wcAXkhTvOH2L4A1g==', '2018-10-19 01:34:06');
INSERT INTO `persistent_logins` VALUES ('test_pm', '0fyafWG7VuRMv2fnTKR0pg==', '7NtGxApiKtHy8fP7pdDtTg==', '2018-10-19 03:10:02');
INSERT INTO `persistent_logins` VALUES ('admin', '0Jj6eHlLsB183ib2U71jFw==', 'gF1JjU5Si8LvoSCS3w+mdg==', '2018-10-17 06:12:08');
INSERT INTO `persistent_logins` VALUES ('test_pm', '0My5Rgl/YwfmURDn2ArCUg==', '1xTr3whCfWLv1roi94n2WA==', '2018-10-18 08:01:46');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', '1assI4jgFoPFi07uWZ5+iA==', 'el54gE1FtbVTx43fSQVJug==', '2018-10-18 02:35:37');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', '1y7JNPER6wWk6tzwpp5y/g==', 'U2pIopXAJHuDJSSR2nMaVA==', '2018-10-17 10:27:33');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', '2fuQFAEQj1FIY8z68nO7uA==', 'ztWnm0bhuuCx8daJs3h1QQ==', '2018-10-15 15:59:01');
INSERT INTO `persistent_logins` VALUES ('test_pm', '2ljpITXo/wJ+z2MsnngbTQ==', '2MhYqpYSnA4BP0mdmpojHA==', '2018-10-17 12:13:22');
INSERT INTO `persistent_logins` VALUES ('test_manager', '2VfQJB9wG6tUOh2Qb8UxMw==', 'JWfMvfFl5hpxMPMDQIV9dw==', '2018-10-18 01:57:22');
INSERT INTO `persistent_logins` VALUES ('liyanhe', '3ch7iFBWQ0LNDgmNH2/Yfg==', 'xdNnVRphq/RLO2bbZWyLow==', '2018-10-16 09:40:50');
INSERT INTO `persistent_logins` VALUES ('test_manager', '3CnJi43VRZow7l+B/G/OOQ==', 'EVSXBzpwNk7gMF5hPCkdcQ==', '2018-10-17 06:58:36');
INSERT INTO `persistent_logins` VALUES ('test_pm', '4Z84Yn+qXCVxkT4xvkQIMQ==', 'sIadg4NqQLF3VszlT16GaA==', '2018-10-19 02:17:13');
INSERT INTO `persistent_logins` VALUES ('test_pm', '6Meg5Vk7GbScTYlaYmdaOQ==', 'dhXIj0W0BtIwdSEkR5z2Vg==', '2018-10-17 06:40:30');
INSERT INTO `persistent_logins` VALUES ('admin', '7OLheimh4V9zgxFDbQafSQ==', 'ZwwPwM43kjvF2n2AyBm5qw==', '2018-10-16 18:03:20');
INSERT INTO `persistent_logins` VALUES ('test_pm', '8iT1BI7e1x5cphi6aAzwMQ==', 'yrzg0pYjmN95TnUiMaZc+Q==', '2018-10-17 10:08:20');
INSERT INTO `persistent_logins` VALUES ('gaozhonghu', '94mmsJHT+qW+9YXPyPpuZA==', 'GUq05llSeGQl7dkhmv2K/Q==', '2018-10-17 10:35:04');
INSERT INTO `persistent_logins` VALUES ('test_manager', '958OlsRqIhanQUzG7TLcDA==', 'FkqDAFWkIcVm4Yw5mV3sBg==', '2018-10-17 06:00:59');
INSERT INTO `persistent_logins` VALUES ('aichunling', '9h+ZgmQNDusCd0OENzuHJg==', 'ihvbdKKyw7bKpxwE3EOnJg==', '2018-10-15 06:30:41');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', '9x2XmvOOJOgbPjLntlrO3g==', '8Bva5M3X9lKhIwOyY+4S7Q==', '2018-10-19 02:00:13');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', '9Z1mcwDQD9ha0LZli/wNjw==', 'Du55UCiaPGXjg+zcjPTlQQ==', '2018-10-16 13:17:59');
INSERT INTO `persistent_logins` VALUES ('admin', 'A1ZccVUKTkzTP3wZHkaYCw==', 'DN2vSJrOV+IP262yOcFb9w==', '2018-10-17 05:57:14');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'B8r2Z2KspQgSK6sUcI9NjQ==', 'F6xgkd/RwiNPacqx1kkR1A==', '2018-10-19 04:18:17');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', 'BK2x64CtTxFSnTPRJMluhQ==', '4nQDm56GWISgTRtkT5FTbA==', '2018-10-18 03:45:04');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'BKmY9QpbvIISignOAwXAOA==', 'z55aj0CaIJESDK7k9PFk6w==', '2018-10-18 01:57:43');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'C2XwjhRSLGH5DEI3BxwroA==', 'fjv/1mfEnChZWiCq7nMPtA==', '2018-10-18 07:17:42');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'CeSfQO8iAIvCj58msugQPA==', '3a8ybsna+O11DQCkLgG+pw==', '2018-10-17 06:02:27');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'CVe+FJIxYQlbaVD0wGr27A==', 'HK33UUuxpucOoy8fPzEgVw==', '2018-10-16 21:19:12');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'CzooIBGmHc+1PxkibvhIJw==', 'GeF0Ydr/4XEsbmZPa1yZyw==', '2018-10-17 15:26:30');
INSERT INTO `persistent_logins` VALUES ('admin', 'D9Jwu2NmGXfQc30qo3fXPQ==', 'Nwr6zw5s4dSTL9rjRaozXw==', '2018-10-17 06:02:11');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', 'DHpGUo4lKUbr5zj/65q5aA==', 'nTd8B736RSPGfcsy9JkuXg==', '2018-10-18 03:36:14');
INSERT INTO `persistent_logins` VALUES ('lvyichen', 'DJiFRb4QstuEFsxl8qJlmA==', 'IZfvMU4KY253/tcnaf7Kgg==', '2018-10-16 17:41:11');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'dT3r4gsAuNI1spgE4L7u3w==', 'XFScPDgDtxNQXZ98Olk8QQ==', '2018-10-18 10:59:58');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'e2JEhhdsO1ckzYc1cQYcFA==', 'n1OQsHQNWAToIpu+udb1qg==', '2018-10-18 08:01:46');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'e7stlggAyQdZ1xykPKN9fw==', 'KSVhCYB4NfmWrAJ5hOFFCw==', '2018-10-18 10:09:21');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'eLbPfjP3Sipc5MrIryUoBQ==', '5K9ds0f0yaWPF1yPVimUFg==', '2018-10-17 07:32:48');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'f5lOg8lBNELiVD+iXz0nzg==', 'IzEzIOK0TL/YsmZ2EIzBtg==', '2018-10-18 11:03:45');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'fB2w/jEVQl6tGKHqo9Cs0A==', '2Lr9Xe9B3/5IYSgCB5mNQw==', '2018-10-18 11:06:37');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', 'fkOr/1TqnsHEjiz60TYr9A==', '9DJ9ZNCIJ6LgbOpL0A2Ybw==', '2018-10-19 02:03:08');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'fvWjDYIHwYMZIkVTa3lBhA==', '7wR868JaqWImJYtJXC9VdQ==', '2018-10-19 02:56:47');
INSERT INTO `persistent_logins` VALUES ('lvyichen', 'G06fbcoWcG+2ssZCCycLmA==', 'z1MOVLegvNAWbXB7VugnWQ==', '2018-10-17 03:06:53');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'gbv4cKSwA28tR6Jz96vXeA==', '10uEfJcO/25ONJj+4rV2Qw==', '2018-10-17 07:36:38');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'Gxkw6PwYl0URyho9n9xJ/Q==', 'REzfEKnsWpvCTtZ4QqhMUQ==', '2018-10-18 08:02:25');
INSERT INTO `persistent_logins` VALUES ('hudongli', 'hcynYdiCcZvKShV2QshEUA==', 'h3rP0vENvZIdPXTj6j48eQ==', '2018-10-15 17:48:14');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'hqhYtV4mIVVfyK8cl2vlfg==', 'QmiPYpcIDqAalUrxt/WSkA==', '2018-10-18 01:52:46');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'HR2w9qICkI/VYQbYSwArAw==', 'SGddW4+AsaNtuexEdzP5QA==', '2018-10-17 09:14:07');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'HSAkzPkXGs9sU7ONwh56NQ==', 'JIarcYcBX6NX3YYyvdS62w==', '2018-10-19 02:10:20');
INSERT INTO `persistent_logins` VALUES ('liyanhe', 'i8MgFiCP8+R99QXU+hg9qQ==', 'XL1WOFyR7sSf7bvBbwqeaA==', '2018-10-15 17:58:21');
INSERT INTO `persistent_logins` VALUES ('admin', 'IaLIielo5JMdb+SvnBHq7w==', 'mDdS7yBoIKLqgyzhUsnAUQ==', '2018-10-17 06:03:55');
INSERT INTO `persistent_logins` VALUES ('admin', 'ITyIpDahguuUDCygaxtD4Q==', 'XktBsOb4VgmuXXcq/zaBgQ==', '2018-10-17 06:08:46');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'j0EulrILSOxwTzO8BrX2pw==', 'myMdO2bEGYCOiRq1J+OQ/A==', '2018-10-18 08:37:06');
INSERT INTO `persistent_logins` VALUES ('lvyichen', 'jdvdYKMe+wXTraR+RKbQUg==', 't8qnYjXNuoMCtGq5j50S1g==', '2018-10-19 02:55:03');
INSERT INTO `persistent_logins` VALUES ('liyanhe', 'jh/dmZlKtZfkH2pWNCrYTg==', 'jZakCxjosJQY4vN6oqwbaw==', '2018-10-16 11:04:41');
INSERT INTO `persistent_logins` VALUES ('lvyichen', 'JKFeUJfSNYuwABk2PKgIEg==', 'GuwBFjMSbSP/Ie864nLfQA==', '2018-10-19 02:17:05');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'josWhKd5BoEQpYVza9HiCQ==', 'tnm/P60jrE5YEcZQJwD08g==', '2018-10-17 06:23:50');
INSERT INTO `persistent_logins` VALUES ('admin', 'jzcGls0si0QIXyplHdjsjg==', 'nntMtfmpa9+eHFOH0biWaQ==', '2018-10-17 02:02:07');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'kI99+fIUaLPq3NFdA1Hu0Q==', 'VG3lUT6+63uhI7smg5YEnw==', '2018-10-19 03:21:51');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'KmC5qIsWOB3wGcIE6o8W2Q==', 'hFkjfMebC+QEopYt9Lrw+g==', '2018-10-17 06:59:23');
INSERT INTO `persistent_logins` VALUES ('xujingxian', 'KuJ00bHTp3wNW1BS36P4fw==', 'ZabwXaNOXVFtX/RfkqPhoA==', '2018-10-17 11:01:55');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'LLfwdza3aOqcq9IbTB2rSw==', 'V9DA3MNNtDmZ0MzZMKQjZA==', '2018-10-17 06:54:49');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'lofaz9Fr2ZDN7TNI46/ZhA==', 'nuYI1KVUUS2Z75zUNezyKQ==', '2018-10-18 11:07:52');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'LQJ3DjjAiMwcM2kMV1cukA==', 'Qj0h8b7ERd2NeLf6uvB+tg==', '2018-10-17 08:54:57');
INSERT INTO `persistent_logins` VALUES ('liyanhe', 'MoWr1Va0JsUXmX0cO2skGg==', 'XPnnnMmvAy6ZFB+2J0q/+A==', '2018-10-15 17:59:35');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', 'mSk+0ZDaFgIUuPstsKWMlQ==', 'AQmJDXMMm5rfeG+HrJ67hw==', '2018-10-17 08:12:21');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'MtVCWVkXc3hxDcqwemhiBQ==', '3if9nWF5OlOf1UESdEALkg==', '2018-10-18 08:47:15');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'NBtPiSjVBMGvIAAKo+SkWA==', '4t7WtbdISdk8kqkNcUly/g==', '2018-10-17 09:28:03');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'ngOunzSurKXKU7kitCMjfw==', 'SSaG689edQvDGZvVMrp+5w==', '2018-10-18 02:54:55');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'NsUWqNfuE4XYpp8s0P2I6A==', '1Xv90r/l7xv51lEJWsrwVQ==', '2018-10-18 10:05:37');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'NVZgkiO60fXlJpkflZu3og==', 'XhgvsF1wi4Bls5Yp2Bzkjw==', '2018-10-17 05:58:29');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'ov7NznYtvEtGEhG8pUxXNg==', 'Ko1lOuKvFy3E13pyqaTuAg==', '2018-10-18 11:15:11');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'p9J6RE+weL6QRcpIpk1eRQ==', '6tlRen68nD0ounfd5A9lQA==', '2018-10-18 07:17:16');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'QCl0A/5fF91VjjTpz+EbGw==', 'p8JL5RJXgnv6/PwaAN778w==', '2018-10-19 02:16:54');
INSERT INTO `persistent_logins` VALUES ('lvyichen', 'RRnBA/ywX9inKRqufDAGDA==', 'lbcUVREo2v1AVdK9249iJg==', '2018-10-19 02:01:45');
INSERT INTO `persistent_logins` VALUES ('admin', 'rwdkdhV8/bxKm+DJPVB0LQ==', 'CDrIkAalbeiKwzFrKWgCDg==', '2018-10-18 05:19:24');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'SbrXcD13DJ3HMuhqYeHwaQ==', '7CSiBfDjiKFQ/YaJaQauHw==', '2018-10-17 07:35:56');
INSERT INTO `persistent_logins` VALUES ('wangzhihao', 'SowM7NwEzzjsDIHTIZ1qEA==', 'TLJOz36bO8O9CMoyZczXnA==', '2018-10-15 20:56:51');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'SXIMSCHfGqe2H3yDMnOisA==', 'VmTwpQb/Yxu3NOrNt9YTnA==', '2018-10-18 02:15:30');
INSERT INTO `persistent_logins` VALUES ('linyuanhong', 'tHuzIUcqdA3JvU+DmrX3Hg==', '4FWO4LPaASJ8kiXPIG4YkQ==', '2018-10-16 16:45:14');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'U50e/P5YnM5iyWNIUA1r7Q==', '2Kz4MDzH67r/ayPrrFmAlQ==', '2018-10-17 07:00:51');
INSERT INTO `persistent_logins` VALUES ('gaozhonghu', 'usj7Kf8ujr4t1ugyIJtDXA==', 'wuN8WvsRXlquC214lQEX5A==', '2018-10-15 17:53:50');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'uVb/gi662I3u/5WsrqADgA==', '9LQOBNv1MH8X9OkMXFtE6g==', '2018-10-15 15:33:10');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'V3dL8eQWLSBKOYx0FQdn/Q==', 'iD2jTzcTruNoXwTIJkLPPw==', '2018-10-17 06:24:00');
INSERT INTO `persistent_logins` VALUES ('zuochangming', 'vamFqbSvQ++JWYy2KUfZcw==', 'VSGQ9V3nhkuAz/xESUenLg==', '2018-10-16 10:13:45');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'VcFlXzS9BGIB6r2m9Ktj3A==', 'QNFq0ZZe1xOQ6KBrLYUafw==', '2018-10-17 10:38:48');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'vLY+Ly8a3dGv84wWWc1JoA==', '6HEqkNhLVgYM5QaAGyW8vQ==', '2018-10-16 13:24:02');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'W1JI4mzOS9WmsCn98vlp4w==', 'ahzC27jbqVfgZoy3PcTF6Q==', '2018-10-18 09:53:21');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'w3m7kSbOiG77s/Pec64Nnw==', 'MEJjLcdWiB6r5cIRFnsxyw==', '2018-10-17 08:46:02');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'wbnWd3hrSJ6m/zc9e1oflQ==', 'QXH31BejcQ55Lk6TNkh3fg==', '2018-10-19 06:28:58');
INSERT INTO `persistent_logins` VALUES ('fanjiamin', 'WnuqdARpI9fnsgfT0+ft2w==', 'IRkFL4m8vhbpubKODLqQuw==', '2018-10-16 13:51:39');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'XBd8Ywt9PdEEH8g7Y8iuzg==', '7X2ztC0xkvRGgvVbOH6w5w==', '2018-10-17 06:40:19');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'xJD781Sq10mV5RdYV/in9g==', 'EmxPzI4p0ctOiyTGfuKYiw==', '2018-10-19 06:03:51');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'XLD12Vyyhg83yJfIcO1tpg==', 'AZOYcc3vogVCxu6iNQ6oGw==', '2018-10-18 06:55:59');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'xTHLYfe6rCYyvI1IgE0ZYA==', 'y09TgnDEWc30eMnApbuKUw==', '2018-10-19 02:47:12');
INSERT INTO `persistent_logins` VALUES ('test_pm', 'Ym1EZHQsY3p3dd1GzKr7fw==', 'lMSL3JgOvxkH8G240inzbQ==', '2018-10-18 11:17:09');
INSERT INTO `persistent_logins` VALUES ('test_manager', 'ZBRR6K6DTZqhsXvQ85pNkg==', '5mZB1zP/I/iavWVVPzbl8A==', '2018-10-17 10:33:18');
INSERT INTO `persistent_logins` VALUES ('liyanhe', 'zfE8xYbgNtuE8/Bl3yQ1xA==', 'kI0IBlSth6fCKa0+uPERSA==', '2018-10-15 17:57:00');
INSERT INTO `persistent_logins` VALUES ('admin', 'zgOQa7Tv1eSWuJ8uxj+saA==', '0EjAou02PbWwv45FdCbAAw==', '2018-10-18 01:10:29');

-- ----------------------------
-- Table structure for physical_map
-- ----------------------------
DROP TABLE IF EXISTS `physical_map`;
CREATE TABLE `physical_map`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物理图表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传路径',
  `upload_time` timestamp(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `physical_map_project_fk`(`project_id`) USING BTREE,
  CONSTRAINT `physical_map_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '立项表PK',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `serial` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编号',
  `from_old_project` tinyint(1) NULL DEFAULT NULL COMMENT '是否旧项目新需求',
  `old_project_serial` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧项目编号',
  `crm_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'CRM编号',
  `type` int(1) NULL DEFAULT NULL COMMENT '项目类型(合同/pk试点/试点转合同)',
  `level` int(1) NULL DEFAULT NULL COMMENT '项目级别(P0/P1/P2/P3)',
  `amount` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成单金额',
  `sale_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
  `solution_manager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '解决方案经理',
  `pre_sale` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售前工程师',
  `first_party` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '甲方',
  `partners` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合作伙伴/集成商名称',
  `friends` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '友商',
  `status` int(1) NULL DEFAULT NULL COMMENT '项目状态(立项中、进行中、已完成等)',
  `last_status` int(1) NULL DEFAULT NULL COMMENT '项目结项前状态',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `doc_file_id` int(11) NULL DEFAULT NULL COMMENT '立项报告id',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `background` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目背景',
  `attachment` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加说明',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_create_user_id_fk`(`create_user_id`) USING BTREE,
  CONSTRAINT `project_create_user_id_fk` FOREIGN KEY (`create_user_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 302 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_conclusion
-- ----------------------------
DROP TABLE IF EXISTS `project_conclusion`;
CREATE TABLE `project_conclusion`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) NULL DEFAULT NULL,
  `target` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目目标',
  `cycle` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目周期',
  `identity` int(1) NULL DEFAULT NULL COMMENT '身份 1:代表我司 2:代表合作伙伴 3:代表代理商',
  `plan` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方案',
  `phase_conclusion` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶段性结论',
  `plan_design_cycle` int(10) NULL DEFAULT NULL COMMENT '方案设计周期',
  `plan_design_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方案设计说明',
  `customization_development_cycle` int(10) NULL DEFAULT NULL COMMENT '定制化开发周期',
  `customization_development_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定制化开发说明',
  `pre_deliver_cycle` int(10) NULL DEFAULT NULL COMMENT '预交付周期',
  `pre_deliver_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预交付说明',
  `deliver_cycle` int(10) NULL DEFAULT NULL COMMENT '交付周期',
  `deliver_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交付说明',
  `maintenance_cycle` int(10) NULL DEFAULT NULL COMMENT '维护周期',
  `maintenance_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护说明',
  `acceptance_cycle` int(10) NULL DEFAULT NULL COMMENT '验收周期',
  `acceptance_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验收说明',
  `after_sale_cycle` int(10) NULL DEFAULT NULL COMMENT '售后周期',
  `after_sale_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售后说明',
  `fault_num` int(10) NULL DEFAULT NULL COMMENT '故障个数',
  `fault_num_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '故障说明',
  `implement_num` int(10) NULL DEFAULT NULL COMMENT '实施次数',
  `implement_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实施说明',
  `total` int(10) NULL DEFAULT NULL COMMENT '总计',
  `total_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总计说明',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `action_event` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要动作及事件',
  `our_strengths_weaknesses` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '我司优劣势',
  `friends_strengths_weaknesses` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '友商优劣势',
  `legacy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '遗留问题',
  `experience_summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经验总结',
  `improvement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '改进建议',
  `status` int(1) NULL DEFAULT NULL COMMENT '1.未处理 2.待评审 3.通过 4.打回',
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `update_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_conclusion_id_index`(`id`) USING BTREE,
  INDEX `project_conclusion_project_id_index`(`project_id`) USING BTREE,
  CONSTRAINT `project_conclusion_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_plan
-- ----------------------------
DROP TABLE IF EXISTS `project_plan`;
CREATE TABLE `project_plan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '每周看板PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '立项id',
  `project_progress` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '整体进展',
  `risk_and_help` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险&求助',
  `project_status` int(1) NULL DEFAULT NULL COMMENT '项目状态',
  `report_cycle` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汇报周期',
  `week_progress` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本周进展',
  `next_week_plan` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下周计划',
  `attachment` int(11) NULL DEFAULT NULL COMMENT 'file表id',
  `start_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本周起始日期时间戳',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `stage_id` int(11) NULL DEFAULT NULL COMMENT '周报时间断id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_plan_project_id_fk`(`project_id`) USING BTREE,
  INDEX `project_plan_stage_id_fk`(`stage_id`) USING BTREE,
  CONSTRAINT `project_plan_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `project_plan_stage_id_fk` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_related_person
-- ----------------------------
DROP TABLE IF EXISTS `project_related_person`;
CREATE TABLE `project_related_person`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目干系人PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '立项id',
  `type` int(1) NULL DEFAULT NULL COMMENT '项目组成员/接口单位',
  `company_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口单位名称',
  `role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户',
  `remark1` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注1',
  `remark2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注2',
  `remark3` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注3',
  `remark4` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注4',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `person_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `person_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_stage
-- ----------------------------
DROP TABLE IF EXISTS `project_stage`;
CREATE TABLE `project_stage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '阶段表PK',
  `step` int(2) NULL DEFAULT NULL COMMENT '阶段数',
  `target` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶段目标',
  `delivery_date` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交付日期',
  `business_system_id` int(11) NULL DEFAULT NULL COMMENT '业务系统id',
  `platform_id` int(11) NULL DEFAULT NULL COMMENT '平台id',
  `stage_scale` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶段规模',
  `expected_scale` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预期规模',
  `product_require` int(1) NULL DEFAULT NULL COMMENT '产品要求(1：有  0：无)',
  `customization` int(1) NULL DEFAULT NULL COMMENT '定制化需求(1：有  0：无)',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stage_platform_id_fk`(`platform_id`) USING BTREE,
  INDEX `stage_business_system_id_fk`(`business_system_id`) USING BTREE,
  INDEX `project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stage_business_system_id_fk` FOREIGN KEY (`business_system_id`) REFERENCES `business_system` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stage_platfrom_id_fk` FOREIGN KEY (`platform_id`) REFERENCES `business_system_platform` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `icon` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `seq` int(10) NULL DEFAULT NULL,
  `display` tinyint(1) NULL DEFAULT NULL,
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `resource_parent_id`(`parent`) USING BTREE,
  INDEX `code`(`code`) USING BTREE,
  CONSTRAINT `resource_parent_code_fk` FOREIGN KEY (`parent`) REFERENCES `resource` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, '账号管理', 'account_management_node', 'NODE', '', NULL, NULL, 1, NULL, NULL);
INSERT INTO `resource` VALUES (2, '项目列表', 'project_list_node', 'NODE', '', NULL, NULL, 2, NULL, NULL);
INSERT INTO `resource` VALUES (3, '消息', 'message_node', 'NODE', '', NULL, NULL, 3, NULL, NULL);
INSERT INTO `resource` VALUES (4, '编辑项目', 'edit_project_node', 'NODE', '', NULL, NULL, 4, NULL, NULL);
INSERT INTO `resource` VALUES (5, '立项', 'project_establishment_node', 'NODE', '', 'edit_project_node', NULL, 1, NULL, NULL);
INSERT INTO `resource` VALUES (6, '项目计划', 'project_plan_node', 'NODE', '', 'edit_project_node', NULL, 2, NULL, NULL);
INSERT INTO `resource` VALUES (7, '项目验收', 'project_acceptance_node', 'NODE', '', 'edit_project_node', NULL, 3, NULL, NULL);
INSERT INTO `resource` VALUES (8, '项目结项', 'project_conclusion_node', 'NODE', '', 'edit_project_node', NULL, 4, NULL, NULL);
INSERT INTO `resource` VALUES (9, '数据图表', 'project_chart_node', 'NODE', '', NULL, NULL, 5, NULL, NULL);
INSERT INTO `resource` VALUES (10, '看板', 'project_chart_board_node', 'NODE', '', 'project_chart_node', NULL, 1, NULL, NULL);
INSERT INTO `resource` VALUES (11, '活跃项目', 'project_chart_active_node', 'NODE', '', 'project_chart_node', NULL, 2, NULL, NULL);
INSERT INTO `resource` VALUES (12, '结项项目', 'project_chart_conclusion_node', 'NODE', '', 'project_chart_node', NULL, 3, NULL, NULL);
INSERT INTO `resource` VALUES (13, '权限资源', 'resource_node', 'NODE', '', NULL, NULL, 6, NULL, NULL);
INSERT INTO `resource` VALUES (14, '获取用户列表', 'get_account_list', 'URL', '/account', 'account_management_node', NULL, 3, NULL, 'GET');
INSERT INTO `resource` VALUES (15, '新增用户', 'save_account', 'URL', '/account', 'account_management_node', NULL, 1, NULL, 'POST');
INSERT INTO `resource` VALUES (16, '更新用户', 'update_account', 'URL', '/account', 'account_management_node', NULL, 2, NULL, 'PUT');
INSERT INTO `resource` VALUES (17, '删除账号', 'delete_account', 'URL', '/account/{id}', 'project_acceptance_node', NULL, 5, NULL, 'DELETE');
INSERT INTO `resource` VALUES (18, '获取用户信息', 'get_account', 'URL', '/account/{id}', 'account_management_node', NULL, 4, NULL, 'GET');
INSERT INTO `resource` VALUES (19, '获取PM列表', 'get_pm_account_list', 'URL', '/account/pm', 'account_management_node', NULL, 11, NULL, 'GET');
INSERT INTO `resource` VALUES (20, '获取实施清单列表', 'get_apply_list', 'URL', '/applyList/{projectId}', 'project_acceptance_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (21, '新增实施清单', 'add_apply_list', 'URL', '/applyList', 'project_acceptance_node', NULL, 3, NULL, 'POST');
INSERT INTO `resource` VALUES (22, '删除实施清单信息', 'delete_apply_list', 'URL', '/applyList/{id}', 'project_acceptance_node', NULL, 4, NULL, 'DELETE');
INSERT INTO `resource` VALUES (23, '导出项目验收清单列表', 'export_apply_list', 'URL', '/applyList/export/{projectId}', 'project_acceptance_node', NULL, 2, NULL, 'GET');
INSERT INTO `resource` VALUES (24, '获取区域列表', 'get_areas', 'URL', '/area', 'account_management_node', NULL, 6, NULL, 'GET');
INSERT INTO `resource` VALUES (25, '更新区域', 'update_area', 'URL', '/area', 'account_management_node', NULL, 9, NULL, 'PUT');
INSERT INTO `resource` VALUES (26, '新增区域', 'add_area', 'URL', '/area', 'account_management_node', NULL, 7, NULL, 'POST');
INSERT INTO `resource` VALUES (27, '删除区域', 'delete_area', 'URL', '/area/{id}', 'account_management_node', NULL, 8, NULL, 'DELETE');
INSERT INTO `resource` VALUES (28, '删除工时', 'delete_work_time', 'URL', '/costStatistics/workTime/{id}', 'project_plan_node', NULL, 7, NULL, 'DELETE');
INSERT INTO `resource` VALUES (29, '删除出差', 'delete_business_trip', 'URL', '/costStatistics/trip/{id}', 'project_plan_node', NULL, 8, NULL, 'DELETE');
INSERT INTO `resource` VALUES (30, '删除设备', 'delete_equipment', 'URL', '/costStatistics/equipment/{id}', 'project_plan_node', NULL, 9, NULL, 'DELETE');
INSERT INTO `resource` VALUES (31, '提交成本统计数据', 'submit_cost_statistics_data', 'URL', '/costStatistics', 'project_plan_node', NULL, 6, NULL, 'POST');
INSERT INTO `resource` VALUES (32, '成本统计数据初始化', 'init_cost_statistics_data', 'URL', '/costStatistics', 'project_plan_node', NULL, 5, NULL, 'GET');
INSERT INTO `resource` VALUES (33, '获取交付物列表', 'get_deliver_list', 'URL', '/deliverList/{projectId}', 'project_acceptance_node', NULL, 17, NULL, 'GET');
INSERT INTO `resource` VALUES (34, '新增交付物', 'add_deliver_list', 'URL', '/deliverList', 'project_acceptance_node', NULL, 18, NULL, 'POST');
INSERT INTO `resource` VALUES (35, '删除交付物信息', 'delete_deliver_list', 'URL', '/deliverList/{id}', 'project_acceptance_node', NULL, 19, NULL, 'DELETE');
INSERT INTO `resource` VALUES (36, '获取未处理消息', 'get_untreated_message', 'URL', '/message', 'message_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (37, '处理消息', 'set_message_handle', 'URL', '/message/{id}', 'message_node', NULL, 2, NULL, 'GET');
INSERT INTO `resource` VALUES (38, '上传物理图的文件', 'import_physical_map', 'URL', '/physicalMap/doc', 'project_acceptance_node', NULL, 15, NULL, 'POST');
INSERT INTO `resource` VALUES (39, '删除物理图', 'delete_physical_map', 'URL', '/physicalMap/{id}', 'project_acceptance_node', NULL, 21, NULL, 'DELETE');
INSERT INTO `resource` VALUES (40, '获取物理图', 'get_physical_map', 'URL', '/physicalMap/{projectId}', 'project_acceptance_node', NULL, 20, NULL, 'GET');
INSERT INTO `resource` VALUES (41, '项目结项数据初始化', 'init_project_conclusion_data', 'URL', '/projectConclusion', 'project_conclusion_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (42, '结项导出', 'export_project_conclusion', 'URL', '/projectConclusion/{id}', 'project_conclusion_node', NULL, 5, NULL, 'GET');
INSERT INTO `resource` VALUES (43, '提交项目结项数据', 'submit_project_conclusion_data', 'URL', '/projectConclusion', 'project_conclusion_node', NULL, 2, NULL, 'POST');
INSERT INTO `resource` VALUES (44, '发起结项', 'launch_conclusion', 'URL', '/launchConclusion/{id}', 'project_conclusion_node', NULL, 3, NULL, 'GET');
INSERT INTO `resource` VALUES (45, '审批结项', 'approval_conclusion', 'URL', '/approvalConclusion', 'project_conclusion_node', NULL, 4, NULL, 'POST');
INSERT INTO `resource` VALUES (46, '立项导出', 'export_project_establishment', 'URL', '/project/export/{id}', 'project_establishment_node', NULL, 5, NULL, 'GET');
INSERT INTO `resource` VALUES (47, '获取所有过滤条件', 'get_filter', 'URL', '/project/filter', 'project_list_node', NULL, 2, NULL, 'GET');
INSERT INTO `resource` VALUES (48, '获取项目列表', 'get_projects', 'URL', '/project/list', 'project_list_node', NULL, 3, NULL, 'POST');
INSERT INTO `resource` VALUES (49, '获取上次导入的项目信息', 'get_init_project', 'URL', '/project/init', 'project_establishment_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (50, '结项项目激活', 'active_project', 'URL', '/project/active/{id}', 'project_list_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (51, '保存项目信息', 'project_cache', 'URL', '/project/cache', 'project_establishment_node', NULL, 6, NULL, 'POST');
INSERT INTO `resource` VALUES (52, '获取当前id项目明细信息', 'get_project', 'URL', '/project/{id}', 'project_establishment_node', NULL, 7, NULL, 'GET');
INSERT INTO `resource` VALUES (53, '创建立项', 'create_project', 'URL', '/project', 'project_establishment_node', NULL, 3, NULL, 'POST');
INSERT INTO `resource` VALUES (54, '上传立项文件', 'import_project', 'URL', '/project/doc', 'project_establishment_node', NULL, 2, NULL, 'POST');
INSERT INTO `resource` VALUES (55, '更新已创建的项目信息', 'update_project', 'URL', '/project', 'project_establishment_node', NULL, 8, NULL, 'PUT');
INSERT INTO `resource` VALUES (56, '下载项目计划文件', 'export_project_plan', 'URL', '/projectPlan/export/{projectId}', 'project_plan_node', NULL, 4, NULL, 'GET');
INSERT INTO `resource` VALUES (57, '获取项目计划(周报)列表', 'get_project_plan', 'URL', '/projectPlan/{projectId}', 'project_plan_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (58, '新增或更新项目周报', 'add_project_plan', 'URL', '/projectPlan', 'project_plan_node', NULL, 2, NULL, 'POST');
INSERT INTO `resource` VALUES (59, '导入项目计划文件', 'import_project_plan', 'URL', '/projectPlan/doc', 'project_plan_node', NULL, 3, NULL, 'POST');
INSERT INTO `resource` VALUES (60, '删除项目干系人', 'delete_project_related_person', 'URL', '/projectRelatedPerson/{id}', 'project_plan_node', NULL, 15, NULL, 'DELETE');
INSERT INTO `resource` VALUES (61, '获取项目干系人列表', 'get_project_related_person', 'URL', '/projectRelatedPerson/{projectId}', 'project_plan_node', NULL, 13, NULL, 'GET');
INSERT INTO `resource` VALUES (62, '新增项目干系人', 'add_project_related_person', 'URL', '/projectRelatedPerson', 'project_plan_node', NULL, 14, NULL, 'POST');
INSERT INTO `resource` VALUES (63, '获取用户资源权限', 'get_account_resource', 'URL', '/resource/{accountId}', 'resource_node', NULL, 1, NULL, 'GET');
INSERT INTO `resource` VALUES (64, '获取所有资源权限', 'get_all_resource', 'URL', '/resource', 'resource_node', NULL, 2, NULL, 'GET');
INSERT INTO `resource` VALUES (65, '修改角色资源权限', 'update_role_resource', 'URL', '/resource', 'resource_node', NULL, 3, NULL, 'PUT');
INSERT INTO `resource` VALUES (66, '删除风险与问题', 'delete_risk_problem_data', 'URL', '/riskProblem/{id}', 'project_plan_node', NULL, 12, NULL, 'DELETE');
INSERT INTO `resource` VALUES (67, '风险问题数据初始化', 'init_risk_problem_data', 'URL', '/riskProblem', 'project_plan_node', NULL, 10, NULL, 'GET');
INSERT INTO `resource` VALUES (68, '提交风险与问题数据', 'submit_risk_problem_data', 'URL', '/riskProblem', 'project_plan_node', NULL, 11, NULL, 'POST');
INSERT INTO `resource` VALUES (69, '新增角色', 'add_role', 'URL', '/role', 'account_management_node', NULL, 10, NULL, 'POST');
INSERT INTO `resource` VALUES (70, '更新角色', 'update_role', 'URL', '/role', 'account_management_node', NULL, 5, NULL, 'PUT');
INSERT INTO `resource` VALUES (71, '获取工勘清单列表', 'get_survey_list', 'URL', '/surveyList/{projectId}', 'project_acceptance_node', NULL, 6, NULL, 'GET');
INSERT INTO `resource` VALUES (72, '新增工勘清单', 'add_survey_list', 'URL', '/surveyList', 'project_acceptance_node', NULL, 7, NULL, 'POST');
INSERT INTO `resource` VALUES (73, '删除工勘清单信息', 'delete_survey_list', 'URL', '/surveyList/{id}', 'project_acceptance_node', NULL, 8, NULL, 'DELETE');
INSERT INTO `resource` VALUES (74, '上传拓扑图的文件', 'import_topological_graph', 'URL', '/topologicalGraph/doc', 'project_acceptance_node', NULL, 12, NULL, 'POST');
INSERT INTO `resource` VALUES (75, '删除拓扑图', 'delete_topological_graph', 'URL', '/topologicalGraph/{id}', 'project_acceptance_node', NULL, 13, NULL, 'DELETE');
INSERT INTO `resource` VALUES (76, '获取拓扑图', 'get_topological_graph', 'URL', '/topologicalGraph/{projectId}', 'project_acceptance_node', NULL, 14, NULL, 'GET');
INSERT INTO `resource` VALUES (77, '新增软硬件', 'add_ware_list', 'URL', '/wareList', 'project_acceptance_node', NULL, 10, NULL, 'POST');
INSERT INTO `resource` VALUES (78, '删除软硬件信息', 'delete_ware_list', 'URL', '/wareList/{id}', 'project_acceptance_node', NULL, 11, NULL, 'DELETE');
INSERT INTO `resource` VALUES (79, '获取软硬件列表', 'get_ware_list', 'URL', '/wareList/{projectId}', 'project_acceptance_node', NULL, 9, NULL, 'GET');
INSERT INTO `resource` VALUES (86, '获取登陆用户信息', 'get_login_account', 'URL', '/account/info', 'account_management_node', NULL, 12, NULL, 'GET');
INSERT INTO `resource` VALUES (88, '获取角色列表', 'get_roles', 'URL', '/role', 'account_management_node', NULL, 12, NULL, 'GET');
INSERT INTO `resource` VALUES (89, '报表', 'report_node', 'NODE', '', NULL, NULL, 6, NULL, NULL);
INSERT INTO `resource` VALUES (90, '报表首页', 'report_index_node', 'NODE', '', 'report_node', NULL, 1, NULL, NULL);
INSERT INTO `resource` VALUES (91, 'PM看板', 'report_pm_board_node', 'NODE', '', 'report_node', NULL, 2, NULL, NULL);
INSERT INTO `resource` VALUES (92, '管理员看板', 'report_admin_board_node', 'NODE', '', 'report_node', NULL, 3, NULL, NULL);
INSERT INTO `resource` VALUES (93, '项目延期看板', 'report_risk_node', 'NODE', '', 'report_node', NULL, 4, NULL, NULL);
INSERT INTO `resource` VALUES (94, '项目结项信息', 'report_junctions_node', 'NODE', '', 'report_node', NULL, 5, NULL, NULL);
INSERT INTO `resource` VALUES (106, '更新已创建的项目信息', 'update_project_level', 'URL', '/project/level', 'project_establishment_node', NULL, 4, NULL, 'POST');
INSERT INTO `resource` VALUES (107, '获取管理员看板信息', 'get_admin_board_report', 'URL', '/report/adminBoard', 'report_admin_board_node', NULL, 3, NULL, 'GET');
INSERT INTO `resource` VALUES (108, '获取PM看板信息', 'get_pm_board_report', 'URL', '/report/pmBoard', 'report_pm_board_node', NULL, 2, NULL, 'GET');
INSERT INTO `resource` VALUES (109, '获取PM或者管理员首页信息', 'get_index_report', 'URL', '/report/index', 'report_index_node', NULL, 1, NULL, 'POST');
INSERT INTO `resource` VALUES (110, '获取结项看板信息', 'get_junctions_report', 'URL', '/report/junctions', 'report_junctions_node', NULL, 5, NULL, 'POST');
INSERT INTO `resource` VALUES (111, '获取延期项目风险统计数据', 'get_risk_report', 'URL', '/report/risk', 'report_risk_node', NULL, 4, NULL, 'POST');
INSERT INTO `resource` VALUES (112, '删除项目计划详细信息', 'delete_weeklyBoard_list', 'URL', '/weeklyBoard/{id}', 'project_plan_node', NULL, 16, NULL, 'DELETE');
INSERT INTO `resource` VALUES (113, '删除项目阶段信息', 'delete_project_stage', 'URL', '/projectStage/{id}', 'project_establishment_node', NULL, 9, NULL, 'DELETE');
INSERT INTO `resource` VALUES (114, '清除项目缓存信息', 'project_cleanCache', 'URL', '/project/cleanCache', 'project_establishment_node', NULL, 10, NULL, 'DELETE');

-- ----------------------------
-- Table structure for resource_role
-- ----------------------------
DROP TABLE IF EXISTS `resource_role`;
CREATE TABLE `resource_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `resource_id` int(10) NULL DEFAULT NULL,
  `role_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `resource_role_fk_1` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `resource_role_fk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 209 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource_role
-- ----------------------------
INSERT INTO `resource_role` VALUES (1, 14, 1);
INSERT INTO `resource_role` VALUES (2, 15, 1);
INSERT INTO `resource_role` VALUES (3, 16, 1);
INSERT INTO `resource_role` VALUES (4, 17, 1);
INSERT INTO `resource_role` VALUES (5, 18, 1);
INSERT INTO `resource_role` VALUES (6, 19, 1);
INSERT INTO `resource_role` VALUES (7, 20, 1);
INSERT INTO `resource_role` VALUES (8, 21, 1);
INSERT INTO `resource_role` VALUES (9, 22, 1);
INSERT INTO `resource_role` VALUES (10, 23, 1);
INSERT INTO `resource_role` VALUES (11, 24, 1);
INSERT INTO `resource_role` VALUES (12, 25, 1);
INSERT INTO `resource_role` VALUES (13, 26, 1);
INSERT INTO `resource_role` VALUES (14, 27, 1);
INSERT INTO `resource_role` VALUES (15, 28, 1);
INSERT INTO `resource_role` VALUES (16, 29, 1);
INSERT INTO `resource_role` VALUES (17, 30, 1);
INSERT INTO `resource_role` VALUES (18, 31, 1);
INSERT INTO `resource_role` VALUES (19, 32, 1);
INSERT INTO `resource_role` VALUES (20, 33, 1);
INSERT INTO `resource_role` VALUES (21, 34, 1);
INSERT INTO `resource_role` VALUES (22, 35, 1);
INSERT INTO `resource_role` VALUES (23, 36, 1);
INSERT INTO `resource_role` VALUES (24, 37, 1);
INSERT INTO `resource_role` VALUES (25, 38, 1);
INSERT INTO `resource_role` VALUES (26, 39, 1);
INSERT INTO `resource_role` VALUES (27, 40, 1);
INSERT INTO `resource_role` VALUES (28, 41, 1);
INSERT INTO `resource_role` VALUES (29, 42, 1);
INSERT INTO `resource_role` VALUES (30, 43, 1);
INSERT INTO `resource_role` VALUES (31, 44, 1);
INSERT INTO `resource_role` VALUES (32, 45, 1);
INSERT INTO `resource_role` VALUES (33, 46, 1);
INSERT INTO `resource_role` VALUES (34, 47, 1);
INSERT INTO `resource_role` VALUES (35, 48, 1);
INSERT INTO `resource_role` VALUES (36, 49, 1);
INSERT INTO `resource_role` VALUES (37, 50, 1);
INSERT INTO `resource_role` VALUES (38, 51, 1);
INSERT INTO `resource_role` VALUES (39, 52, 1);
INSERT INTO `resource_role` VALUES (40, 53, 1);
INSERT INTO `resource_role` VALUES (41, 54, 1);
INSERT INTO `resource_role` VALUES (42, 55, 1);
INSERT INTO `resource_role` VALUES (43, 56, 1);
INSERT INTO `resource_role` VALUES (44, 57, 1);
INSERT INTO `resource_role` VALUES (45, 58, 1);
INSERT INTO `resource_role` VALUES (46, 59, 1);
INSERT INTO `resource_role` VALUES (47, 60, 1);
INSERT INTO `resource_role` VALUES (48, 61, 1);
INSERT INTO `resource_role` VALUES (49, 62, 1);
INSERT INTO `resource_role` VALUES (50, 63, 1);
INSERT INTO `resource_role` VALUES (51, 64, 1);
INSERT INTO `resource_role` VALUES (52, 65, 1);
INSERT INTO `resource_role` VALUES (53, 66, 1);
INSERT INTO `resource_role` VALUES (54, 67, 1);
INSERT INTO `resource_role` VALUES (55, 68, 1);
INSERT INTO `resource_role` VALUES (56, 69, 1);
INSERT INTO `resource_role` VALUES (57, 70, 1);
INSERT INTO `resource_role` VALUES (58, 71, 1);
INSERT INTO `resource_role` VALUES (59, 72, 1);
INSERT INTO `resource_role` VALUES (60, 73, 1);
INSERT INTO `resource_role` VALUES (61, 74, 1);
INSERT INTO `resource_role` VALUES (62, 75, 1);
INSERT INTO `resource_role` VALUES (63, 76, 1);
INSERT INTO `resource_role` VALUES (64, 77, 1);
INSERT INTO `resource_role` VALUES (65, 78, 1);
INSERT INTO `resource_role` VALUES (66, 79, 1);
INSERT INTO `resource_role` VALUES (67, 18, 3);
INSERT INTO `resource_role` VALUES (68, 20, 3);
INSERT INTO `resource_role` VALUES (69, 21, 3);
INSERT INTO `resource_role` VALUES (70, 22, 3);
INSERT INTO `resource_role` VALUES (71, 23, 3);
INSERT INTO `resource_role` VALUES (72, 24, 3);
INSERT INTO `resource_role` VALUES (73, 28, 3);
INSERT INTO `resource_role` VALUES (74, 29, 3);
INSERT INTO `resource_role` VALUES (75, 30, 3);
INSERT INTO `resource_role` VALUES (76, 31, 3);
INSERT INTO `resource_role` VALUES (77, 32, 3);
INSERT INTO `resource_role` VALUES (78, 33, 3);
INSERT INTO `resource_role` VALUES (79, 34, 3);
INSERT INTO `resource_role` VALUES (80, 35, 3);
INSERT INTO `resource_role` VALUES (81, 36, 3);
INSERT INTO `resource_role` VALUES (82, 37, 3);
INSERT INTO `resource_role` VALUES (83, 38, 3);
INSERT INTO `resource_role` VALUES (84, 39, 3);
INSERT INTO `resource_role` VALUES (85, 40, 3);
INSERT INTO `resource_role` VALUES (86, 41, 3);
INSERT INTO `resource_role` VALUES (87, 42, 3);
INSERT INTO `resource_role` VALUES (88, 43, 3);
INSERT INTO `resource_role` VALUES (89, 44, 3);
INSERT INTO `resource_role` VALUES (90, 46, 3);
INSERT INTO `resource_role` VALUES (91, 47, 3);
INSERT INTO `resource_role` VALUES (92, 48, 3);
INSERT INTO `resource_role` VALUES (93, 49, 3);
INSERT INTO `resource_role` VALUES (94, 51, 3);
INSERT INTO `resource_role` VALUES (95, 52, 3);
INSERT INTO `resource_role` VALUES (96, 53, 3);
INSERT INTO `resource_role` VALUES (97, 54, 3);
INSERT INTO `resource_role` VALUES (98, 55, 3);
INSERT INTO `resource_role` VALUES (99, 56, 3);
INSERT INTO `resource_role` VALUES (100, 57, 3);
INSERT INTO `resource_role` VALUES (101, 58, 3);
INSERT INTO `resource_role` VALUES (102, 59, 3);
INSERT INTO `resource_role` VALUES (103, 60, 3);
INSERT INTO `resource_role` VALUES (104, 61, 3);
INSERT INTO `resource_role` VALUES (105, 62, 3);
INSERT INTO `resource_role` VALUES (106, 63, 3);
INSERT INTO `resource_role` VALUES (107, 66, 3);
INSERT INTO `resource_role` VALUES (108, 67, 3);
INSERT INTO `resource_role` VALUES (109, 68, 3);
INSERT INTO `resource_role` VALUES (110, 71, 3);
INSERT INTO `resource_role` VALUES (111, 72, 3);
INSERT INTO `resource_role` VALUES (112, 73, 3);
INSERT INTO `resource_role` VALUES (113, 74, 3);
INSERT INTO `resource_role` VALUES (114, 75, 3);
INSERT INTO `resource_role` VALUES (115, 76, 3);
INSERT INTO `resource_role` VALUES (116, 77, 3);
INSERT INTO `resource_role` VALUES (117, 78, 3);
INSERT INTO `resource_role` VALUES (118, 79, 3);
INSERT INTO `resource_role` VALUES (119, 14, 2);
INSERT INTO `resource_role` VALUES (120, 18, 2);
INSERT INTO `resource_role` VALUES (121, 19, 2);
INSERT INTO `resource_role` VALUES (122, 20, 2);
INSERT INTO `resource_role` VALUES (123, 23, 2);
INSERT INTO `resource_role` VALUES (124, 24, 2);
INSERT INTO `resource_role` VALUES (125, 32, 2);
INSERT INTO `resource_role` VALUES (126, 33, 2);
INSERT INTO `resource_role` VALUES (127, 36, 2);
INSERT INTO `resource_role` VALUES (128, 37, 2);
INSERT INTO `resource_role` VALUES (129, 40, 2);
INSERT INTO `resource_role` VALUES (130, 41, 2);
INSERT INTO `resource_role` VALUES (131, 42, 2);
INSERT INTO `resource_role` VALUES (132, 45, 2);
INSERT INTO `resource_role` VALUES (133, 46, 2);
INSERT INTO `resource_role` VALUES (134, 47, 2);
INSERT INTO `resource_role` VALUES (135, 48, 2);
INSERT INTO `resource_role` VALUES (136, 50, 2);
INSERT INTO `resource_role` VALUES (137, 52, 2);
INSERT INTO `resource_role` VALUES (138, 56, 2);
INSERT INTO `resource_role` VALUES (139, 57, 2);
INSERT INTO `resource_role` VALUES (140, 61, 2);
INSERT INTO `resource_role` VALUES (141, 63, 2);
INSERT INTO `resource_role` VALUES (142, 67, 2);
INSERT INTO `resource_role` VALUES (143, 71, 2);
INSERT INTO `resource_role` VALUES (144, 76, 2);
INSERT INTO `resource_role` VALUES (145, 79, 2);
INSERT INTO `resource_role` VALUES (154, 86, 1);
INSERT INTO `resource_role` VALUES (155, 86, 3);
INSERT INTO `resource_role` VALUES (156, 86, 2);
INSERT INTO `resource_role` VALUES (157, 87, 1);
INSERT INTO `resource_role` VALUES (158, 87, 3);
INSERT INTO `resource_role` VALUES (159, 88, 1);
INSERT INTO `resource_role` VALUES (160, 93, 1);
INSERT INTO `resource_role` VALUES (161, 94, 1);
INSERT INTO `resource_role` VALUES (162, 95, 1);
INSERT INTO `resource_role` VALUES (163, 94, 3);
INSERT INTO `resource_role` VALUES (164, 95, 3);
INSERT INTO `resource_role` VALUES (165, 93, 2);
INSERT INTO `resource_role` VALUES (166, 94, 2);
INSERT INTO `resource_role` VALUES (167, 98, 1);
INSERT INTO `resource_role` VALUES (168, 98, 3);
INSERT INTO `resource_role` VALUES (169, 98, 2);
INSERT INTO `resource_role` VALUES (170, 98, 3);
INSERT INTO `resource_role` VALUES (171, 98, 2);
INSERT INTO `resource_role` VALUES (172, 98, 3);
INSERT INTO `resource_role` VALUES (173, 98, 2);
INSERT INTO `resource_role` VALUES (177, 102, 1);
INSERT INTO `resource_role` VALUES (178, 102, 3);
INSERT INTO `resource_role` VALUES (179, 102, 2);
INSERT INTO `resource_role` VALUES (180, 106, 1);
INSERT INTO `resource_role` VALUES (181, 107, 1);
INSERT INTO `resource_role` VALUES (182, 108, 1);
INSERT INTO `resource_role` VALUES (183, 109, 1);
INSERT INTO `resource_role` VALUES (184, 110, 1);
INSERT INTO `resource_role` VALUES (185, 111, 1);
INSERT INTO `resource_role` VALUES (186, 112, 1);
INSERT INTO `resource_role` VALUES (187, 113, 1);
INSERT INTO `resource_role` VALUES (188, 106, 3);
INSERT INTO `resource_role` VALUES (189, 113, 3);
INSERT INTO `resource_role` VALUES (190, 112, 3);
INSERT INTO `resource_role` VALUES (191, 108, 3);
INSERT INTO `resource_role` VALUES (192, 109, 3);
INSERT INTO `resource_role` VALUES (193, 111, 3);
INSERT INTO `resource_role` VALUES (194, 110, 3);
INSERT INTO `resource_role` VALUES (195, 107, 2);
INSERT INTO `resource_role` VALUES (196, 109, 2);
INSERT INTO `resource_role` VALUES (197, 111, 2);
INSERT INTO `resource_role` VALUES (198, 110, 2);
INSERT INTO `resource_role` VALUES (199, 109, 3);
INSERT INTO `resource_role` VALUES (200, 109, 2);
INSERT INTO `resource_role` VALUES (201, 109, 3);
INSERT INTO `resource_role` VALUES (202, 109, 2);
INSERT INTO `resource_role` VALUES (203, 114, 1);
INSERT INTO `resource_role` VALUES (204, 114, 3);

-- ----------------------------
-- Table structure for risk_problem
-- ----------------------------
DROP TABLE IF EXISTS `risk_problem`;
CREATE TABLE `risk_problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目风险/问题汇总表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '立项id',
  `risk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险/问题',
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '级别',
  `measure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '具体措施',
  `occur_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发生日期',
  `planed_solve_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划解决日期',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `person_liable` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '责任人',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `risk_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `risk_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表PK',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色code',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '2018-08-01 10:43:48.000000', '2018-08-01 10:43:51.000000', 'ROLE_admin');
INSERT INTO `role` VALUES (2, '管理员', '2018-08-01 10:43:48.000000', '2018-08-01 10:43:51.000000', 'ROLE_manager');
INSERT INTO `role` VALUES (3, '项目经理', '2018-08-01 10:43:48.000000', '2018-08-01 10:43:51.000000', 'ROLE_pm');

-- ----------------------------
-- Table structure for stage
-- ----------------------------
DROP TABLE IF EXISTS `stage`;
CREATE TABLE `stage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目计划id',
  `start_date` date NULL DEFAULT NULL COMMENT '开始时间yyyy-MM-dd',
  `end_date` date NULL DEFAULT NULL COMMENT '本周结束时间yyyy-MM-dd',
  `type` int(1) NULL DEFAULT NULL COMMENT '1为周报2为工时',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stage_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `stage_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for survey_list
-- ----------------------------
DROP TABLE IF EXISTS `survey_list`;
CREATE TABLE `survey_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工勘清单表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `constablewick` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '警区',
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地点',
  `amount` int(11) NULL DEFAULT NULL COMMENT '数量',
  `glare` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位照度-强光',
  `backlight` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位照度-背光',
  `weaklight` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位照度-弱光',
  `passing_rate` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位-过人率',
  `positive_rate` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位-正脸率',
  `poe` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供电POE',
  `dc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '直流电源',
  `longitude` float(10, 5) NULL DEFAULT NULL COMMENT '经度',
  `latitude` float(10, 5) NULL DEFAULT NULL COMMENT '纬度',
  `direction_angle` float(10, 2) NULL DEFAULT NULL COMMENT '方向角度（度）',
  `focal_length` float(10, 2) NULL DEFAULT NULL COMMENT '镜头焦距（毫米）',
  `view_distance` float(10, 2) NULL DEFAULT NULL COMMENT '镜头取景距离（米）',
  `view_width` float(10, 2) NULL DEFAULT NULL COMMENT '镜头取景宽度（米）',
  `height` float(10, 2) NULL DEFAULT NULL COMMENT '架设高度（米）臂装/吊装/立杆',
  `width_height` float(10, 2) NULL DEFAULT NULL COMMENT '横向高度（米）装/吊装/立杆',
  `screen` tinyint(1) NULL DEFAULT NULL COMMENT '是否有遮挡物',
  `anti_collision` tinyint(1) NULL DEFAULT NULL COMMENT '是否防撞',
  `point_check` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位验收（签字署名表示验收）',
  `eye_distance` int(11) NULL DEFAULT NULL COMMENT '眼间距（像素）',
  `focal_graph` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对焦图(通过)',
  `reexamination` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复验（签字署名表示验收）',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topological_graph
-- ----------------------------
DROP TABLE IF EXISTS `topological_graph`;
CREATE TABLE `topological_graph`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '拓扑图表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `upload_time` timestamp(0) NULL DEFAULT NULL COMMENT '上传时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `topological_graph_project_fk`(`project_id`) USING BTREE,
  CONSTRAINT `topological_graph_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ware_list
-- ----------------------------
DROP TABLE IF EXISTS `ware_list`;
CREATE TABLE `ware_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '硬件清单表PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `sn_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN编号',
  `node_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器节点名称',
  `config_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器配置标准代号',
  `size` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器尺寸',
  `hardware_config` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器硬件配置',
  `software_module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件/模块',
  `version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `port` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
  `video_private_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视屏专网ip',
  `police_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公安网ip',
  `account_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器账户/密码',
  `license_expiration` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加密狗到期时间',
  `revised_record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修订记录',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `hardware_list_project_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `hardware_list_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for weekly_board
-- ----------------------------
DROP TABLE IF EXISTS `weekly_board`;
CREATE TABLE `weekly_board`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '看板任务PK',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '每周看板id',
  `stage` int(11) NULL DEFAULT NULL COMMENT '阶段（预定义）',
  `task` int(11) NULL DEFAULT NULL COMMENT '任务项（预定义）',
  `work_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '具体工作',
  `output` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输出物',
  `plan_start_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划开始时间',
  `real_start_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际开始时间',
  `plan_finish_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划完成时间',
  `real_finish_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际完成时间',
  `completion_rate` float(5, 2) NULL DEFAULT NULL COMMENT '完成率',
  `workload` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作量（人名-人天）',
  `person_liable` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '责任人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险、问题及备注',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `weekly_board_id_fk`(`project_id`) USING BTREE,
  CONSTRAINT `kanban_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 278 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for work_time
-- ----------------------------
DROP TABLE IF EXISTS `work_time`;
CREATE TABLE `work_time`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工时表PK',
  `stage_id` int(11) NULL DEFAULT NULL COMMENT '每周看板id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `work_desc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职能',
  `work_hour1` float(4, 2) NULL DEFAULT NULL COMMENT '工时1',
  `work_hour2` float(4, 2) NULL DEFAULT NULL COMMENT '工时2',
  `work_hour3` float(4, 2) NULL DEFAULT NULL COMMENT '工时3',
  `work_hour4` float(4, 2) NULL DEFAULT NULL COMMENT '工时4',
  `work_hour5` float(4, 2) NULL DEFAULT NULL COMMENT '工时5',
  `work_hour6` float(4, 2) NULL DEFAULT NULL COMMENT '工时6',
  `work_hour7` float(4, 2) NULL DEFAULT NULL COMMENT '工时7',
  `create_time` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  `week_total_time` float(6, 0) NULL DEFAULT NULL COMMENT '本周工时汇总',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `work_time_stage_id_index`(`stage_id`) USING BTREE,
  CONSTRAINT `work_time_stage_id_fk` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
