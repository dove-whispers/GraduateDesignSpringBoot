/*
Navicat MySQL Data Transfer

Source Server         : MySQLConnectTest
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : graduate_design

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2023-05-16 13:45:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for deal_record
-- ----------------------------
DROP TABLE IF EXISTS `deal_record`;
CREATE TABLE `deal_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作记录表id',
  `expensive_id` int(11) NOT NULL COMMENT '报销单id',
  `em_id` int(11) NOT NULL COMMENT '操作员工id',
  `deal_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '处理时间',
  `deal_way` varchar(20) DEFAULT NULL COMMENT '处理方式',
  `deal_result` varchar(20) DEFAULT NULL COMMENT '处理结果',
  `comment` varchar(100) DEFAULT NULL COMMENT '处理结果备注',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COMMENT='操作记录表';

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `dep_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL COMMENT '部门名称',
  `address` varchar(1000) NOT NULL COMMENT '办公地点',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1代表有效,0代表无效',
  PRIMARY KEY (`dep_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `em_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `login_name` varchar(64) NOT NULL COMMENT '用户名',
  `dep_id` int(11) NOT NULL COMMENT '部门id',
  `position_id` int(11) NOT NULL COMMENT '职位id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(0:离职 1:在职)',
  PRIMARY KEY (`em_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- ----------------------------
-- Table structure for expense_report
-- ----------------------------
DROP TABLE IF EXISTS `expense_report`;
CREATE TABLE `expense_report` (
  `expense_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报销单id',
  `cause` varchar(100) NOT NULL COMMENT '报销原因',
  `em_id` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` datetime NOT NULL COMMENT '截止时间',
  `next_deal_em` int(11) DEFAULT NULL COMMENT '待处理人',
  `total_amount` decimal(10,2) NOT NULL COMMENT '报销总金额',
  `status` varchar(20) NOT NULL COMMENT '状态',
  PRIMARY KEY (`expense_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='报销单表';

-- ----------------------------
-- Table structure for expense_report_detail
-- ----------------------------
DROP TABLE IF EXISTS `expense_report_detail`;
CREATE TABLE `expense_report_detail` (
  `expensive_detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报销单细节表id',
  `expensive_id` int(11) NOT NULL COMMENT '报销单id',
  `item` varchar(20) NOT NULL COMMENT '报销项目',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开票日期',
  `type` varchar(20) DEFAULT NULL COMMENT '类别',
  `code` varchar(20) NOT NULL COMMENT '发票代码',
  `num` varchar(20) NOT NULL COMMENT '发票号码',
  `amount` decimal(10,2) NOT NULL COMMENT '费用明细',
  `comment` varchar(100) DEFAULT NULL COMMENT '费用备注',
  `image` mediumblob COMMENT '报销单图片',
  PRIMARY KEY (`expensive_detail_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='报销单细节表';

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `position_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '职位表id',
  `position_name` varchar(255) NOT NULL COMMENT '职位名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:有效 0:无效',
  PRIMARY KEY (`position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='职位表';
