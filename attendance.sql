/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : bsdesign

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 03/09/2025 10:55:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for check_in_records
-- ----------------------------
DROP TABLE IF EXISTS `check_in_records`;
CREATE TABLE `check_in_records`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `task_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '签到任务ID',
  `student_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生ID',
  `check_in_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间',
  `status` enum('success','late','absent','invalid') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '签到状态',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签到位置(经纬度)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_task_student`(`task_id` ASC, `student_id` ASC) USING BTREE,
  INDEX `idx_student_id`(`student_id` ASC) USING BTREE,
  INDEX `idx_check_in_time`(`check_in_time` ASC) USING BTREE,
  CONSTRAINT `fk_record_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_record_task` FOREIGN KEY (`task_id`) REFERENCES `check_in_tasks` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '签到记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of check_in_records
-- ----------------------------
INSERT INTO `check_in_records` VALUES (8, '3', 'U813570619', '2025-05-10 11:30:42', 'success', NULL);
INSERT INTO `check_in_records` VALUES (10, '10', 'U813570619', '2025-06-19 15:30:42', 'success', NULL);
INSERT INTO `check_in_records` VALUES (11, '1', 'U813570619', '2025-06-21 09:30:42', 'success', NULL);

-- ----------------------------
-- Table structure for check_in_tasks
-- ----------------------------
DROP TABLE IF EXISTS `check_in_tasks`;
CREATE TABLE `check_in_tasks`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '签到任务ID',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程ID',
  `teacher_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布教师ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '签到标题',
  `method` enum('GESTURE','PASSWORD','FACE','QR') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '签到方式',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签到密码/手势(人脸识别为空)',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签到位置(经纬度)',
  `allow_distance` int NULL DEFAULT NULL COMMENT '允许签到距离(米)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` enum('pending','active','finished') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id` ASC) USING BTREE,
  INDEX `idx_time`(`start_time` ASC, `end_time` ASC) USING BTREE,
  CONSTRAINT `fk_task_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_task_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '签到任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of check_in_tasks
-- ----------------------------
INSERT INTO `check_in_tasks` VALUES ('1', '1', 'U5002227403', '第3周高数签到', 'QR', '', '2021-10-03 18:21:33', '2021-10-03 20:21:56', '122.199288,29.967633', 500, '2025-05-02 00:22:28', 'pending');
INSERT INTO `check_in_tasks` VALUES ('10', '10', 'U6999638479', '第x周线性代数签到', 'QR', NULL, '2024-06-18 08:40:32', '2024-06-18 10:38:32', '122.199288,29.967633', 500, '2024-06-18 07:40:32', 'pending');
INSERT INTO `check_in_tasks` VALUES ('2', '18', 'U1056300379', '第1周编译原理', 'QR', NULL, '2025-03-03 09:50:19', '2025-03-03 10:26:21', '122.199288,29.967633', 500, '2025-05-08 12:26:51', 'pending');
INSERT INTO `check_in_tasks` VALUES ('3', '1', 'U5002227403', '第4周高数签到', 'FACE', '', '2021-10-11 18:21:33', '2021-10-11 20:21:56', '122.199288,29.967633', 500, '2025-05-10 00:22:28', 'pending');
INSERT INTO `check_in_tasks` VALUES ('4', '16', 'U1056300379', '第6周操作系统签到', 'QR', NULL, '2023-04-19 14:32:21', '2024-04-19 16:32:37', '122.199288,29.967633', 500, '2025-05-08 14:32:57', 'pending');
INSERT INTO `check_in_tasks` VALUES ('5', '11', 'U1056300379', '第2周大学物理签到', 'FACE', NULL, '2024-03-10 08:37:36', '2024-03-10 09:38:26', '122.199288,29.967633', 500, '2025-05-08 14:38:41', 'pending');
INSERT INTO `check_in_tasks` VALUES ('6', '14', 'U2083706442', '第5周计算机网络签到', 'FACE', NULL, '2022-10-26 14:46:05', '2022-10-26 16:46:30', '122.199288,29.967633', 500, '2025-05-08 14:46:42', 'pending');
INSERT INTO `check_in_tasks` VALUES ('7', '13', 'U1056300379', '第11周大学语文签到', 'QR', NULL, '2024-05-14 10:48:09', '2024-05-14 11:25:00', '122.199288,29.967633', 500, '2025-05-08 14:49:40', 'pending');
INSERT INTO `check_in_tasks` VALUES ('8', '9', 'U4588120190', '第8周歌舞喜剧签到', 'QR', NULL, '2025-04-17 13:50:51', '2025-04-17 14:51:14', '122.199288,29.967633', 500, '2025-05-08 14:51:25', 'pending');
INSERT INTO `check_in_tasks` VALUES ('9', '33', 'U1056300379', '第4周C语言签到', 'QR', NULL, '2025-03-28 15:51:57', '2025-03-28 16:10:11', '122.199288,29.967633', 500, '2025-05-08 14:52:23', 'pending');

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级名称',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '院系',
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级',
  `adviser_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班主任ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_adviser`(`adviser_id` ASC) USING BTREE,
  CONSTRAINT `fk_class_adviser` FOREIGN KEY (`adviser_id`) REFERENCES `teachers` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', 'A21计算机3', '信息工程学院', '计算机科学与技术', '4', 'U3009388591', '2021-08-05 14:54:00');
INSERT INTO `classes` VALUES ('2', 'A18物理1', '信息工程学院', '物理', '', 'U6999638479', '2018-08-04 14:54:35');
INSERT INTO `classes` VALUES ('3', 'A20英语1', '外国语学院', '英语', NULL, 'U8135142364', '2020-08-04 09:58:46');
INSERT INTO `classes` VALUES ('4', 'A23数学1', '信息工程学院', '数学', NULL, 'U3009388591', '2023-08-11 08:59:33');
INSERT INTO `classes` VALUES ('5', 'Z22海工1', '船舶与海运学院', '海洋工程与技术', NULL, 'U2083706442', '2024-08-07 14:30:40');
INSERT INTO `classes` VALUES ('6', 'B24历史1', '师范学院', '历史', NULL, 'U1056300379', '2024-08-09 13:01:17');

-- ----------------------------
-- Table structure for course_schedules
-- ----------------------------
DROP TABLE IF EXISTS `course_schedules`;
CREATE TABLE `course_schedules`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '安排ID',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程ID',
  `week_day` tinyint(1) NOT NULL COMMENT '星期几(1-7)',
  `start_week` tinyint NOT NULL COMMENT '开始周',
  `end_week` tinyint NOT NULL COMMENT '结束周',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上课地点',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_schedule_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程安排表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_schedules
-- ----------------------------
INSERT INTO `course_schedules` VALUES ('1', '1', 5, 14, 3, '13:42:56', '15:50:52', '教学楼103');
INSERT INTO `course_schedules` VALUES ('10', '14', 2, 10, 10, '14:31:36', '12:25:02', '教学楼101');
INSERT INTO `course_schedules` VALUES ('100', '18', 6, 16, 6, '14:43:03', '12:07:19', '教学楼124');
INSERT INTO `course_schedules` VALUES ('101', '38', 4, 8, 13, '12:23:13', '16:28:19', '教学楼121');
INSERT INTO `course_schedules` VALUES ('102', '41', 6, 7, 1, '17:20:05', '17:28:30', '教学楼203');
INSERT INTO `course_schedules` VALUES ('103', '37', 6, 16, 8, '15:42:52', '10:11:36', '教学楼301');
INSERT INTO `course_schedules` VALUES ('104', '39', 5, 10, 11, '14:08:11', '15:22:11', '教学楼305');
INSERT INTO `course_schedules` VALUES ('105', '30', 6, 13, 5, '09:34:59', '17:57:45', '教学楼221');
INSERT INTO `course_schedules` VALUES ('106', '28', 3, 10, 11, '10:48:41', '12:39:46', '教学楼113');
INSERT INTO `course_schedules` VALUES ('107', '16', 2, 6, 9, '10:15:50', '17:43:12', '教学楼114');
INSERT INTO `course_schedules` VALUES ('108', '16', 2, 6, 3, '14:37:24', '13:37:43', '教学楼131');
INSERT INTO `course_schedules` VALUES ('109', '8', 4, 10, 1, '11:24:11', '17:05:16', '教学楼310');
INSERT INTO `course_schedules` VALUES ('11', '5', 4, 4, 3, '17:15:13', '11:24:36', '教学楼302');
INSERT INTO `course_schedules` VALUES ('110', '15', 6, 13, 2, '12:33:07', '10:13:14', '教学楼A101');
INSERT INTO `course_schedules` VALUES ('111', '10', 2, 12, 1, '09:37:30', '11:17:46', '教学楼B111');
INSERT INTO `course_schedules` VALUES ('112', '17', 7, 14, 13, '15:15:25', '17:37:46', '教学楼B103');
INSERT INTO `course_schedules` VALUES ('113', '11', 5, 1, 15, '10:10:18', '12:17:36', '教学楼B203');
INSERT INTO `course_schedules` VALUES ('114', '18', 2, 15, 10, '13:25:23', '13:25:14', '教学楼111');
INSERT INTO `course_schedules` VALUES ('115', '5', 4, 1, 16, '16:57:56', '17:55:04', '教学楼122');
INSERT INTO `course_schedules` VALUES ('116', '7', 6, 16, 3, '15:48:02', '10:32:20', '教学楼206');
INSERT INTO `course_schedules` VALUES ('117', '4', 6, 14, 11, '14:36:27', '14:35:05', '教学楼103');
INSERT INTO `course_schedules` VALUES ('118', '30', 3, 3, 4, '15:10:19', '09:40:39', '教学楼A103');
INSERT INTO `course_schedules` VALUES ('119', '33', 3, 2, 8, '09:54:39', '13:10:47', '教学楼A301');
INSERT INTO `course_schedules` VALUES ('12', '39', 5, 9, 4, '17:18:27', '11:04:20', '教学楼B307');
INSERT INTO `course_schedules` VALUES ('120', '21', 5, 5, 11, '10:36:16', '10:42:36', '教学楼B210');
INSERT INTO `course_schedules` VALUES ('121', '25', 2, 8, 7, '17:20:01', '16:40:46', '教学楼A305');
INSERT INTO `course_schedules` VALUES ('122', '28', 3, 15, 2, '09:13:11', '12:20:41', '教学楼103');

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程ID',
  `course_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程代码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '课程描述',
  `credit` decimal(3, 1) NULL DEFAULT NULL COMMENT '学分',
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学期(如:2023-2024-1)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_course_code_semester`(`course_code` ASC, `semester` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES ('1', '425', '高等数学A1', 'This is a class of math', 4.0, '2021-2022-1', '2025-05-02 00:14:50', '2025-05-02 00:14:50');
INSERT INTO `courses` VALUES ('10', '68', '线性代数', 'Sometimes you win, sometimes you learn.', 7.0, '2015-2016-2', '2017-08-28 20:02:47', '2025-05-05 18:05:18');
INSERT INTO `courses` VALUES ('11', '125', '大学物理', 'A man is not old until regrets                    ', 5.0, '2018-2019-1', '2001-12-03 03:06:40', '2025-05-05 18:05:24');
INSERT INTO `courses` VALUES ('12', '71', '大学英语', 'Navicat 15 has added support for                  ', 2.0, '2020-2021-1', '2017-10-16 04:38:29', '2025-05-05 18:05:31');
INSERT INTO `courses` VALUES ('13', '91', '大学语文', 'You must be the change you wish                   ', 6.0, '2021-2022-2', '2004-05-06 23:10:16', '2025-05-05 18:05:36');
INSERT INTO `courses` VALUES ('14', '113', '计算机网络', 'All the Navicat Cloud objects are                 ', 6.0, '2020-2021-2', '2002-02-12 21:49:16', '2025-05-05 18:05:43');
INSERT INTO `courses` VALUES ('15', '104', '计算机组成原理', 'After logged in the Navicat Cloud                 ', 5.0, '2017-2018-1', '2012-10-08 22:40:07', '2025-05-05 18:05:49');
INSERT INTO `courses` VALUES ('16', '22', '操作系统', 'Always keep your eyes open. Keep                  ', 5.0, '2013-2014-1', '2023-05-03 12:35:35', '2025-05-05 18:05:54');
INSERT INTO `courses` VALUES ('17', '101', '数据结构', 'To successfully establish a new                   ', 1.0, '2013-2014-2', '2007-10-07 14:29:48', '2025-05-05 18:06:04');
INSERT INTO `courses` VALUES ('18', '102', '编译原理', 'Sometimes you win, sometimes you learn.', 3.0, '2018-2019-1', '2018-01-10 12:06:11', '2025-05-05 18:06:13');
INSERT INTO `courses` VALUES ('19', '19', '马列', 'The reason why a great man is great               ', 3.0, '2016-2017-2', '2005-10-02 11:14:00', '2025-05-05 18:06:17');
INSERT INTO `courses` VALUES ('2', '11', '毛概', 'The Navigation pane employs tree                  ', 6.0, '2019-2020-2', '2006-12-27 03:18:03', '2025-05-05 18:06:23');
INSERT INTO `courses` VALUES ('20', '11', '思想政治', 'The Navigation pane employs tree                  ', 3.0, '2019-2020-1', '2016-01-31 04:39:43', '2025-05-05 18:06:30');
INSERT INTO `courses` VALUES ('21', '83', '生涯规划', 'Navicat provides a wide range advanced            ', 2.0, '2017-2018-1', '2002-07-27 02:00:29', '2025-05-05 18:06:38');
INSERT INTO `courses` VALUES ('22', '10', '大学体育', 'To successfully establish a new                   ', 2.0, '2017-2018-1', '2005-10-21 08:06:00', '2025-05-05 18:06:39');
INSERT INTO `courses` VALUES ('23', '12', '马原', 'It shdwia noinwfn a down', 1.0, '2017-2018-1', '2025-05-05 18:07:57', '2025-05-31 18:08:02');
INSERT INTO `courses` VALUES ('24', '57', '离散数学', 'To connect to a database or schema,               ', 3.0, '2018-2019-1', '2024-01-09 03:29:08', '2025-05-05 18:08:16');
INSERT INTO `courses` VALUES ('25', '67', '计算机概论', 'Anyone who has ever made anything                 ', 1.0, '2018-2019-1', '2014-08-31 00:22:29', '2025-05-05 18:08:17');
INSERT INTO `courses` VALUES ('26', '11', '3D打印', 'If the plan doesn’t work, change                ', 3.0, '2018-2019-1', '2022-07-17 18:42:31', '2025-05-05 18:08:18');
INSERT INTO `courses` VALUES ('27', '2', '军事理论', 'Navicat provides powerful tools                   ', 8.0, '2018-2019-1', '2010-02-21 16:48:03', '2025-05-05 18:08:19');
INSERT INTO `courses` VALUES ('28', '77', '流体力学', 'You can select any connections,                   ', 4.0, '2018-2019-1', '2016-12-21 09:40:36', '2025-05-05 18:08:20');
INSERT INTO `courses` VALUES ('29', '113', '工程图学', 'To get a secure connection, the                   ', 2.0, '2018-2019-1', '2023-08-09 14:26:31', '2025-05-05 18:08:21');
INSERT INTO `courses` VALUES ('3', '32', '金工实习', 'The Information Pane shows the                    ', 6.0, '2018-2019-1', '2011-03-04 07:50:49', '2025-05-05 18:08:22');
INSERT INTO `courses` VALUES ('30', '93', '商务英语', 'Genius is an infinite capacity for taking pains.', 5.0, '2018-2019-1', '2015-07-04 06:07:58', '2025-05-05 18:08:23');
INSERT INTO `courses` VALUES ('31', '65', '电子信息技术', 'If it scares you, it might be a good thing to try.', 6.0, '2018-2019-1', '2000-01-25 12:30:01', '2025-05-05 18:08:23');
INSERT INTO `courses` VALUES ('32', '26', '面向对象编程', 'Anyone who has never made a mistake               ', 7.0, '2018-2019-1', '2003-08-29 18:18:02', '2025-05-05 18:08:24');
INSERT INTO `courses` VALUES ('33', '23', 'C语言', 'To successfully establish a new                   ', 3.0, '2018-2019-1', '2020-12-02 01:24:07', '2025-05-05 18:08:27');
INSERT INTO `courses` VALUES ('34', '81', 'web系统开发', 'Flexible settings enable you to                   ', 5.0, '2021-2022-2', '2016-07-31 00:24:55', '2025-05-05 18:08:31');
INSERT INTO `courses` VALUES ('35', '6', '防火墙技术', 'Creativity is intelligence having fun.', 5.0, '2021-2022-2', '2020-05-30 10:54:11', '2025-05-05 18:08:30');
INSERT INTO `courses` VALUES ('36', '53', '网络攻防', 'Navicat authorizes you to make                    ', 2.0, '2021-2022-2', '2011-12-17 10:55:07', '2025-05-05 18:08:32');
INSERT INTO `courses` VALUES ('37', '85', '神经网络', 'To connect to a database or schema,               ', 6.0, '2019-2020-2', '2012-12-17 06:09:28', '2025-05-05 18:08:40');
INSERT INTO `courses` VALUES ('38', '102', '唐致远', 'Navicat Cloud provides a cloud                    ', 4.0, '2019-2020-2', '2011-11-17 17:51:08', '2025-05-05 18:08:40');
INSERT INTO `courses` VALUES ('39', '19', '汤宇宁', 'To connect to a database or schema,               ', 1.0, '2021-2022-2', '2006-02-17 06:48:12', '2025-05-05 18:08:36');
INSERT INTO `courses` VALUES ('4', '102', '史詩涵', 'You will succeed because most people are lazy.', 3.0, 'kVpybqW3G0', '2020-08-31 11:13:27', '2015-06-26 03:47:52');
INSERT INTO `courses` VALUES ('40', '31', '常子韬', 'Such sessions are also susceptible                ', 3.0, 'zMhMdGAhzL', '2024-07-09 20:05:41', '2014-05-01 17:13:07');
INSERT INTO `courses` VALUES ('41', '105', '龙致远', 'It is used while your ISPs do not                 ', 3.0, '2NHgKCqZW5', '2011-12-31 04:37:53', '2001-03-20 07:00:49');
INSERT INTO `courses` VALUES ('5', '35', '任宇宁', 'HTTP Tunneling is a method for                    ', 3.0, 'XyQ1RLXsea', '2006-12-02 00:01:56', '2023-07-08 01:49:55');
INSERT INTO `courses` VALUES ('6', '76', '郑震南', 'I will greet this day with love in my heart.', 3.0, 'h7zDXdaKXb', '2008-01-19 13:16:58', '2021-03-01 15:58:26');
INSERT INTO `courses` VALUES ('7', '7', '沈嘉伦', 'If the plan doesn’t work, change                ', 6.0, 'tZYRIwdDxt', '2007-08-06 15:25:05', '2020-02-06 12:49:17');
INSERT INTO `courses` VALUES ('8', '14', '周云熙', 'Navicat Data Modeler is a powerful                ', 6.0, 'LvKCKQCGHY', '2021-12-07 23:56:34', '2019-08-16 00:16:56');
INSERT INTO `courses` VALUES ('9', '101', '歌舞戏剧', 'Flexible settings enable you to                   ', 2.0, 'OBlsARWhtK', '2002-09-14 12:08:40', '2025-05-08 15:02:15');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送者ID',
  `receiver_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收者ID',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联课程ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `type` enum('text','attend','image','file','location') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息类型',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读(1:是,0:否)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender`(`sender_id` ASC) USING BTREE,
  INDEX `idx_receiver`(`receiver_id` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_message_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of messages
-- ----------------------------
INSERT INTO `messages` VALUES (1, 'U3009388591', 'U813570619', '1', '欢迎大家选择这门课程', 'text', 1, '2021-11-19 06:20:10');
INSERT INTO `messages` VALUES (2, 'U8210279826', 'U8210279826', '2', 'To successfully establish a new connection to local/remote server - no matter via SSL or SSH, set the database login information in the General tab. Always keep your eyes open. Keep watching. Because whatever you see can inspire you. To successfully establish a new connection to local/remote server - no matter via SSL or SSH, set the database login information in the General tab.', 'attend', 1, '2015-03-06 00:25:23');
INSERT INTO `messages` VALUES (3, 'U0828577737', 'U0828577737', '28', 'It collects process metrics such as CPU load, RAM usage, and a variety of other resources over SSH/SNMP.', 'image', 0, '2022-09-14 09:43:01');
INSERT INTO `messages` VALUES (4, 'U7810384928', 'U7810384928', '32', 'To successfully establish a new connection to local/remote server - no matter via SSL or SSH, set the database login information in the General tab. Genius is an infinite capacity for taking pains. It collects process metrics such as CPU load, RAM usage, and a variety of other resources over SSH/SNMP. The past has no power over the present moment.', 'file', 0, '2021-08-23 14:06:18');
INSERT INTO `messages` VALUES (5, 'U6276227474', 'U6276227474', '16', 'If the Show objects under schema in navigation pane option is checked at the Preferences window, all database objects are also displayed in the pane. In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram buttons to change the object view.', 'location', 1, '2021-08-23 17:20:01');
INSERT INTO `messages` VALUES (6, 'U5344387079', 'U5344387079', '40', 'Champions keep playing until they get it right. In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram buttons to change the object view. Creativity is intelligence having fun. In the middle of winter I at last discovered that there was in me an invincible summer. The reason why a great man is great is that he resolves to be a great man.', 'image', 1, '2015-03-04 04:34:09');
INSERT INTO `messages` VALUES (7, 'U1056300379', 'U1056300379', '27', 'I may not have gone where I intended to go, but I think I have ended up where I needed to be. Such sessions are also susceptible to session hijacking, where a malicious user takes over your session once you have authenticated.', 'location', 0, '2021-01-23 17:48:13');
INSERT INTO `messages` VALUES (8, 'U3009388591', 'U813570619', '1', '这还是测试消息', 'text', 0, '2021-12-03 13:24:01');
INSERT INTO `messages` VALUES (9, 'U9866087380', 'U9866087380', '40', 'Navicat Data Modeler is a powerful and cost-effective database design tool which helps you build high-quality conceptual, logical and physical data models. Champions keep playing until they get it right. A man’s best friends are his ten fingers. Anyone who has never made a mistake has never tried anything new.', 'location', 0, '2016-02-08 19:50:11');
INSERT INTO `messages` VALUES (10, 'U3009388591', 'U813570619', '1', 'https://cdn.pixabay.com/photo/2021/04/24/18/07/road-6204694_1280.jpg', 'image', 1, '2021-11-20 20:57:47');
INSERT INTO `messages` VALUES (11, 'U3009388591', 'U813570619', '1', '1', 'attend', 1, '2021-11-28 13:23:20');
INSERT INTO `messages` VALUES (12, 'U8040067632', 'U813570619', '11', 'https://cdn.pixabay.com/photo/2023/01/22/19/06/swan-7737169_1280.jpg', 'image', 1, '2022-04-05 08:43:28');
INSERT INTO `messages` VALUES (13, 'U6999638479', 'U813570619', '10', '这是测试消息', 'text', 0, '2024-06-01 08:44:08');
INSERT INTO `messages` VALUES (14, 'U6337946643', 'U6337946643', '20', 'https://cdn.pixabay.com/photo/2025/04/24/04/58/flowers-9554400_1280.jpg', 'image', 1, '2021-12-31 11:15:53');
INSERT INTO `messages` VALUES (15, 'U4855931633', 'U4855931633', '39', 'Anyone who has never made a mistake has never tried anything new. To successfully establish a new connection to local/remote server - no matter via SSL, SSH or HTTP, set the database login information in the General tab.', 'attend', 0, '2022-10-31 09:51:41');
INSERT INTO `messages` VALUES (16, 'U6999638479', 'U813570619', '10', '122.199288,29.967633', 'location', 0, '2024-06-02 09:36:04');
INSERT INTO `messages` VALUES (17, '4', 'U0586622100', '23', 'https://cdn.pixabay.com/photo/2021/09/16/22/12/coffee-6631154_1280.jpg', 'image', 0, '2025-03-02 05:16:02');
INSERT INTO `messages` VALUES (18, '4', 'U1056300379', '16', 'The Main Window consists of several toolbars and panes for you to work on connections, database objects and advanced tools. I will greet this day with love in my heart. Navicat Data Modeler enables you to build high-quality conceptual, logical and physical data models for a wide variety of audiences. The reason why a great man is great is that he resolves to be a great man. Navicat Monitor requires a repository to store alerts and metrics for historical analysis.', 'image', 1, '2017-08-06 18:41:01');
INSERT INTO `messages` VALUES (19, 'U3591983558', 'U3591983558', '31', 'If it scares you, it might be a good thing to try. Flexible settings enable you to set up a custom key for comparison and synchronization.', 'file', 1, '2016-01-04 10:47:44');
INSERT INTO `messages` VALUES (20, 'U8040067632', 'U813570619', '11', '这是测试消息', 'text', 0, '2022-05-09 10:25:19');
INSERT INTO `messages` VALUES (21, 'U3009388591', 'U813570619', '1', '3', 'attend', 1, '2021-12-06 08:20:40');
INSERT INTO `messages` VALUES (22, 'U6999638479', 'U813570619', '10', '10', 'attend', 1, '2024-06-18 08:38:32');
INSERT INTO `messages` VALUES (23, 'U6276227474', 'U6276227474', '3', 'Optimism is the one quality more associated with success and happiness than any other. You can select any connections, objects or projects, and then select the corresponding buttons on the Information Pane. Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more. After comparing data, the window shows the number of records that will be inserted, updated or deleted in the target.', 'attend', 1, '2021-07-24 09:51:37');
INSERT INTO `messages` VALUES (24, 'U3009388591', 'U3009388591', '35', 'If your Internet Service Provider (ISP) does not provide direct access to its server, Secure Tunneling Protocol (SSH) / HTTP is another solution. All the Navicat Cloud objects are located under different projects. You can share the project to other Navicat Cloud accounts for collaboration. Flexible settings enable you to set up a custom key for comparison and synchronization.', 'location', 0, '2019-07-06 22:58:56');
INSERT INTO `messages` VALUES (25, 'U7865400024', 'U7865400024', '12', 'How we spend our days is, of course, how we spend our lives. Navicat allows you to transfer data from one database and/or schema to another with detailed analytical process.', 'image', 0, '2016-02-13 06:55:24');
INSERT INTO `messages` VALUES (26, '3', 'U2083706442', '25', 'It collects process metrics such as CPU load, RAM usage, and a variety of other resources over SSH/SNMP. The Main Window consists of several toolbars and panes for you to work on connections, database objects and advanced tools. The past has no power over the present moment. The repository database can be an existing MySQL, MariaDB, PostgreSQL, SQL Server, or Amazon RDS instance.', 'file', 1, '2017-03-06 17:17:44');
INSERT INTO `messages` VALUES (27, 'U0828577737', 'U0828577737', '18', 'The On Startup feature allows you to control what tabs appear when you launch Navicat. There is no way to happiness. Happiness is the way.', 'file', 1, '2021-08-29 23:59:30');
INSERT INTO `messages` VALUES (28, 'U8040067632', 'U813570619', '11', '122.199288,29.967633', 'location', 0, '2022-05-27 12:41:56');
INSERT INTO `messages` VALUES (29, 'U9518255256', 'U9518255256', '2', 'If your Internet Service Provider (ISP) does not provide direct access to its server, Secure Tunneling Protocol (SSH) / HTTP is another solution. Difficult circumstances serve as a textbook of life for people.', 'image', 0, '2021-07-10 20:48:17');
INSERT INTO `messages` VALUES (30, 'U8135142364', 'U8135142364', '30', 'The first step is as good as half over. To get a secure connection, the first thing you need to do is to install OpenSSL Library and download Database Source. If you wait, all that happens is you get older. The Information Pane shows the detailed object information, project activities, the DDL of database objects, object dependencies, membership of users/roles and preview. You cannot save people, you can just love them. All the Navicat Cloud objects are located under different                   ', 'attend', 0, '2024-10-21 11:34:12');
INSERT INTO `messages` VALUES (31, '2', 'U6293209899', '24', 'In the middle of winter I at last discovered that there was in me an invincible summer. Optimism is the one quality more associated with success and happiness than any other. To clear or reload various internal caches, flush tables, or acquire locks, control-click your connection in the Navigation pane and select Flush and choose the flush option. You must have the reload privilege to use this feature.', 'image', 0, '2021-06-01 11:04:00');
INSERT INTO `messages` VALUES (32, 'U1455770562', 'U1455770562', '4', 'To successfully establish a new connection to local/remote server - no matter via SSL or SSH, set the database login information in the General tab. The past has no power over the present moment. A query is used to extract data from the database in a readable format according to the user\'s request. A query is used to extract data from the database in a readable format according to the user\'s request.', 'text', 1, '2023-12-06 10:05:40');
INSERT INTO `messages` VALUES (33, 'U9518255256', 'U9518255256', '8', 'Typically, it is employed as an encrypted version of Telnet. With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and easily create, organize, access and share information in a secure and easy way. Anyone who has never made a mistake has never tried anything new. Actually it is just in an idea when feel oneself can achieve and cannot achieve. I will greet this day with love in my heart. A man is not old until regrets take the place of dreams.', 'text', 0, '2015-02-17 00:54:12');
INSERT INTO `messages` VALUES (34, '2', 'U9796023214', '20', 'The Navigation pane employs tree structure which allows you to take action upon the database and their objects through their pop-up menus quickly and easily. Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more. What you get by achieving your goals is not as important as what you become by achieving your goals.', 'attend', 1, '2017-05-17 00:33:09');
INSERT INTO `messages` VALUES (35, 'U5441681828', 'U5441681828', '16', 'How we spend our days is, of course, how we spend our lives. Actually it is just in an idea when feel oneself can achieve and cannot achieve. The Main Window consists of several toolbars and panes for you to work on connections, database objects and advanced tools. The Navigation pane employs tree structure which allows you to take action upon the database and their objects through their pop-up menus quickly and easily. Sometimes you win, sometimes you learn. To successfully establish           ', 'file', 1, '2022-03-20 16:27:12');
INSERT INTO `messages` VALUES (36, 'U8014032139', 'U8014032139', '19', 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram buttons to change the object view. All journeys have secret destinations of which the traveler is unaware. The Navigation pane employs tree structure which allows you to take action upon the database and their objects through their pop-up menus quickly and easily. Remember that failure is an event, not a person. Navicat Monitor can be installed on any local computer or virtual machine and does                 ', 'file', 1, '2018-03-24 08:20:37');
INSERT INTO `messages` VALUES (37, 'U8321588183', 'U8321588183', '21', 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model files and virtual group information from Navicat, other Navicat family members, different machines and different platforms.', 'text', 0, '2016-01-27 10:22:50');
INSERT INTO `messages` VALUES (38, 'U3033735932', 'U3033735932', '17', 'If opportunity doesn’t knock, build a door. Navicat provides a wide range advanced features, such as compelling code editing capabilities, smart code-completion, SQL formatting, and more. A comfort zone is a beautiful place, but nothing ever grows there. Navicat is a multi-connections Database Administration tool allowing you to connect to MySQL, Oracle, PostgreSQL, SQLite, SQL Server, MariaDB and/or MongoDB databases, making database administration to multiple kinds of database so easy.', 'attend', 1, '2015-06-05 07:30:13');
INSERT INTO `messages` VALUES (39, 'U3009388591', 'U3009388591', '17', 'After comparing data, the window shows the number of records that will be inserted, updated or deleted in the target. Such sessions are also susceptible to session hijacking, where a malicious user takes over your session once you have authenticated. All the Navicat Cloud objects are located under different projects. You can share the project to other Navicat Cloud accounts for collaboration.', 'text', 0, '2017-02-10 00:30:51');
INSERT INTO `messages` VALUES (40, 'U5650519664', 'U5650519664', '36', 'It can also manage cloud databases such as Amazon Redshift, Amazon RDS, Alibaba Cloud. Features in Navicat are sophisticated enough to provide professional developers for all their specific needs, yet easy to learn for users who are new to database server.', 'location', 1, '2015-06-09 11:13:32');
INSERT INTO `messages` VALUES (41, 'U5002227403', 'U5002227403', '33', 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more. Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.', 'location', 1, '2023-07-11 23:10:26');
INSERT INTO `messages` VALUES (42, 'U5650519664', 'U5650519664', '2', 'The Synchronize to Database function will give you a full picture of all database differences. It collects process metrics such as CPU load, RAM usage, and a variety of other resources over SSH/SNMP. HTTP Tunneling is a method for connecting to a server that uses the same protocol (http://) and the same port (port 80) as a web server does.', 'text', 1, '2018-08-22 10:58:08');
INSERT INTO `messages` VALUES (43, 'U0791486112', 'U0791486112', '2', 'The past has no power over the present moment. After logged in the Navicat Cloud feature, the Navigation pane will be divided into Navicat Cloud and My Connections sections. If you wait, all that happens is you get older. Navicat Data Modeler is a powerful and cost-effective database design tool which helps you build high-quality conceptual, logical and physical data models. To start working with your server in Navicat, you should first establish a connection or several connections              ', 'attend', 0, '2015-09-15 07:57:03');
INSERT INTO `messages` VALUES (44, 'U8014032139', 'U8014032139', '41', 'Navicat provides powerful tools for working with queries: Query Editor for editing the query text directly, and Query Builder, Find Builder or Aggregate Builder for building queries visually.', 'location', 1, '2022-12-01 00:01:38');
INSERT INTO `messages` VALUES (45, 'U0604674752', 'U0604674752', '15', 'Remember that failure is an event, not a person. Creativity is intelligence having fun. It can also manage cloud databases such as Amazon Redshift, Amazon RDS, Alibaba Cloud. Features in Navicat are sophisticated enough to provide professional developers for all their specific needs, yet easy to learn for users who are new to database server. A query is used to extract data from the database in a readable format according to the user\'s request.', 'file', 0, '2015-12-03 21:14:11');
INSERT INTO `messages` VALUES (46, 'U9006144310', 'U9006144310', '30', 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s shell without compromising security. Anyone who has never made a mistake has never tried anything new. I may not have gone where I intended to go, but I think I have ended up where I needed to be. Navicat Monitor is a safe, simple and agentless remote server monitoring tool that is packed with powerful features to make your monitoring effective as possible.', 'file', 1, '2023-02-22 19:48:46');
INSERT INTO `messages` VALUES (47, 'U0604674752', 'U0604674752', '32', 'If your Internet Service Provider (ISP) does not provide direct access to its server, Secure Tunneling Protocol (SSH) / HTTP is another solution. The first step is as good as half over. To get a secure connection, the first thing you need to do is to install OpenSSL Library and download Database Source.', 'file', 1, '2016-12-19 14:46:32');
INSERT INTO `messages` VALUES (48, 'U3883192244', 'U3883192244', '27', 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.', 'attend', 1, '2021-01-04 23:01:49');
INSERT INTO `messages` VALUES (49, 'U7885016104', 'U7885016104', '23', 'Navicat authorizes you to make connection to remote servers running on different platforms (i.e. Windows, macOS, Linux and UNIX), and supports PAM and GSSAPI authentication.', 'image', 1, '2019-01-30 22:31:09');
INSERT INTO `messages` VALUES (50, 'U3009388591', 'U3009388591', '3', 'Secure SHell (SSH) is a program to log in into another computer over a network, execute commands on a remote server, and move files from one machine to another. Anyone who has never made a mistake has never tried anything new. SQL Editor allows you to create and edit SQL text, prepare and execute selected queries. If you wait, all that happens is you get older.', 'file', 1, '2018-08-13 00:40:44');
INSERT INTO `messages` VALUES (51, 'U813570619', 'U813570619', '1', '测试消息', 'text', 0, '2025-05-09 15:31:10');
INSERT INTO `messages` VALUES (55, 'U813570619', 'null', '1', 'hello', 'text', 0, '2025-09-02 12:10:31');

-- ----------------------------
-- Table structure for student_courses
-- ----------------------------
DROP TABLE IF EXISTS `student_courses`;
CREATE TABLE `student_courses`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ID',
  `student_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生ID',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程ID',
  `selected_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(1:正常,0:退课)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_student_course`(`student_id` ASC, `course_id` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_sc_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_sc_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生选课表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_courses
-- ----------------------------
INSERT INTO `student_courses` VALUES ('10', 'U813570619', '1', '2024-10-24 13:19:37', 1);
INSERT INTO `student_courses` VALUES ('11', 'U813570619', '10', '2024-11-01 15:21:52', 1);
INSERT INTO `student_courses` VALUES ('12', 'U813570619', '11', '2025-03-05 16:22:10', 1);
INSERT INTO `student_courses` VALUES ('2', 'U0586622100', '33', '2022-11-16 18:09:52', 1);
INSERT INTO `student_courses` VALUES ('3', 'U6293209899', '14', '2022-11-17 18:09:52', 1);
INSERT INTO `student_courses` VALUES ('4', 'U2083706442', '18', '2022-11-18 10:09:52', 1);
INSERT INTO `student_courses` VALUES ('5', 'U6276227474', '29', '2022-11-19 09:09:52', 1);
INSERT INTO `student_courses` VALUES ('6', 'U8135142364', '12', '2022-11-21 11:09:52', 1);
INSERT INTO `student_courses` VALUES ('7', 'U6276227474', '15', '2023-11-26 15:09:52', 1);
INSERT INTO `student_courses` VALUES ('8', 'U0586622100', '41', '2023-03-16 18:11:17', 1);
INSERT INTO `student_courses` VALUES ('9', 'U2083706442', '5', '2024-03-01 10:11:30', 1);

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生ID(与users.id关联)',
  `student_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `class_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级ID',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '院系',
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `enrollment_year` int NULL DEFAULT NULL COMMENT '入学年份',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_student_number`(`student_number` ASC) USING BTREE,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('U0586622100', '9165974893', 'A16YX1', 'Medical', 'medy', 2016);
INSERT INTO `students` VALUES ('U1844467536', '9215486465', 'A21SX2', 'Info', 'Math', 2021);
INSERT INTO `students` VALUES ('U2083706442', '9194632312', 'A19YY1', 'Foreign', 'English', 2019);
INSERT INTO `students` VALUES ('U6276227474', '9225645613', 'A22RY1', 'Foreign', 'Japanese', 2022);
INSERT INTO `students` VALUES ('U6293209899', '9201645412', 'A20WL1', 'Info', 'Physical', 2020);
INSERT INTO `students` VALUES ('U8135142364', '9209848541', 'A20SC1', 'Marin', 'Boat', 2020);
INSERT INTO `students` VALUES ('U813570619', '9210308115', 'A21CS3', 'Info', 'CS', 2021);

-- ----------------------------
-- Table structure for system_logs
-- ----------------------------
DROP TABLE IF EXISTS `system_logs`;
CREATE TABLE `system_logs`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志ID',
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户ID',
  `action` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `target_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标类型',
  `target_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标ID',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理',
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作详情',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_action`(`action` ASC) USING BTREE,
  INDEX `idx_created_at`(`created_at` ASC) USING BTREE,
  CONSTRAINT `fk_log_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_logs
-- ----------------------------

-- ----------------------------
-- Table structure for teacher_courses
-- ----------------------------
DROP TABLE IF EXISTS `teacher_courses`;
CREATE TABLE `teacher_courses`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ID',
  `teacher_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师ID',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程ID',
  `is_main` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否主讲(1:是,0:否)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_teacher_course`(`teacher_id` ASC, `course_id` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_tc_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_tc_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教师授课表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_courses
-- ----------------------------
INSERT INTO `teacher_courses` VALUES ('1', 'U1056300379', '13', 1);
INSERT INTO `teacher_courses` VALUES ('10', 'U4588120190', '9', 1);
INSERT INTO `teacher_courses` VALUES ('11', 'U3009388591', '1', 1);
INSERT INTO `teacher_courses` VALUES ('12', 'U6999638479', '10', 1);
INSERT INTO `teacher_courses` VALUES ('13', 'U8040067632', '11', 1);
INSERT INTO `teacher_courses` VALUES ('2', 'U1056300379', '33', 1);
INSERT INTO `teacher_courses` VALUES ('3', 'U2083706442', '12', 1);
INSERT INTO `teacher_courses` VALUES ('4', 'U2083706442', '15', 1);
INSERT INTO `teacher_courses` VALUES ('5', 'U2083706442', '16', 1);
INSERT INTO `teacher_courses` VALUES ('6', 'U3009388591', '21', 1);
INSERT INTO `teacher_courses` VALUES ('7', 'U3499510620', '29', 1);
INSERT INTO `teacher_courses` VALUES ('8', 'U4588120190', '41', 1);
INSERT INTO `teacher_courses` VALUES ('9', 'U5002227403', '5', 1);

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师ID(与users.id关联)',
  `teacher_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工号',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '院系',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称',
  `office` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '办公室',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_teacher_number`(`teacher_number` ASC) USING BTREE,
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES ('U1056300379', '1231547412', '师范学院', '讲师', '文理楼114');
INSERT INTO `teachers` VALUES ('U2083706442', '2354487847', '船舶与海运学院', '讲师', '文理楼116');
INSERT INTO `teachers` VALUES ('U3009388591', '1397846145', '信息工程学院', '助教', '文理楼104');
INSERT INTO `teachers` VALUES ('U3499510620', '1654544770', '经济管理学院', '副教授', '文理楼210');
INSERT INTO `teachers` VALUES ('U4588120190', '5468433119', '生态学院', '讲师', '文理楼201');
INSERT INTO `teachers` VALUES ('U5002227403', '3645611106', '生态学院', '教授', '文理楼130');
INSERT INTO `teachers` VALUES ('U6999638479', '4524524770', '信息工程学院', '副教授', '文理楼111');
INSERT INTO `teachers` VALUES ('U8040067632', '4578764535', '信息工程学院', '副教授', '文理楼231');
INSERT INTO `teachers` VALUES ('U8135142364', '4889451501', '外国语学院', '教授', '文理楼102');
INSERT INTO `teachers` VALUES ('U9796023214', '541564646', '音乐学院', '副教授', '文理楼215');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID(UUID)',
  `openid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户在微信小程序的唯一标识',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码(加密存储)',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `role` enum('admin','teacher','student') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户角色',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_role`(`role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户基础表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '2c96plPsyl', 'Stanley Soto', 'OKba96yZIY', '余岚', 'https://cdn.pixabay.com/photo/2025/03/15/14/21/abstract-9472319_1280.png', '12022558975', 'stanley1206@gmail.com', 'student', 1, '2018-11-01 04:52:13', '2025-05-08 14:15:11');
INSERT INTO `users` VALUES ('2', 'G85NUqay0H', 'John Roberts', 'McYogrQSrl', '董杰宏', 'https://cdn.pixabay.com/photo/2020/07/14/18/02/head-5405110_1280.png', '17607984478', 'roberts08@icloud.com', 'admin', 1, '2016-11-03 22:41:39', '2025-05-08 14:15:16');
INSERT INTO `users` VALUES ('3', '35xPhGHXqg', 'Carmen Allen', 'IyH1z9JDEj', '谭嘉伦', 'https://cdn.pixabay.com/photo/2016/03/31/19/58/avatar-1295429_1280.png', '1288343910', 'allen4@icloud.com', 'teacher', 1, '2013-03-31 23:13:26', '2025-05-08 14:15:21');
INSERT INTO `users` VALUES ('4', 'RErDTEIEbc', 'Carlos Gutierrez', '6STDOc0fZA', '于安琪', 'https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635464_1280.png', '116578232681', 'cg1950@outlook.com', 'student', 1, '2018-10-17 09:46:21', '2025-05-08 14:15:24');
INSERT INTO `users` VALUES ('5', 'PrbzOqiopA', 'Eugene Dunn', 'OMRbWjRY8J', '尹震南', 'https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635456_1280.png', '114016481917', 'eugenedunn@icloud.com', 'admin', 1, '2021-09-16 16:43:36', '2025-05-08 14:15:44');
INSERT INTO `users` VALUES ('U0586622100', 'w7KcpeDZVmejjWBvVPPCVGjFvn4EMfk0', 'Marvin', 'FN99yHFwtq', '徐子韬', 'https://cdn.pixabay.com/photo/2021/11/12/03/04/woman-6787784_1280.png', '1281811102', 'memarvi1@hotmail.com', 'student', 1, '2013-01-30 18:06:19', '2025-05-08 14:14:21');
INSERT INTO `users` VALUES ('U0604674752', 'ncUiDjqjobM1KKt9wqO65y3BOBfznyQk', 'Ricky', '8ObYuxT6pa', '陶秀英', 'https://cdn.pixabay.com/photo/2016/06/23/18/55/apple-1475977_1280.png', '17556943882', 'riricky225@yahoo.com', 'student', 1, '2016-12-23 18:22:49', '2025-05-08 14:14:30');
INSERT INTO `users` VALUES ('U0791486112', 'DhBNY0NGyAMkk6kuEq63Wq4FJBLnps9D', 'Juan', 'qFHeS51NV6', '郭宇宁', 'https://cdn.pixabay.com/photo/2022/02/13/17/22/cartoon-easter-bunny-7011655_1280.jpg', '176902886142', 'evansjuan@outlook.com', 'student', 1, '2002-08-05 01:22:42', '2025-05-08 14:14:42');
INSERT INTO `users` VALUES ('U0828577737', 'gVkErOA6yzeuIyB1KrbVu3b7oZxDMFiF', 'Kimberly', 'PPeEAsm9aO', '曾云熙', 'https://cdn.pixabay.com/photo/2016/08/21/16/31/emoticon-1610228_1280.png', '12169594487', 'kid@outlook.com', 'student', 1, '2001-03-02 00:01:34', '2025-05-08 14:14:56');
INSERT INTO `users` VALUES ('U1056300379', 'k7bhWFcgzc3eKNA6Y0Z8OMgAR0Ld7hb1', 'Lois', 'qmB8WQCEXw', '毛子异', 'https://cdn.pixabay.com/photo/2013/07/12/19/14/angry-154378_1280.png', '1106682869', 'collinslois105@mail.com', 'teacher', 1, '2017-06-24 15:23:19', '2025-05-08 14:15:54');
INSERT INTO `users` VALUES ('U1455770562', '8vc6iZyEcHJLzhG0mhLOvW2KMxl5DhtG', 'Howard', '4rLcRwVs18', '苏宇宁', 'https://cdn.pixabay.com/photo/2020/08/02/18/08/woman-5458225_1280.png', '120730911274', 'jimenezhoward6@gmail.com', 'student', 1, '2016-03-21 16:17:58', '2025-05-08 14:16:24');
INSERT INTO `users` VALUES ('U1844467536', 'nU2hXu8PhfmBuW9Mz0pVFMaKmWqtuaDx', 'Jack', 'TPSBSqs6rS', '金宇宁', 'https://cdn.pixabay.com/photo/2024/08/02/16/56/little-monster-8940271_1280.jpg', '17601151205', 'jack1986@icloud.com', 'student', 1, '2005-05-04 00:49:00', '2025-05-08 14:17:05');
INSERT INTO `users` VALUES ('U1e5bb9232', 'wSvq94xBFvjuZF6NCZMsuIZ2m2gpk1az', 'jackson111', '156458484', '徐强胜', 'https://cdn.pixabay.com/photo/2023/10/07/22/20/ai-generated-8301045_1280.jpg', '16456589465', 'whdioawhdi@163.com', 'student', 1, '2025-08-15 18:57:07', '2025-08-24 19:42:30');
INSERT INTO `users` VALUES ('U2083706442', 'WaeYrTwu1Xu0OMhCFniVPwuDSTGWpigx', 'Joel', 'ipNVR3Gtej', '何晓明', 'https://cdn.pixabay.com/photo/2024/11/19/07/01/ai-generated-9208261_1280.jpg', '12050907640', 'joelr@yahoo.com', 'teacher', 1, '2018-04-03 20:51:46', '2025-05-08 14:17:09');
INSERT INTO `users` VALUES ('U3009388591', 'Xeol3V2xAnAS5HbsprICb2wqkXfn5HPA', 'Francisco', 'Af4DFUt5k5', '梁云熙', 'https://cdn.pixabay.com/photo/2024/10/28/09/40/ai-generated-9155663_1280.png', '20-220-2401', 'franciscogutie@mail.com', 'teacher', 1, '2017-02-27 01:13:37', '2025-05-08 14:17:10');
INSERT INTO `users` VALUES ('U3033735932', 'kWSvIJI5Fh1QJdxfkePpdCDsvagUjfyy', 'Tina', 'bYPv8cy1QO', '马嘉伦', 'https://image.hinakayama.com/Others', '181-7032-3104', 'tjord@gmail.com', 'student', 1, '2008-05-24 02:04:39', '2025-05-05 17:47:16');
INSERT INTO `users` VALUES ('U3499510620', 'fheXczNQUM24oRsl0jODwr1y0jDtSnPK', 'Chris', '7rCGh4KMPu', '孔震南', 'https://video.daisa.xyz/Handcrafts', '170-9942-5395', 'chrguzm1942@gmail.com', 'teacher', 1, '2000-09-04 19:22:49', '2021-07-12 08:18:45');
INSERT INTO `users` VALUES ('U3591983558', 'ls6rdTpeIqdUGEVGJ6CChAfG5hOUTreJ', 'Patricia', 'FN8tl2BxBh', '胡睿', 'https://image.mak3.net/AppsGames', '181-2632-3180', 'patriciahow@icloud.com', 'student', 1, '2001-05-11 08:33:49', '2025-05-08 14:12:57');
INSERT INTO `users` VALUES ('U3854067691', 'kEknNX43z3GaSidokptZPccmqRVuKMmF', 'Richard', 'JybXDnsJYh', '曾晓明', 'https://www.whitetiffa603.co.jp/AutomotivePartsAccessories', '28-3425-1497', 'richahunter@gmail.com', 'student', 1, '2011-03-09 06:52:46', '2021-08-29 18:54:27');
INSERT INTO `users` VALUES ('U3862644602', '3Y2CblbJn9l8kftRN888bZIXvC72AETN', 'Danny', 'R4qx05GoD6', '范岚', 'https://video.mingszecheung.us/BeautyPersonalCare', '21-632-9151', 'edanny407@outlook.com', 'student', 1, '2004-02-25 16:41:49', '2005-05-19 14:49:30');
INSERT INTO `users` VALUES ('U3883192244', 'IbFeQ0cJDMQhECbj17ygilH54psRUs8Y', 'Ernest', 'TYpsxDu6mD', '蒋嘉伦', 'https://drive.anqi510.com/PetSupplies', '167-3495-5312', 'hamilton6@hotmail.com', 'student', 0, '2005-02-22 09:13:26', '2025-05-05 17:47:18');
INSERT INTO `users` VALUES ('U4588120190', 'rhiTI0fGaXc0xHykl2gT9CGKbey1VEGl', 'Gloria', '4u0u4uFHlh', '吴詩涵', 'https://video.ytakwah.org/PetSupplies', '10-5300-0010', 'kimg5@outlook.com', 'teacher', 1, '2016-11-17 14:10:56', '2014-04-16 10:36:04');
INSERT INTO `users` VALUES ('U4855931633', 'j5mn1nM90l4Nc3E53pD22JnMI4jYmBbW', 'Kelly', 'WOzi0L8tI2', '胡云熙', 'http://auth.tokw.xyz/IndustrialScientificSupplies', '130-2861-0643', 'fisherkelly@gmail.com', 'student', 0, '2022-02-15 14:43:17', '2009-05-12 03:24:36');
INSERT INTO `users` VALUES ('U5002227403', 'VWy8pqloPGINW2muXPeuRTfAfkrl3r7h', 'Christine', 'QhBFps1drN', '林云熙', 'http://image.seben55.cn/AutomotivePartsAccessories', '20-228-6285', 'christinecoope@hotmail.com', 'teacher', 1, '2008-06-19 04:49:50', '2022-11-19 20:12:05');
INSERT INTO `users` VALUES ('U5182717058', 'KA5ZGhchEKwT049jnva76WnKUk82AjRf', 'Adam', 's0xMIHRAA2', '于子异', 'https://video.tszching5.jp/MusicalInstrument', '149-9409-5279', 'adamc@yahoo.com', 'teacher', 1, '2000-06-11 18:50:12', '2023-09-02 20:18:01');
INSERT INTO `users` VALUES ('U5313955605', 'nhsWsbMgigACt7Qtx18VPXiLwPcZECLI', 'Alan', 'bvoU78SH5a', '余云熙', 'http://www.luh89.org/IndustrialScientificSupplies', '161-6654-4828', 'whitealan@gmail.com', 'student', 0, '2021-03-10 16:40:27', '2025-05-05 17:47:21');
INSERT INTO `users` VALUES ('U5344387079', 'RcopYX2NaaW5WjUlY38UmsT8yuIxPSr3', 'Diane', 'IKaMGEve9n', '周安琪', 'https://drive.zhiyyan.co.jp/SportsOutdoor', '172-7366-6746', 'vargasdiane@outlook.com', 'student', 0, '2016-12-09 14:04:16', '2023-09-06 12:58:06');
INSERT INTO `users` VALUES ('U5441681828', 'daCSUMTEVSzuQeTE7OjTADGQ7ZrgTrJX', 'Pamela', 'KgxbdVkuHG', '方子异', 'http://www.louisea69.net/ToolsHomeDecoration', '198-8898-6611', 'riverapa@hotmail.com', 'teacher', 0, '2013-10-16 19:06:45', '2008-08-07 08:19:20');
INSERT INTO `users` VALUES ('U5650519664', '4DAGYLmyre7FqQIsFGJeoKOw8OJ3Uz8Z', 'Tammy', 'AhiE4EWJse', '阎子异', 'https://auth.jacwatson.jp/PetSupplies', '755-6570-1082', 'tevans@yahoo.com', 'student', 0, '2009-01-16 09:12:41', '2025-05-05 17:47:23');
INSERT INTO `users` VALUES ('U6271421880', 'OMEpFbTi6hB0CDSgI6W8ktWDIbQNYpcA', 'Leroy', 'ehHkBg09PQ', '吴睿', 'https://drive.sukyeeti4.xyz/Handcrafts', '130-1994-6214', 'lerowoo4@mail.com', 'teacher', 0, '2014-11-24 06:38:08', '2004-09-10 11:00:53');
INSERT INTO `users` VALUES ('U6276227474', '4W0TAPOYk2Vrr3w4xuRSNx3UPKCwBbJs', 'Dennis', 'xrfqjtXRhi', '宋嘉伦', 'https://image.choiyuling.org/Others', '769-425-3370', 'denniblack224@icloud.com', 'teacher', 1, '2007-06-15 10:17:04', '2024-10-05 19:31:28');
INSERT INTO `users` VALUES ('U6293209899', 'fEEWKrBBj7fCCupP7cRb9FMhRIxFODee', 'Shawn', 'BxneETtoAr', '孙宇宁', 'http://auth.mary1008.co.jp/ToolsHomeDecoration', '167-6978-5355', 'watssha@hotmail.com', 'student', 1, '2017-03-24 00:26:30', '2018-02-16 15:09:49');
INSERT INTO `users` VALUES ('U6337946643', '7XBSwBceMImwgcPAB2icp74KUSB6qvTG', 'Danielle', 'NbHpAMFvZf', '贾秀英', 'http://image.kaitonomur.net/AppsGames', '185-5858-6072', 'ld1103@yahoo.com', 'student', 0, '2014-09-20 04:19:32', '2025-05-05 17:47:25');
INSERT INTO `users` VALUES ('U6418529094', 'kDfwyDLwQih6CXGLM4krba3LSP0WOWug', 'Jane', 'lTUAmvTxEd', '郭晓明', 'https://www.tsesk.info/FilmSupplies', '187-9289-3603', 'yojan2011@outlook.com', 'student', 0, '2022-01-20 07:12:19', '2025-05-05 17:47:32');
INSERT INTO `users` VALUES ('U6703912954', '3bSq6R6CEgEp6GlOPaML7pVGQLIgtNPc', 'Russell', 'xTvAfWKMOx', '石嘉伦', 'https://drive.antw.cn/Beauty', '28-604-3038', 'moralesrussell68@gmail.com', 'student', 0, '2015-02-01 10:33:07', '2025-05-05 17:47:34');
INSERT INTO `users` VALUES ('U6999638479', '5s4mbdaLBy7mEWLR510hRs4GHf64gQZ0', 'Sharon', 'QrwWij9KMM', '薛致远', 'http://www.yfyeow.com/AppsGames', '760-4845-8028', 'sharonort07@yahoo.com', 'teacher', 0, '2001-04-09 14:21:50', '2020-05-12 23:05:15');
INSERT INTO `users` VALUES ('U7245038581', 'PRS3ZATDUAn6SkF4iGhXWHI6e3mIGOLb', 'Hazel', 'TY5lmA4DHI', '何秀英', 'http://drive.qianxiao9.cn/CenturionGardenOutdoor', '21-992-7782', 'johnson827@gmail.com', 'student', 1, '2022-11-12 12:27:28', '2025-05-05 17:47:31');
INSERT INTO `users` VALUES ('U7486673570', 'q6zDiqHjPhWKYybS6EiF2mHAIfs81m5T', 'Jeffrey', 'SlBqwUpBV6', '邹云熙', 'https://drive.mo511.info/ArtsHandicraftsSewing', '145-0313-7871', 'ruizjeffrey@outlook.com', 'student', 0, '2003-07-05 19:15:37', '2025-05-05 17:47:36');
INSERT INTO `users` VALUES ('U7810384928', 'U0Ge5rVppaWqDWSLXiNYOWEfwafA8b0k', 'Judith', 'lbtLYsSqVN', '常璐', 'http://image.laiyankwan.org/MusicalInstrument', '146-0271-5085', 'judith6@mail.com', 'student', 0, '2014-07-23 15:15:18', '2010-06-06 12:39:37');
INSERT INTO `users` VALUES ('U7865400024', 'cVcRB1ZKgTADkAMS8qexCyWB37AmYcXQ', 'Glenn', 'DhSqXiCQ7r', '孙子韬', 'http://image.sakun.info/CollectiblesFineArt', '21-924-8543', 'stone1967@gmail.com', 'student', 0, '2018-09-15 07:31:06', '2025-05-05 17:47:39');
INSERT INTO `users` VALUES ('U7885016104', '8HQx0e0pFbZt650lOLJgdSx7Onnd3k7F', 'Amy', 'udyb9sWG7i', '金睿', 'http://video.yoshida128.us/HouseholdKitchenAppliances', '131-2648-5661', 'aburns@hotmail.com', 'teacher', 0, '2000-10-14 19:39:05', '2019-11-18 03:06:22');
INSERT INTO `users` VALUES ('U8014032139', '6PP99ZDflGMWUiTmzOT2o5vUH59d8QC4', 'Jeremy', 'xsLmmJVgZa', '谭安琪', 'http://www.renat7.org/CollectiblesFineArt', '769-058-2547', 'jerelewis2009@hotmail.com', 'student', 1, '2008-02-21 02:09:17', '2005-03-05 09:35:54');
INSERT INTO `users` VALUES ('U8040067632', 'qwvd9ZLWwkoLseCH2WnMYaWiI1QcFrtb', 'Ashley', 'qUTEl4OeB0', '朱詩涵', 'https://video.alanc.cn/Appliances', '10-652-2183', 'ashley1@icloud.com', 'teacher', 1, '2013-11-25 03:58:47', '2019-10-08 05:08:26');
INSERT INTO `users` VALUES ('U8135142364', 'rzztyNZxAYJVtxKEAuOt70aTaesfiAhX', 'Sarah', 'vEDrQms6wI', '廖安琪', 'https://auth.hypang.co.jp/CollectiblesFineArt', '174-2288-0459', 'ellisarah926@icloud.com', 'teacher', 1, '2013-11-28 06:58:07', '2021-03-20 04:03:38');
INSERT INTO `users` VALUES ('U813570619', 'obsW266H9qCCXUtEscAk9WARsIEo', 'Hanlc', '20030505Chlnb', '陈翰林', 'https://cdn.pixabay.com/photo/2023/10/07/22/20/ai-generated-8301045_1280.jpg', '19818021510', 'chlnb813570619@163.com', 'student', 1, '2025-04-14 02:02:12', '2025-05-26 22:02:39');
INSERT INTO `users` VALUES ('U8210279826', 'wSvq94xBFvjuZF6NCZMsuIZ2m2gpk1dj', 'Jimmy', 'eZkzjdCkxf', '黎震南', 'https://auth.jane9.us/Books', '755-656-0291', 'ljimmy@gmail.com', 'student', 0, '2022-12-26 14:32:26', '2002-02-14 22:20:08');
INSERT INTO `users` VALUES ('U8233052549', 'yHHJ9nRkDl4X3FKCeduOaWA6lN0GeM2O', 'Aaron', 'h5GLNiv25z', '何杰宏', 'https://image.huntjan212.info/ComputersElectronics', '28-480-7888', 'roaaron@icloud.com', 'student', 0, '2003-07-17 06:21:06', '2014-12-14 16:06:29');
INSERT INTO `users` VALUES ('U8321588183', 'fxdtgMrbNaARM6QtQ7u3dEW1Rjbktmda', 'Vincent', 'W4A1IQe836', '韦詩涵', 'https://image.zhiyuanxia90.com/Books', '760-9742-6900', 'jonvincent227@icloud.com', 'student', 0, '2006-03-04 11:21:13', '2025-05-05 17:47:41');
INSERT INTO `users` VALUES ('U8562596510', 'hCpYgIWShIWu7O2VY2hygMLfS42sOSGh', 'Laura', 'W6qZMTTYhq', '江秀英', 'https://drive.hmiya1.org/HealthBabyCare', '138-7698-8942', 'laura1952@outlook.com', 'student', 0, '2017-04-29 23:48:01', '2018-04-13 21:27:19');
INSERT INTO `users` VALUES ('U9000774263', 'mZjhQyF7mF82V2EKpnI06YI7nBdrzuSw', 'Melissa', 'Wx2aFFlNND', '秦詩涵', 'https://www.yeungkk.net/CellPhonesAccessories', '21-0005-9463', 'melisc8@outlook.com', 'student', 1, '2012-02-10 02:44:10', '2025-05-05 17:47:44');
INSERT INTO `users` VALUES ('U9006144310', 'rAxnSLroUBMbTfIJsyyuvIjZmlG0btW4', 'Wayne', 'NYYxSMxaJ5', '段岚', 'https://drive.zhennxie.xyz/ComputersElectronics', '28-6638-1474', 'waramirez41@yahoo.com', 'student', 1, '2020-01-19 07:38:51', '2025-05-05 17:47:45');
INSERT INTO `users` VALUES ('U9482449866', 'eOWhmIbS6jDiifiguTKJu4p0QRbnTcVZ', 'Marjorie', 'nG6pHrqBqS', '田子异', 'https://drive.hrobert.net/CollectiblesFineArt', '10-001-5487', 'hernamarjo@mail.com', 'student', 1, '2005-06-03 03:18:33', '2025-05-05 17:47:47');
INSERT INTO `users` VALUES ('U9518255256', 'Sli6eKZFpvV6vlksTTQDSuK8UauRKU4K', 'Timothy', 'PpyO5qwpJp', '邵睿', 'https://drive.blackpaula.jp/PetSupplies', '755-424-8906', 'henderson40@mail.com', 'student', 0, '2008-10-22 15:53:41', '2025-05-05 17:47:49');
INSERT INTO `users` VALUES ('U9612494155', 'kGkqsxUIpgszgl3PjDxy3dvFoE8UfgF2', 'Walter', 'wn93SfucQq', '贾晓明', 'https://video.mcha.org/Handcrafts', '10-453-0185', 'florwalter@gmail.com', 'student', 0, '2003-04-03 06:29:35', '2025-05-05 17:47:52');
INSERT INTO `users` VALUES ('U9796023214', 'HyYA2n21xMZGkD9tr2wSLl1e61gfUfDa', 'Norma', 'mUpCf6tH2P', '杜子韬', 'https://video.aoshi5.jp/HouseholdKitchenAppliances', '21-0312-5151', 'sancheznorma@gmail.com', 'teacher', 1, '2015-10-22 17:48:12', '2025-05-05 17:47:56');
INSERT INTO `users` VALUES ('U9866087380', '1KVVTVGrJaLtYsT8Zu0cOOubGAKIDXIp', 'Sylvia', 'HNydj3c2hT', '任璐', 'https://auth.zhiyuan84.com/Food', '144-6442-2883', 'sylvia8@gmail.com', 'student', 1, '2016-01-30 05:48:20', '2008-05-06 11:28:46');

SET FOREIGN_KEY_CHECKS = 1;
