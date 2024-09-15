-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yunque-cloud
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表Id',
                             `name` varchar(200) DEFAULT '' COMMENT '表名称',
                             `comment` varchar(200) DEFAULT '' COMMENT '表描述',
                             `class_name` varchar(50) DEFAULT '' COMMENT '实体类名称',
                             `prefix` varchar(50) DEFAULT '' COMMENT '前缀名称',
                             `tpl_category` varchar(10) NOT NULL DEFAULT 'base' COMMENT '使用的模板（base单表 tree树表 merge关联表）',
                             `rd_package_name` varchar(100) DEFAULT NULL COMMENT '生成后端包路径',
                             `fe_package_name` varchar(100) DEFAULT NULL COMMENT '生成前端包路径',
                             `authority_name` varchar(30) DEFAULT NULL COMMENT '生成权限标识',
                             `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
                             `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
                             `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
                             `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
                             `gen_type` char(1) DEFAULT '0' COMMENT '生成路径类型（0默认路径 1自定义路径）',
                             `gen_path` varchar(200) DEFAULT '/' COMMENT '后端生成路径（不填默认项目路径）',
                             `ui_path` varchar(200) DEFAULT '/' COMMENT '前端生成路径（不填默认项目路径）',
                             `options` json DEFAULT NULL COMMENT '其它生成选项',
                             `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                             `create_by` bigint DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_by` bigint DEFAULT NULL COMMENT '更新者',
                             `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字段Id',
                                    `table_id` bigint NOT NULL COMMENT '表Id',
                                    `name` varchar(100) DEFAULT NULL COMMENT '列名称',
                                    `comment` varchar(100) DEFAULT NULL COMMENT '列描述',
                                    `type` varchar(20) DEFAULT NULL COMMENT '列类型',
                                    `java_type` varchar(20) DEFAULT NULL COMMENT 'JAVA类型',
                                    `java_field` varchar(100) DEFAULT NULL COMMENT 'JAVA字段名',
                                    `is_pk` tinyint(1) NOT NULL DEFAULT '0' COMMENT '主键字段（1是 0否）',
                                    `is_increment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自增字段（1是 0否）',
                                    `is_required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '必填字段（1是 0否）',
                                    `is_view` tinyint(1) NOT NULL DEFAULT '0' COMMENT '查看字段（1是 0否）',
                                    `is_insert` tinyint(1) NOT NULL DEFAULT '0' COMMENT '新增字段（1是 0否）',
                                    `is_edit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '编辑字段（1是 0否）',
                                    `is_list` tinyint(1) NOT NULL DEFAULT '0' COMMENT '列表字段（1是 0否）',
                                    `is_query` tinyint(1) NOT NULL DEFAULT '0' COMMENT '查询字段（1是 0否）',
                                    `is_unique` tinyint(1) NOT NULL DEFAULT '0' COMMENT '唯一字段（1是 0否）',
                                    `is_import` tinyint(1) NOT NULL DEFAULT '0' COMMENT '导入字段（1是 0否）',
                                    `is_export` tinyint(1) NOT NULL DEFAULT '0' COMMENT '导出字段（1是 0否）',
                                    `is_hide` tinyint(1) NOT NULL DEFAULT '0' COMMENT '隐藏字段（1是 0否）',
                                    `is_cover` tinyint(1) NOT NULL DEFAULT '0' COMMENT '覆盖字段（1是 0否）',
                                    `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
                                    `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
                                    `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
                                    `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                    `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_auth_group`
--

DROP TABLE IF EXISTS `sys_auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_auth_group` (
                                  `id` bigint NOT NULL COMMENT '权限组Id',
                                  `code` varchar(64) DEFAULT NULL COMMENT '权限组编码',
                                  `name` varchar(30) NOT NULL COMMENT '权限组名称',
                                  `auth_key` varchar(100) DEFAULT NULL COMMENT '权限组权限字符串',
                                  `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                  `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='租户权限组信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_auth_group`
--

LOCK TABLES `sys_auth_group` WRITE;
/*!40000 ALTER TABLE `sys_auth_group` DISABLE KEYS */;
INSERT INTO `sys_auth_group` VALUES (1,'001','默认权限组','A',0,'0','默认权限组',NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
                              `id` bigint NOT NULL COMMENT '参数Id',
                              `name` varchar(100) DEFAULT '' COMMENT '参数名称',
                              `code` varchar(100) DEFAULT '' COMMENT '参数编码',
                              `value` text COMMENT '参数键值',
                              `data_type` char(1) NOT NULL COMMENT '数据类型（0默认 1只读）',
                              `cache_type` char(1) NOT NULL COMMENT '缓存类型（0租户 1全局）',
                              `type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
                              `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                              `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                              `create_by` bigint DEFAULT NULL COMMENT '创建者',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_by` bigint DEFAULT NULL COMMENT '更新者',
                              `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','0','0','Y',0,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','0','0','Y',0,'初始化密码 123456',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','0','0','Y',0,'深色主题theme-dark，浅色主题theme-light',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(4,'账号自助-是否开启租户注册功能','sys.account.registerTenant','false','0','0','Y',0,'是否开启注册租户功能（true开启，false关闭）',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10001,'系统模块:数据编码配置:功能开关','sys.code.show','Y','0','0','Y',0,'系统模块:数据编码配置:功能开关（Y启用 N禁用）',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10002,'系统模块:数据编码配置:必须字段','sys.code.must','N','0','0','Y',0,'系统模块:数据编码配置:必须字段（Y是 N否）',NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
                            `id` bigint NOT NULL COMMENT '部门id',
                            `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
                            `code` varchar(64) DEFAULT NULL COMMENT '部门编码',
                            `name` varchar(30) DEFAULT '' COMMENT '部门名称',
                            `level` int NOT NULL COMMENT '树层级',
                            `ancestors` varchar(500) DEFAULT '' COMMENT '祖级列表',
                            `leader` varchar(20) DEFAULT '' COMMENT '负责人',
                            `phone` varchar(11) DEFAULT '' COMMENT '联系电话',
                            `email` varchar(50) DEFAULT '' COMMENT '邮箱',
                            `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (99,0,'99','雪忆科技',1,'0','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(100,0,'100','雪忆科技',1,'0','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(101,100,'101','深圳总公司',2,'0,100','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(102,100,'102','长沙分公司',2,'0,100','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(103,101,'103','研发部门',3,'0,100,101','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(104,101,'104','市场部门',3,'0,100,101','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(105,101,'105','测试部门',3,'0,100,101','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(106,101,'106','财务部门',3,'0,100,101','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(107,101,'107','运维部门',3,'0,100,101','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(108,102,'108','市场部门',3,'0,100,102','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(109,102,'109','财务部门',3,'0,100,102','','','',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
                                 `id` bigint NOT NULL COMMENT '数据Id',
                                 `code` varchar(100) DEFAULT '' COMMENT '字典编码',
                                 `value` varchar(100) DEFAULT '' COMMENT '数据键值',
                                 `label` varchar(100) DEFAULT '' COMMENT '数据标签',
                                 `additional_a` varchar(100) DEFAULT NULL COMMENT '附加数据1',
                                 `additional_b` varchar(100) DEFAULT NULL COMMENT '附加数据2',
                                 `additional_c` varchar(100) DEFAULT NULL COMMENT '附加数据3',
                                 `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                 `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
                                 `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
                                 `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
                                 `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                 `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                 `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                 `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1000101,'sys_user_sex','0','男',NULL,NULL,NULL,1,'Y','','','0','常规|性别：男',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000102,'sys_user_sex','1','女',NULL,NULL,NULL,2,'N','','','0','常规|性别：女',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000103,'sys_user_sex','2','未知',NULL,NULL,NULL,3,'N','','','0','常规|性别：未知',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000201,'sys_show_hide','0','显示',NULL,NULL,NULL,1,'Y','','blue','0','常规|显隐：显示',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000202,'sys_show_hide','1','隐藏',NULL,NULL,NULL,2,'N','','red','0','常规|显隐：隐藏',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000301,'sys_common_private','0','公共',NULL,NULL,NULL,1,'N','','cyan','0','常规|公共私有：公共',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000302,'sys_common_private','1','私有',NULL,NULL,NULL,2,'N','','purple','0','常规|公共私有：私有',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000401,'sys_normal_disable','0','正常',NULL,NULL,NULL,1,'Y','','blue','0','常规|状态：正常',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000402,'sys_normal_disable','1','停用',NULL,NULL,NULL,2,'N','','red','0','常规|状态：停用',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000501,'sys_job_status','0','正常',NULL,NULL,NULL,1,'Y','','blue','0','定时任务|任务状态：正常',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000502,'sys_job_status','1','暂停',NULL,NULL,NULL,2,'N','','red','0','定时任务|任务状态：停用',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000601,'sys_job_policy','0','默认',NULL,NULL,NULL,1,'N','','blue','0','定时任务|任务策略：默认',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000602,'sys_job_policy','1','立即执行',NULL,NULL,NULL,2,'N','','green','0','定时任务|任务策略：立即执行',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000603,'sys_job_policy','2','执行一次',NULL,NULL,NULL,3,'N','','cyan','0','定时任务|任务策略：执行一次',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000604,'sys_job_policy','3','放弃执行',NULL,NULL,NULL,4,'N','','purple','0','定时任务|任务策略：放弃执行',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000701,'sys_job_concurrent','0','允许',NULL,NULL,NULL,1,'N','','blue','0','定时任务|任务并发：允许',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000702,'sys_job_concurrent','1','禁止',NULL,NULL,NULL,2,'N','','red','0','定时任务|任务并发：禁止',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000801,'sys_job_group','DEFAULT','默认',NULL,NULL,NULL,1,'Y','','','0','定时任务|任务分组：默认',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000802,'sys_job_group','SYSTEM','系统',NULL,NULL,NULL,2,'N','','','0','定时任务|任务分组：系统',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000901,'sys_yes_no','Y','是',NULL,NULL,NULL,1,'Y','','blue','0','常规|是否：是',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1000902,'sys_yes_no','N','否',NULL,NULL,NULL,2,'N','','red','0','常规|是否：否',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001001,'sys_notice_type','0','通知',NULL,NULL,NULL,1,'Y','','blue','0','系统服务|消息模块|通知类型：通知',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001002,'sys_notice_type','1','公告',NULL,NULL,NULL,2,'N','','green','0','系统服务|消息模块|通知类型：公告',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001101,'sys_notice_status','0','待发送',NULL,NULL,NULL,1,'Y','','blue','0','系统服务|消息模块|通知状态：待发送',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001102,'sys_notice_status','1','已发送',NULL,NULL,NULL,2,'N','','green','0','系统服务|消息模块|通知状态：已发送',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001103,'sys_notice_status','2','已关闭',NULL,NULL,NULL,3,'N','','cyan','0','系统服务|消息模块|通知状态：已关闭',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001104,'sys_notice_status','3','发送失败',NULL,NULL,NULL,4,'N','','orange','0','系统服务|消息模块|通知状态：发送失败',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001105,'sys_notice_status','4','发送异常',NULL,NULL,NULL,5,'N','','red','0','系统服务|消息模块|通知状态：发送异常',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001201,'sys_operate_business','00','其它',NULL,NULL,NULL,1,'N','','orange','0','系统服务|监控模块|操作类型：其它',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001202,'sys_operate_business','01','新增',NULL,NULL,NULL,2,'N','','blue','0','系统服务|监控模块|操作类型：新增',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001203,'sys_operate_business','02','强制新增',NULL,NULL,NULL,3,'N','','red','0','系统服务|监控模块|操作类型：强制新增',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001204,'sys_operate_business','03','修改',NULL,NULL,NULL,4,'N','','green','0','系统服务|监控模块|操作类型：修改',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001205,'sys_operate_business','04','强制修改',NULL,NULL,NULL,5,'N','','red','0','系统服务|监控模块|操作类型：强制修改',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001206,'sys_operate_business','05','权限控制',NULL,NULL,NULL,6,'N','','purple','0','系统服务|监控模块|操作类型：权限控制',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001207,'sys_operate_business','06','修改状态',NULL,NULL,NULL,7,'N','','purple','0','系统服务|监控模块|操作类型：修改状态',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001208,'sys_operate_business','07','强制修改状态',NULL,NULL,NULL,8,'N','','red','0','系统服务|监控模块|操作类型：强制修改状态',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001209,'sys_operate_business','08','删除',NULL,NULL,NULL,9,'N','','orange','0','系统服务|监控模块|操作类型：删除',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001210,'sys_operate_business','09','强制删除',NULL,NULL,NULL,10,'N','','red','0','系统服务|监控模块|操作类型：强制删除',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001211,'sys_operate_business','10','授权',NULL,NULL,NULL,11,'N','','orange','0','系统服务|监控模块|操作类型：授权',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001212,'sys_operate_business','11','导出',NULL,NULL,NULL,12,'N','','pink','0','系统服务|监控模块|操作类型：导出',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001213,'sys_operate_business','12','导入',NULL,NULL,NULL,13,'N','','cyan','0','系统服务|监控模块|操作类型：导入',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001214,'sys_operate_business','13','强退',NULL,NULL,NULL,14,'N','','orange','0','系统服务|监控模块|操作类型：强退',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001215,'sys_operate_business','14','生成代码',NULL,NULL,NULL,15,'N','','orange','0','系统服务|监控模块|操作类型：生成代码',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001216,'sys_operate_business','15','清空数据',NULL,NULL,NULL,16,'N','','orange','0','系统服务|监控模块|操作类型：清空数据',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001217,'sys_operate_business','16','更新缓存',NULL,NULL,NULL,17,'N','','orange','0','系统服务|监控模块|操作类型：更新缓存',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001301,'sys_operate_type','00','其它',NULL,NULL,NULL,1,'N','','green','0','系统服务|监控模块|操作用户类别：其它',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001302,'sys_operate_type','01','后台用户',NULL,NULL,NULL,2,'N','','blue','0','系统服务|监控模块|操作用户类别：后台用户',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001303,'sys_operate_type','02','手机端用户',NULL,NULL,NULL,3,'N','','red','0','系统服务|监控模块|操作用户类别：手机端用户',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001401,'sys_operate_status','0','正常',NULL,NULL,NULL,1,'N','','green','0','系统服务|监控模块|操作日志状态：正常',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001402,'sys_operate_status','1','异常',NULL,NULL,NULL,2,'N','','red','0','系统服务|监控模块|操作日志状态：异常',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001501,'sys_message_status','0','成功',NULL,NULL,NULL,1,'N','','blue','0','常规|消息状态：成功',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001502,'sys_message_status','1','失败',NULL,NULL,NULL,2,'N','','red','0','常规|消息状态：失败',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001601,'sys_dict_color','','none',NULL,NULL,NULL,1,'N','','','0','系统服务|字典模块|字典颜色：none',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001602,'sys_dict_color','red','red',NULL,NULL,NULL,2,'N','','red','0','系统服务|字典模块|字典颜色：red',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001603,'sys_dict_color','pink','pink',NULL,NULL,NULL,3,'N','','pink','0','系统服务|字典模块|字典颜色：pink',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001604,'sys_dict_color','orange','orange',NULL,NULL,NULL,4,'N','','orange','0','系统服务|字典模块|字典颜色：orange',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001605,'sys_dict_color','green','green',NULL,NULL,NULL,5,'N','','green','0','系统服务|字典模块|字典颜色：green',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001606,'sys_dict_color','blue','blue',NULL,NULL,NULL,6,'N','','blue','0','系统服务|字典模块|字典颜色：blue',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001607,'sys_dict_color','purple','purple',NULL,NULL,NULL,7,'N','','purple','0','系统服务|字典模块|字典颜色：purple',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001608,'sys_dict_color','cyan','cyan',NULL,NULL,NULL,8,'N','','cyan','0','系统服务|字典模块|字典颜色：cyan',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001701,'sys_grant_type','authorization_code','授权码模式',NULL,NULL,NULL,1,'N','','','0','系统服务|权限模块|授权类型：授权码模式',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001702,'sys_grant_type','password','密码模式',NULL,NULL,NULL,2,'N','','','0','系统服务|权限模块|授权类型：密码模式',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001703,'sys_grant_type','client_credentials','客户端模式',NULL,NULL,NULL,3,'N','','','0','系统服务|权限模块|授权类型：客户端模式',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001704,'sys_grant_type','implicit','简化模式',NULL,NULL,NULL,4,'N','','','0','系统服务|权限模块|授权类型：简化模式',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001705,'sys_grant_type','refresh_token','刷新模式',NULL,NULL,NULL,5,'N','','','0','系统服务|权限模块|授权类型：刷新模式',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001801,'auth_frame_type','0','常规',NULL,NULL,NULL,1,'Y','','pink','0','系统服务|权限模块|页面类型：常规',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001802,'auth_frame_type','1','内嵌',NULL,NULL,NULL,2,'N','','cyan','0','系统服务|权限模块|页面类型：内嵌',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001803,'auth_frame_type','2','外链',NULL,NULL,NULL,3,'N','','green','0','系统服务|权限模块|页面类型：外链',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001901,'auth_menu_type','M','目录',NULL,NULL,NULL,1,'Y','','pink','0','系统服务|权限模块|菜单类型：目录',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001902,'auth_menu_type','C','菜单',NULL,NULL,NULL,2,'N','','cyan','0','系统服务|权限模块|菜单类型：菜单',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001903,'auth_menu_type','X','详情',NULL,NULL,NULL,3,'N','','purple','0','系统服务|权限模块|菜单类型：详情',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1001904,'auth_menu_type','F','按钮',NULL,NULL,NULL,4,'N','','green','0','系统服务|权限模块|菜单类型：按钮',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002001,'auth_data_scope','1','全部数据权限',NULL,NULL,NULL,1,'Y','','','0','系统服务|权限模块|数据范围：全部数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002002,'auth_data_scope','2','自定数据权限',NULL,NULL,NULL,2,'N','','','0','系统服务|权限模块|数据范围：自定数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002003,'auth_data_scope','3','本部门数据权限',NULL,NULL,NULL,3,'N','','','0','系统服务|权限模块|数据范围：本部门数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002004,'auth_data_scope','4','本部门及以下数据权限',NULL,NULL,NULL,4,'N','','','0','系统服务|权限模块|数据范围：本部门及以下数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002005,'auth_data_scope','5','本岗位数据权限',NULL,NULL,NULL,5,'N','','','0','系统服务|权限模块|数据范围：本岗位数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002006,'auth_data_scope','6','仅本人数据权限',NULL,NULL,NULL,6,'N','','','0','系统服务|权限模块|数据范围：仅本人数据权限',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002101,'sys_source_type','0','读&写',NULL,NULL,NULL,1,'Y','','blue','0','代码生成|读写类型：读&写',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002102,'sys_source_type','1','只读',NULL,NULL,NULL,2,'N','','green','0','代码生成|读写类型：只读',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002103,'sys_source_type','2','只写',NULL,NULL,NULL,3,'N','','red','0','代码生成|读写类型：只写',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002201,'sys_database_type','0','子数据源',NULL,NULL,NULL,1,'Y','','green','0','代码生成|数据源类型：子数据源',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002202,'sys_database_type','1','主数据源',NULL,NULL,NULL,2,'N','','red','0','代码生成|数据源类型：主数据源',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002301,'te_configuration_type','0','自动配置',NULL,NULL,NULL,1,'N','','','0','代码生成|配置类型：自动配置',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002302,'te_configuration_type','1','手动配置',NULL,NULL,NULL,2,'N','','','0','代码生成|配置类型：手动配置',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002401,'gen_template_type','base','单表',NULL,NULL,NULL,1,'Y','','','0','代码生成|模板类型：单表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002402,'gen_template_type','tree','树表',NULL,NULL,NULL,2,'N','','','0','代码生成|模板类型：树表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002403,'gen_template_type','merge','关联表',NULL,NULL,NULL,3,'N','','','0','代码生成|模板类型：关联表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002501,'gen_java_type','Long','Long',NULL,NULL,NULL,1,'N','','','0','代码生成|属性类型：Long',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002502,'gen_java_type','String','String',NULL,NULL,NULL,2,'N','','','0','代码生成|属性类型：String',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002503,'gen_java_type','Integer','Integer',NULL,NULL,NULL,3,'N','','','0','代码生成|属性类型：Integer',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002504,'gen_java_type','Double','Double',NULL,NULL,NULL,4,'N','','','0','代码生成|属性类型：Double',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002505,'gen_java_type','BigDecimal','BigDecimal',NULL,NULL,NULL,5,'N','','','0','代码生成|属性类型：BigDecimal',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002506,'gen_java_type','LocalDateTime','LocalDateTime',NULL,NULL,NULL,6,'N','','','0','代码生成|属性类型：LocalDateTime',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002601,'gen_query_type','eq','=',NULL,NULL,NULL,1,'Y','','','0','代码生成|查询类型：=',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002602,'gen_query_type','ne','!=',NULL,NULL,NULL,2,'N','','','0','代码生成|查询类型：!=',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002603,'gen_query_type','gt','>',NULL,NULL,NULL,3,'N','','','0','代码生成|查询类型：>',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002604,'gen_query_type','ge','>=',NULL,NULL,NULL,4,'N','','','0','代码生成|查询类型：>=',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002605,'gen_query_type','lt','<',NULL,NULL,NULL,5,'N','','','0','代码生成|查询类型：<',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002606,'gen_query_type','le','<=',NULL,NULL,NULL,6,'N','','','0','代码生成|查询类型：<=',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002607,'gen_query_type','like','LIKE',NULL,NULL,NULL,7,'N','','','0','代码生成|查询类型：LIKE',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002608,'gen_query_type','notLike','NOT_LIKE',NULL,NULL,NULL,8,'N','','','0','代码生成|查询类型：NOT_LIKE',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002609,'gen_query_type','likeLeft','LIKE_LEFT',NULL,NULL,NULL,9,'N','','','0','代码生成|查询类型：LIKE_LEFT',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002610,'gen_query_type','likeRight','LIKE_RIGHT',NULL,NULL,NULL,10,'N','','','0','代码生成|查询类型：LIKE_RIGHT',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002611,'gen_query_type','between','BETWEEN',NULL,NULL,NULL,11,'N','','','0','代码生成|查询类型：BETWEEN',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002612,'gen_query_type','notBetween','NOT_BETWEEN',NULL,NULL,NULL,12,'N','','','0','代码生成|查询类型：NOT_BETWEEN',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002613,'gen_query_type','isNull','ISNULL',NULL,NULL,NULL,13,'N','','','0','代码生成|查询类型：ISNULL',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002614,'gen_query_type','isNotNull','IS_NOT_NULL',NULL,NULL,NULL,14,'N','','','0','代码生成|查询类型：IS_NOT_NULL',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002701,'gen_display_type','Input','文本框',NULL,NULL,NULL,1,'Y','','','0','代码生成|显示类型：文本框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002702,'gen_display_type','InputPassword','密码框',NULL,NULL,NULL,2,'Y','','','0','代码生成|显示类型：密码框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002703,'gen_display_type','InputNumber','数字输入框',NULL,NULL,NULL,3,'Y','','','0','代码生成|显示类型：数字输入框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002704,'gen_display_type','InputTextArea','文本域',NULL,NULL,NULL,4,'N','','','0','代码生成|显示类型：文本域',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002705,'gen_display_type','Select','下拉框',NULL,NULL,NULL,5,'N','','','0','代码生成|显示类型：下拉框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002706,'gen_display_type','CheckboxGroup','单选框',NULL,NULL,NULL,6,'N','','','0','代码生成|显示类型：单选框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002707,'gen_display_type','RadioButtonGroup','按钮风格单选框',NULL,NULL,NULL,7,'N','','','0','代码生成|显示类型：按钮风格单选框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002708,'gen_display_type','RadioGroup','复选框',NULL,NULL,NULL,8,'N','','','0','代码生成|显示类型：复选框',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002709,'gen_display_type','DatePicker','日期控件',NULL,NULL,NULL,9,'N','','','0','代码生成|显示类型：日期控件',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002710,'gen_display_type','TimePicker','时间控件',NULL,NULL,NULL,10,'N','','','0','代码生成|显示类型：时间控件',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002711,'gen_display_type','ImageUpload','图片上传',NULL,NULL,NULL,11,'N','','','0','代码生成|显示类型：图片上传',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002712,'gen_display_type','Upload','文件上传',NULL,NULL,NULL,12,'N','','','0','代码生成|显示类型：文件上传',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002713,'gen_display_type','tinymce','富文本控件',NULL,NULL,NULL,13,'N','','','0','代码生成|显示类型：富文本控件',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002714,'gen_display_type','markdown','MarkDown编辑器控件',NULL,NULL,NULL,14,'N','','','0','代码生成|显示类型：MarkDown编辑器控件',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002801,'gen_generation_mode','0','默认路径',NULL,NULL,NULL,1,'Y','','','0','代码生成|生成路径：默认路径',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002802,'gen_generation_mode','1','自定义路径',NULL,NULL,NULL,2,'N','','','0','代码生成|生成路径：自定义路径',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002901,'gen_source_mode','Isolate','策略源',NULL,NULL,NULL,1,'Y','','','0','代码生成|源策略模式：策略源',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1002902,'gen_source_mode','Master','主数据源',NULL,NULL,NULL,2,'N','','','0','代码生成|源策略模式：主数据源',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003001,'sys_dict_data_type','0','默认',NULL,NULL,NULL,1,'N','','blue','0','系统服务|字典模块|数据类型：默认',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003002,'sys_dict_data_type','1','只增',NULL,NULL,NULL,2,'N','','green','0','系统服务|字典模块|数据类型：只增',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003003,'sys_dict_data_type','2','只减',NULL,NULL,NULL,3,'N','','cyan','0','系统服务|字典模块|数据类型：只减',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003004,'sys_dict_data_type','3','只读',NULL,NULL,NULL,4,'N','','purple','0','系统服务|字典模块|数据类型：只读',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003101,'sys_dict_cache_type','0','租户',NULL,NULL,NULL,1,'N','','purple','0','系统服务|字典模块|缓存类型：租户',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003102,'sys_dict_cache_type','1','全局',NULL,NULL,NULL,2,'N','','blue','0','系统服务|字典模块|缓存类型：全局',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003201,'sys_function_status','Y','开启',NULL,NULL,NULL,1,'N','','blue','0','常规|功能状态: 开启',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003202,'sys_function_status','N','关闭',NULL,NULL,NULL,2,'Y','','orange','0','常规|功能状态: 关闭',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(1003301,'te_strategy_source_type','sourceType1','业务源A','N',NULL,NULL,1,'N','','blue','0','租户服务|策略模块|源策略|策略类型：业务源A（非必填）',NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
                                 `id` bigint NOT NULL COMMENT '字典主键',
                                 `name` varchar(100) DEFAULT '' COMMENT '字典名称',
                                 `code` varchar(100) DEFAULT '' COMMENT '字典类型',
                                 `data_type` char(1) NOT NULL COMMENT '数据类型（0默认 1只增 2只减 3只读）',
                                 `cache_type` char(1) NOT NULL COMMENT '缓存类型（0租户 1全局）',
                                 `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                 `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                 `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                 `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                 `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (10001,'常规|性别','sys_user_sex','0','1',0,'0','常规|性别列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10002,'常规|显隐','sys_show_hide','0','1',0,'0','常规|显隐列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10003,'常规|公共私有','sys_common_private','0','1',0,'0','常规|公共私有列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10004,'常规|状态','sys_normal_disable','0','1',0,'0','常规|状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10005,'定时任务|任务状态','sys_job_status','0','1',0,'0','定时任务|任务状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10006,'定时任务|任务策略','sys_job_policy','0','1',0,'0','定时任务|任务策略列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10007,'定时任务|任务并发','sys_job_concurrent','0','1',0,'0','定时任务|任务并发列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10008,'定时任务|任务分组','sys_job_group','0','1',0,'0','定时任务|任务分组列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10009,'常规|是否','sys_yes_no','0','1',0,'0','常规|是否列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10010,'系统服务|消息模块|通知类型','sys_notice_type','0','1',0,'0','系统服务|消息模块|通知类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10011,'系统服务|消息模块|通知状态','sys_notice_status','0','1',0,'0','系统服务|消息模块|通知状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10012,'系统服务|监控模块|操作类型','sys_operate_business','0','1',0,'0','系统服务|监控模块|操作类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10013,'系统服务|监控模块|操作用户类别','sys_operate_type','0','1',0,'0','系统服务|监控模块|操作用户类别列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10014,'系统服务|监控模块|操作日志状态','sys_operate_status','0','1',0,'0','系统服务|监控模块|操作日志状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10015,'常规|消息状态','sys_message_status','0','1',0,'0','常规|消息状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10016,'系统服务|字典模块|字典颜色','sys_dict_color','0','1',0,'0','系统服务|字典模块|字典颜色列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10017,'系统服务|权限模块|授权类型','sys_grant_type','0','1',0,'0','系统服务|权限模块|授权类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10018,'系统服务|权限模块|页面类型','auth_frame_type','0','1',0,'0','系统服务|权限模块|页面类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10019,'系统服务|权限模块|菜单类型','auth_menu_type','0','1',0,'0','系统服务|权限模块|菜单类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10020,'系统服务|权限模块|数据范围','auth_data_scope','0','1',0,'0','系统服务|权限模块|数据范围列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10021,'代码生成|读写类型','sys_source_type','0','1',0,'0','代码生成|读写类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10022,'代码生成|数据源类型','sys_database_type','0','1',0,'0','代码生成|数据源类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10023,'代码生成|配置类型','te_configuration_type','0','1',0,'0','代码生成|配置类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10024,'代码生成|模板类型','gen_template_type','0','1',0,'0','代码生成|模板类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10025,'代码生成|属性类型','gen_java_type','0','1',0,'0','代码生成|属性类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10026,'代码生成|查询类型','gen_query_type','0','1',0,'0','代码生成|查询类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10027,'代码生成|显示类型','gen_display_type','0','1',0,'0','代码生成|显示类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10028,'代码生成|生成路径','gen_generation_mode','0','1',0,'0','代码生成|生成路径列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10029,'代码生成|源策略模式','gen_source_mode','0','1',0,'0','代码生成|源策略模式列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10030,'系统服务|字典模块|数据类型','sys_dict_data_type','0','1',0,'0','系统服务|字典模块|数据类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10031,'系统服务|字典模块|缓存类型','sys_dict_cache_type','0','1',0,'0','系统服务|字典模块|缓存类型列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10032,'常规|功能状态','sys_function_status','3','1',0,'0','常规|功能状态列表',NULL,'2024-09-15 18:11:54',NULL,NULL,0),(10033,'租户服务|策略模块|源策略|策略类型','te_strategy_source_type','3','1',0,'0','租户服务|策略模块|源策略|策略类型列表（additional_a控制是否必填（Y必填/N非必填））',NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file` (
                            `id` bigint NOT NULL COMMENT '文件Id',
                            `folder_id` bigint NOT NULL DEFAULT '0' COMMENT '分类Id',
                            `name` varchar(100) NOT NULL COMMENT '文件名称',
                            `nick` varchar(100) DEFAULT NULL COMMENT '文件别名',
                            `url` varchar(500) NOT NULL COMMENT '文件网址',
                            `path` varchar(500) NOT NULL COMMENT '存储路径',
                            `size` bigint NOT NULL DEFAULT '0' COMMENT '文件大小',
                            `type` char(1) NOT NULL DEFAULT '0' COMMENT '文件类型（0默认 1系统）',
                            `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file_folder`
--

DROP TABLE IF EXISTS `sys_file_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file_folder` (
                                   `id` bigint NOT NULL COMMENT '分类Id',
                                   `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父分类Id',
                                   `name` varchar(100) NOT NULL COMMENT '分类名称',
                                   `level` int NOT NULL COMMENT '树层级',
                                   `ancestors` varchar(500) DEFAULT '' COMMENT '祖级列表',
                                   `type` char(1) NOT NULL DEFAULT '0' COMMENT '分类类型（0默认文件夹 1系统文件夹）',
                                   `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                   `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                   `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                   `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件分类信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file_folder`
--

LOCK TABLES `sys_file_folder` WRITE;
/*!40000 ALTER TABLE `sys_file_folder` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_im_ex`
--

DROP TABLE IF EXISTS `sys_im_ex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_im_ex` (
                             `id` bigint NOT NULL COMMENT '配置Id',
                             `code` varchar(100) NOT NULL COMMENT '配置编码',
                             `name` varchar(100) NOT NULL COMMENT '配置名称',
                             `import_config` text COMMENT '导入配置',
                             `export_config` text COMMENT '导出配置',
                             `data_type` char(1) NOT NULL COMMENT '数据类型（0默认 1只读）',
                             `cache_type` char(1) NOT NULL COMMENT '缓存类型（0租户 1全局）',
                             `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                             `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                             `create_by` bigint DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_by` bigint DEFAULT NULL COMMENT '更新者',
                             `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='导入导出配置信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_im_ex`
--

LOCK TABLES `sys_im_ex` WRITE;
/*!40000 ALTER TABLE `sys_im_ex` DISABLE KEYS */;
INSERT INTO `sys_im_ex` VALUES (1,'IE0001','导入导出配置demo','{	\"sheetName\": \"数据表\",	\"fieldList\": [		{			\"name\": \"资产编号\",			\"sort\": 1,			\"field\": \"assetCode\"		},		{			\"name\": \"财务编码\",			\"sort\": 2,			\"field\": \"financeCode\"		},		{			\"name\": \"设备名称\",			\"sort\": 3,			\"field\": \"assetName\"		},		{			\"name\": \"使用部门\",			\"sort\": 4,			\"field\": \"deptName\"		},		{			\"name\": \"使用年限\",			\"sort\": 5,			\"field\": \"assetLife\"		},		{			\"name\": \"建筑面积或工程量\",			\"sort\": 6,			\"field\": \"cusKey9\"		},		{			\"name\": \"施工单位\",			\"sort\": 7,			\"field\": \"cusKey3\"		},		{			\"name\": \"出厂年月\",			\"sort\": 8,			\"field\": \"manufactureTime\"		},		{			\"name\": \"结构形式\",			\"sort\": 9,			\"field\": \"cusKey11\"		},		{			\"name\": \"建设地点\",			\"sort\": 10,			\"field\": \"cusKey8\"		},		{			\"name\": \"立卡年月\",			\"sort\": 11,			\"field\": \"tentCardTime\"		},		{			\"name\": \"开工日期\",			\"sort\": 12,			\"field\": \"cusKey10\"		},		{			\"name\": \"竣工日期\",			\"sort\": 13,			\"field\": \"cusKey5\"		},		{			\"name\": \"原值(元)\",			\"sort\": 14,			\"field\": \"currentPrice\"		},		{			\"name\": \"当前管理状态\",			\"sort\": 15,			\"field\": \"status\"		},		{			\"name\": \"外形尺寸\",			\"sort\": 16,			\"field\": \"cusKey7\"		},		{			\"name\": \"随机附件\",			\"sort\": 17,			\"field\": \"cusKey12\"		},		{			\"name\": \"备注\",			\"sort\": 18,			\"field\": \"remark\"		},		{			\"name\": \"财务账上编号\",			\"sort\": 19,			\"field\": \"cusKey13\"		},		{			\"name\": \"报废日期\",			\"sort\": 20,			\"field\": \"realityScrapTime\"		}	]}','{	\"sheetName\": \"数据表\",	\"fieldList\": [		{			\"name\": \"变更前价值\",			\"sort\": 1,			\"field\": \"beforePrice\"		},		{			\"name\": \"变更价值\",			\"sort\": 1,			\"field\": \"modifyPrice\"		},		{			\"name\": \"变更后价值\",			\"sort\": 1,			\"field\": \"afterPrice\"		},		{			\"field\": \"assetInfo\",			\"children\": [				{					\"name\": \"资产编号\",					\"sort\": 1,					\"field\": \"assetCode\"				}			]		}	]}','3','1',0,NULL,NULL,'2024-01-23 21:04:08',NULL,'2024-01-25 11:02:49',0);
/*!40000 ALTER TABLE `sys_im_ex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
                           `id` bigint NOT NULL COMMENT '任务Id',
                           `name` varchar(64) DEFAULT '' COMMENT '任务名称',
                           `job_group` varchar(64) DEFAULT 'DEFAULT' COMMENT '任务组名',
                           `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
                           `invoke_tenant` varchar(500) NOT NULL COMMENT '调用租户字符串',
                           `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
                           `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（0默认 1立即执行 2执行一次 3放弃执行）',
                           `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
                           `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
                           `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                           `create_by` bigint DEFAULT NULL COMMENT '创建者',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_by` bigint DEFAULT NULL COMMENT '更新者',
                           `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','1L, \'Y\', \'slave\'','0/10 * * * * ?','3','1','1',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','1L, \'Y\', \'slave\'','0/15 * * * * ?','3','1','1',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','1L, \'Y\', \'slave\'','0/20 * * * * ?','3','1','1',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志Id',
                               `job_id` bigint NOT NULL COMMENT '任务Id',
                               `name` varchar(64) NOT NULL COMMENT '任务名称',
                               `job_group` varchar(64) NOT NULL COMMENT '任务组名',
                               `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
                               `invoke_tenant` varchar(500) NOT NULL COMMENT '调用租户字符串',
                               `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
                               `status` char(1) NOT NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
                               `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `del_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
                               `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_login_log`
--

DROP TABLE IF EXISTS `sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_log` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问Id',
                                 `enterprise_name` varchar(50) DEFAULT '' COMMENT '企业账号',
                                 `user_id` bigint NOT NULL DEFAULT '-2' COMMENT '用户Id',
                                 `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
                                 `user_nick` varchar(50) DEFAULT '' COMMENT '用户名称',
                                 `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
                                 `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
                                 `msg` varchar(255) DEFAULT '' COMMENT '提示信息',
                                 `access_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
                                 `del_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
                                 `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_sys_login_log_s` (`status`),
                                 KEY `idx_sys_login_log_lt` (`access_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1835280165299564545 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

LOCK TABLES `sys_login_log` WRITE;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` VALUES (1835270138018934784,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:51:13',NULL,0),(1835270138253815808,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:51:13',NULL,0),(1835270237507825664,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:51:36',NULL,0),(1835270237696569344,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:51:36',NULL,0),(1835270304797044736,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:51:52',NULL,0),(1835270304931262464,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:51:53',NULL,0),(1835270390079827968,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:52:13',NULL,0),(1835270390293737472,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:52:13',NULL,0),(1835271068131012608,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:54:54',NULL,0),(1835271069108285440,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:54:55',NULL,0),(1835271633879707648,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:57:09',NULL,0),(1835271634534019072,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:57:10',NULL,0),(1835271726280224768,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 18:57:31',NULL,0),(1835271726607380480,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 18:57:31',NULL,0),(1835273618297204736,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:05:02',NULL,0),(1835273882907455488,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:06:06',NULL,0),(1835274158947184640,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:07:11',NULL,0),(1835274230246158336,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:07:28',NULL,0),(1835274333119852544,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:07:53',NULL,0),(1835274712280739840,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:09:23',NULL,0),(1835275658121457664,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:13:09',NULL,0),(1835275658389893120,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:13:09',NULL,0),(1835275800698433536,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:13:43',NULL,0),(1835275800832651264,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:13:43',NULL,0),(1835276898091937792,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:18:04',NULL,0),(1835277180553146368,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:19:12',NULL,0),(1835277663455948800,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:21:07',NULL,0),(1835278133683564544,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:22:59',NULL,0),(1835278243565940736,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:23:25',NULL,0),(1835279002357477376,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:26:26',NULL,0),(1835279813120634880,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:29:39',NULL,0),(1835279846272413696,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:29:47',NULL,0),(1835279965638111232,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:30:16',NULL,0),(1835279991915425792,'',1,'admin','admin','127.0.0.1','0','登录成功','2024-09-15 19:30:22',NULL,0),(1835280165299564544,'',1,'admin','admin','127.0.0.1','0','退出成功','2024-09-15 19:31:03',NULL,0);
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
                            `id` bigint NOT NULL COMMENT '菜单Id',
                            `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单Id',
                            `name` varchar(100) NOT NULL COMMENT '菜单名称',
                            `title` varchar(50) NOT NULL COMMENT '菜单标题 | 多语言',
                            `level` int NOT NULL COMMENT '树层级',
                            `ancestors` varchar(500) DEFAULT '' COMMENT '祖级列表',
                            `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
                            `frame_src` varchar(200) DEFAULT NULL COMMENT '外链地址 | 仅页面类型为外链时生效',
                            `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
                            `param_path` varchar(255) DEFAULT NULL COMMENT '路由参数',
                            `transition_name` varchar(255) DEFAULT NULL COMMENT '路由切换动画',
                            `ignore_route` char(1) NOT NULL DEFAULT 'N' COMMENT '是否忽略路由（Y是 N否）',
                            `is_cache` char(1) NOT NULL DEFAULT 'N' COMMENT '是否缓存（Y是 N否）',
                            `is_affix` char(1) NOT NULL DEFAULT 'N' COMMENT '是否固定标签（Y是 N否）',
                            `is_disabled` char(1) NOT NULL DEFAULT 'N' COMMENT '是否禁用（Y是 N否）',
                            `frame_type` char(1) NOT NULL DEFAULT '0' COMMENT '页面类型（0常规 1内嵌 2外链）',
                            `menu_type` char(1) NOT NULL COMMENT '菜单类型（M目录 C菜单 X详情 F按钮）',
                            `hide_tab` char(1) NOT NULL DEFAULT '0' COMMENT '标签显隐状态（0显示 1隐藏）',
                            `hide_menu` char(1) NOT NULL DEFAULT '0' COMMENT '菜单显隐状态（0显示 1隐藏）',
                            `hide_breadcrumb` char(1) NOT NULL DEFAULT '0' COMMENT '面包屑路由显隐状态（0显示 1隐藏）',
                            `hide_children` char(1) NOT NULL DEFAULT '0' COMMENT '子菜单显隐状态（0显示 1隐藏）',
                            `hide_path_for_children` char(1) NOT NULL DEFAULT '0' COMMENT '是否在子级菜单的完整path中忽略本级path（0显示 1隐藏）',
                            `dynamic_level` int NOT NULL DEFAULT '1' COMMENT '详情页可打开Tab页数',
                            `real_path` varchar(255) DEFAULT NULL COMMENT '详情页的实际Path',
                            `perms` varchar(255) DEFAULT NULL COMMENT '权限标识',
                            `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
                            `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_common` char(1) NOT NULL DEFAULT '1' COMMENT '公共菜单（0公共 1私有）',
                            `is_default` char(1) NOT NULL DEFAULT 'N' COMMENT '默认菜单（Y是 N否）',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                            `module_id` bigint NOT NULL COMMENT '模块Id',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (13000000,0,'4be0456e05a7422d9f1c82fb7bf19377','组织管理',1,'0','organize',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:apartment-outlined',1,'0','目录:组织管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010000,13000000,'0a612ebdfaa64ea4b8fd8cfd787042ea','部门管理',2,'0,13000000','dept',NULL,'system/organize/dept/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:organize:dept:list, RD:system:organize:dept:list','ant-design:partition-outlined',1,'0','菜单:系统服务 | 组织模块 | 部门管理 | 部门管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010100,13010000,'c67c9520af2c48d89e1bae0d2a0342d1','部门详情',3,'0,13000000,13010000','deptDetail/:id',NULL,'system/organize/dept/DeptDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:organize:dept:single, RD:system:organize:dept:single',NULL,1,'0','详情:系统服务 | 组织模块 | 部门管理 | 部门详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010200,13010000,'5e50660b195a49c4860607aec14fc330','部门新增',3,'0,13000000,13010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:dept:add, RD:system:organize:dept:add',NULL,2,'0','按钮:系统服务 | 组织模块 | 部门管理 | 部门新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010300,13010000,'ad0a108f2c35433daf1864cf4cf93b64','部门修改',3,'0,13000000,13010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:dept:edit, RD:system:organize:dept:edit, RD:system:organize:dept:single',NULL,3,'0','按钮:系统服务 | 组织模块 | 部门管理 | 部门修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010400,13010000,'df7b77ef91c8433f97a9848ebe9b714b','部门状态修改',3,'0,13000000,13010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:dept:es, RD:system:organize:dept:es',NULL,3,'0','按钮:系统服务 | 组织模块 | 部门管理 | 部门状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010500,13010000,'a4c9cb1f239744b6bc7e6c5f11ba3268','部门权限分配',3,'0,13000000,13010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:dept:auth, RD:system:organize:dept:auth, RD:system:authority:role:list',NULL,5,'0','按钮:系统服务 | 组织模块 | 部门管理 | 部门权限分配',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13010600,13010000,'83a7e4bafb154a938b2c67d7f3e540d5','部门删除',3,'0,13000000,13010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:dept:del, RD:system:organize:dept:del',NULL,6,'0','按钮:系统服务 | 组织模块 | 部门管理 | 部门删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020000,13000000,'ecdcabe9c00a408796626a83d0129fdb','岗位管理',2,'0,13000000','post',NULL,'system/organize/post/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:organize:post:list, RD:system:organize:post:list, RD:system:organize:dept:list','ant-design:idcard-outlined',2,'0','菜单:系统服务 | 组织模块 | 岗位管理 | 岗位管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020100,13020000,'270eab9abe6a4e58b8c06d1339b46009','岗位详情',3,'0,13000000,13020000','postDetail/:id',NULL,'system/organize/post/PostDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:organize:post:single, RD:system:organize:post:single',NULL,1,'0','详情:系统服务 | 组织模块 | 岗位管理 | 岗位详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020200,13020000,'5ee95c2e5af8481ca6346faa82974251','岗位新增',3,'0,13000000,13020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:post:add, RD:system:organize:post:add, RD:system:organize:dept:list',NULL,3,'0','按钮:系统服务 | 组织模块 | 岗位管理 | 岗位新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020300,13020000,'082b9043d201490badbb403e2de9d0e1','岗位修改',3,'0,13000000,13020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:post:edit, RD:system:organize:post:edit, RD:system:organize:post:single, RD:system:organize:dept:list',NULL,4,'0','按钮:系统服务 | 组织模块 | 岗位管理 | 岗位修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020400,13020000,'6a9ffc830afd4f3d8f74a047d0619e0f','岗位状态修改',3,'0,13000000,13020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:post:es, RD:system:organize:post:es',NULL,5,'0','按钮:系统服务 | 组织模块 | 岗位管理 | 岗位状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020500,13020000,'1af7167de8ba4f819149956fe5f166e9','岗位权限分配',3,'0,13000000,13020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:post:auth, RD:system:organize:post:auth, RD:system:authority:role:list',NULL,5,'0','按钮:系统服务 | 组织模块 | 岗位管理 | 岗位权限分配',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13020600,13020000,'baf3aa4d771a4ac5b80c0192ae52964b','岗位删除',3,'0,13000000,13020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:post:del, RD:system:organize:post:del',NULL,6,'0','按钮:系统服务 | 组织模块 | 岗位管理 | 岗位删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030000,13000000,'f450bb13df9f42618ecbe62c46346d3e','用户管理',2,'0,13000000','user',NULL,'system/organize/user/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:organize:user:list, RD:system:organize:user:list','ant-design:user-outlined',3,'0','菜单:系统服务 | 组织模块 | 用户管理 | 用户管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030100,13030000,'3584b9c409ab49b79b2888936de01a2d','用户详情',3,'0,13000000,13030000','userDetail/:id',NULL,'system/organize/user/UserDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:organize:user:single, RD:system:organize:user:single',NULL,1,'0','详情:系统服务 | 组织模块 | 用户管理 | 用户详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030200,13030000,'679d5eccf32d43639898d6d5a347d643','用户新增',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:add, RD:system:organize:user:add',NULL,3,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030300,13030000,'bc20a8b4c70f42ada4666269cc6a7624','用户修改',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:edit, RD:system:organize:user:edit, RD:system:organize:user:single',NULL,4,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030400,13030000,'5fb9a4cbaa1f4059aff2f22ff02a78f0','用户状态修改',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:es, RD:system:organize:user:es',NULL,5,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030500,13030000,'b0d4e749c5eb49bdb3634fc5d4e57ecc','用户权限分配',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:auth, RD:system:organize:user:auth, RD:system:authority:role:list',NULL,5,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户权限分配',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030600,13030000,'6669f111d1974ec28b82e830ad2195c2','用户删除',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:del, RD:system:organize:user:del',NULL,6,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030700,13030000,'2527782114544c53acdff12dc8e09a34','用户导入',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:import, RD:system:organize:user:import',NULL,7,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户导入',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030800,13030000,'abc1c9ec3a73477184384bbfc37cf8a6','用户导出',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:export, RD:system:organize:user:export',NULL,8,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户导出',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(13030900,13030000,'a79774cd4205485c9e5ea9c36be41573','用户密码重置',3,'0,13000000,13030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:organize:user:rp, RD:system:organize:user:rp',NULL,5,'0','按钮:系统服务 | 组织模块 | 用户管理 | 用户密码修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14000000,0,'61ac2678fa5a4cd7977c118114ff1828','权限管理',1,'0','authority',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:safety-certificate-outlined',2,'0','目录:权限管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010000,14000000,'88c136711d98441699a6013ef27a356a','角色管理',2,'0,14000000','role',NULL,'system/authority/role/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:authority:role:list, RD:system:authority:role:list','ant-design:team-outlined',3,'0','菜单:系统服务 | 权限模块 | 角色管理 | 角色管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010100,14010000,'458c8e2ae43e47059b978504dd363a94','角色详情',3,'0,14000000,14010000','roleDetail/:id',NULL,'system/authority/role/RoleDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:authority:role:single, RD:system:authority:role:single',NULL,1,'0','详情:系统服务 | 权限模块 | 角色管理 | 角色详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010200,14010000,'949497ca68724eb18f43b550c4c4bf5d','角色新增',3,'0,14000000,14010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:role:add, RD:system:authority:role:add',NULL,2,'0','按钮:系统服务 | 权限模块 | 角色管理 | 角色新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010300,14010000,'77604dc9a47b4cc296e9f41cb76f81f5','角色修改',3,'0,14000000,14010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:role:edit, RD:system:authority:role:edit, RD:system:authority:role:single',NULL,3,'0','按钮:系统服务 | 权限模块 | 角色管理 | 角色修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010400,14010000,'ed3d7dfa06424f1abe271058aa3abbd9','角色状态修改',3,'0,14000000,14010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:role:es, RD:system:authority:role:es',NULL,4,'0','按钮:系统服务 | 权限模块 | 角色管理 | 角色状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010500,14010000,'8bd13d9c71624e60a288d424a6a96670','角色权限分配',3,'0,14000000,14010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:role:auth, RD:system:authority:role:auth',NULL,5,'0','按钮:系统服务 | 权限模块 | 角色管理 | 角色权限分配',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(14010600,14010000,'54dc750a6076494ebc40e62b5d775db7','角色删除',3,'0,14000000,14010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:role:del, RD:system:authority:role:del',NULL,6,'0','按钮:系统服务 | 权限模块 | 角色管理 | 角色删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15000000,0,'de6e93785ba944e1bde5538e92bd7b91','公告管理',1,'0','notice',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:schedule-outlined',3,'0','目录:公告管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15010000,15000000,'b7214522d55c4f1ebd381859dc6774bb','通知公告',2,'0,15000000','notice',NULL,'system/notice/notice/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:notice:notice:list, RD:system:notice:notice:list','ant-design:notification-outlined',1,'0','菜单:系统服务 | 消息模块 | 通知公告管理 | 通知公告管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15010100,15010000,'3b77bf3ae1264e5a960262cd898a9279','通知公告详情',3,'0,15000000,15010000','noticeDetail/:id',NULL,'system/notice/notice/NoticeDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:notice:notice:single, RD:system:notice:notice:single',NULL,1,'0','详情:系统服务 | 消息模块 | 通知公告管理 | 通知公告详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15010200,15010000,'9d21b1e6c2134ac9943db2d1ffa4d61f','通知公告新增',3,'0,15000000,15010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:notice:notice:add, RD:system:notice:notice:add',NULL,2,'0','按钮:系统服务 | 消息模块 | 通知公告管理 | 通知公告新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15010300,15010000,'04b84309c6544e20bf40df26349b4212','通知公告修改',3,'0,15000000,15010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:notice:notice:edit, RD:system:notice:notice:edit',NULL,3,'0','按钮:系统服务 | 消息模块 | 通知公告管理 | 通知公告修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(15010400,15010000,'010e949049864492b046883b8571063d','通知公告删除',3,'0,15000000,15010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:notice:notice:del, RD:system:notice:notice:del',NULL,4,'0','按钮:系统服务 | 消息模块 | 通知公告管理 | 通知公告删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16000000,0,'fa8965418a3540a99a62c805a4616e59','系统管理',1,'0','system',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:control-outlined',4,'0','目录:系统管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010000,16000000,'60a02b15ddcf45eab582a0c57af6ae62','定时任务',2,'0,16000000','job',NULL,'system/system/job/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:job:schedule:job:list, RD:job:schedule:job:list','ant-design:field-time-outlined',1,'0','菜单:定时任务 | 调度任务管理 | 定时任务管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010100,16010000,'101aeea275ce4c61b0652491f75125c0','任务详情',3,'0,16000000,16010000','jobDetail/:id',NULL,'system/system/job/JobDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:job:schedule:job:single, RD:job:schedule:job:single',NULL,1,'0','详情:定时任务 | 调度任务管理 | 定时任务详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010200,16010000,'c48c23456ed144719fe6d0e35d9e8ddf','调度日志',3,'0,16000000,16010000','jobLogDetail/:id',NULL,'system/system/jobLog/index',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:job:schedule:job:log, RD:job:schedule:job:log, RD:job:schedule:job:list',NULL,2,'0','详情:定时任务 | 调度任务管理 | 调度日志',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010300,16010000,'099a0d943a604d0198516e5f6766ac04','任务新增',3,'0,16000000,16010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:job:schedule:job:add, RD:job:schedule:job:add',NULL,3,'0','按钮:定时任务 | 调度任务管理 | 定时任务新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010400,16010000,'0acf6076acca4ff689f3df52161099e7','任务修改',3,'0,16000000,16010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:job:schedule:job:edit, RD:job:schedule:job:edit, RD:job:schedule:job:single',NULL,4,'0','按钮:定时任务 | 调度任务管理 | 定时任务修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010500,16010000,'83398a4cd3ce4aa1a26a00b0b8a7612b','任务状态修改',3,'0,16000000,16010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:job:schedule:job:es, RD:job:schedule:job:es',NULL,5,'0','按钮:定时任务 | 调度任务管理 | 定时任务状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(16010600,16010000,'96bdff8d31be422b89cafb64d22ffd2c','任务删除',3,'0,16000000,16010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:job:schedule:job:del, RD:job:schedule:job:del',NULL,6,'0','按钮:定时任务 | 调度任务管理 | 定时任务删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17000000,0,'e24afc855afc485d961b865d5d999811','系统监控',1,'0','monitor',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:eye-outlined',5,'0','目录:系统监控',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17010000,17000000,'f4a053ab18c84a82be6b2f04d3adaaac','在线用户',2,'0,17000000','online',NULL,'system/monitor/online/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:monitor:online:list, RD:system:monitor:online:list','ant-design:contacts-outlined',1,'0','菜单:系统服务 | 监控模块 | 在线用户管理 | 在线用户管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17010100,17010000,'6829ff5c29894306b2d4fa0d6769e6e6','在线用户强退',3,'0,17000000,17010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:online:forceLogout, RD:system:monitor:online:forceLogout',NULL,1,'0','按钮:-系统服务 | 监控模块 | 在线用户管理 | 在线用户强退',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020000,17000000,'693be0ee3df14643ba93acf1b0e2df97','日志管理',2,'0,17000000','log',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:file-text-outlined',2,'0','目录:日志管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020100,17020000,'89d2de0ab59248a59f41d097a41517b5','登录日志',3,'0,17000000,17020000','loginLog',NULL,'system/monitor/loginLog/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:monitor:loginLog:list, RD:system:monitor:loginLog:list','ant-design:contacts-outlined',3,'0','菜单:系统服务 | 监控模块 | 访问日志管理 | 登录日志',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020101,17020100,'10afaa52d5be4a9ca9de5da3567d3d47','登录日志删除',4,'0,17000000,17020000,17020100',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:loginLog:del, RD:system:monitor:loginLog:del',NULL,1,'0','按钮:系统服务 | 监控模块 | 访问日志管理 | 登录日志删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020102,17020100,'f29ae2f1ec794de992109776f46743cc','登录日志导出',4,'0,17000000,17020000,17020100',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:loginLog:export, RD:system:monitor:loginLog:export',NULL,2,'0','按钮:系统服务 | 监控模块 | 访问日志管理 | 登录日志导出',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020200,17020000,'7a3b5c396f8647499a9c9cd97712324a','操作日志',3,'0,17000000,17020000','operateLog',NULL,'system/monitor/operateLog/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:monitor:operateLog:list, RD:system:monitor:operateLog:list','ant-design:contacts-twotone',4,'0','菜单:系统服务 | 监控模块 | 操作日志管理 | 操作日志管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020201,17020000,'8b4266f509f748baac4f86739d7bb8c9','操作日志详情',4,'0,17000000,17020000,17020200',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:operateLog:add, RD:system:monitor:operateLog:add',NULL,1,'0','按钮:系统服务 | 监控模块 | 操作日志管理 | 操作日志详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020202,17020000,'8340af3b5dcc44e2837ef92e7de4da9d','操作日志删除',4,'0,17000000,17020000,17020200',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:operateLog:del, RD:system:monitor:operateLog:del',NULL,2,'0','按钮:系统服务 | 监控模块 | 操作日志管理 | 操作日志删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(17020203,17020000,'05598058a8794251b454025d863c6daa','操作日志导出',4,'0,17000000,17020000,17020200',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:monitor:operateLog:export, RD:system:monitor:operateLog:export',NULL,3,'0','按钮:系统服务 | 监控模块 | 操作日志管理 | 操作日志导出',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,1),(22000000,0,'0d7114083f3e4c169e29bb7c4c2394d1','权限管理',1,'0','authority',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:safety-certificate-outlined',3,'0','目录:权限管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010000,22000000,'7fdc19cde2494105b5b1ee951a560904','模块管理',2,'0,22000000','module',NULL,'system/authority/module/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:authority:module:list, RD:system:authority:module:list, RD:tenant:tenant:tenant:list','ant-design:appstore-add-outlined',1,'0','菜单:系统服务 | 权限模块 | 模块管理 | 模块管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010100,22010000,'b6aeef72800e4082ace3673e454a66b6','模块详情',3,'0,22000000,22010000','moduleDetail/:id',NULL,'system/authority/module/ModuleDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:authority:module:single, RD:system:authority:module:single',NULL,1,'0','详情:系统服务 | 权限模块 | 模块管理 | 模块详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010200,22010000,'96047ce6aead414bab788070a65abab9','模块新增',3,'0,22000000,22010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:module:add, RD:system:authority:module:add, RD:tenant:tenant:tenant:list',NULL,2,'0','按钮:系统服务 | 权限模块 | 模块管理 | 模块新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010300,22010000,'ec5c9fc0b5ba4c9794f5b87b031c3104','模块修改',3,'0,22000000,22010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:module:edit, RD:system:authority:module:edit, RD:system:authority:module:single, RD:tenant:tenant:tenant:list',NULL,3,'0','按钮:系统服务 | 权限模块 | 模块管理 | 模块修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010400,22010000,'ca53bb0ac4524d58baf1e647e0d0da68','模块状态修改',3,'0,22000000,22010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:module:es, RD:system:authority:module:es',NULL,4,'0','按钮:系统服务 | 权限模块 | 模块管理 | 模块状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22010500,22010000,'37a9b9cc2bcf4e7a98ea535526b3a597','模块删除',3,'0,22000000,22010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:module:del, RD:system:authority:module:del',NULL,5,'0','按钮:系统服务 | 权限模块 | 模块管理 | 模块删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020000,22000000,'bba8d25857ec45f38a8946c8c74182c2','菜单管理',2,'0,22000000','menu',NULL,'system/authority/menu/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:authority:menu:list, RD:system:authority:menu:list, RD:tenant:tenant:tenant:list, RD:system:authority:module:list','ant-design:bars-outlined',2,'0','菜单:系统服务 | 权限模块 | 菜单管理 | 菜单管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020100,22020000,'5e4b17aeae124b91b7bad8f4dd11b7b4','菜单详情',3,'0,22000000,22020000','menuDetail/:id',NULL,'system/authority/menu/MenuDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:authority:menu:single, RD:system:authority:menu:single',NULL,1,'0','详情:系统服务 | 权限模块 | 菜单管理 | 菜单详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020200,22020000,'a1ea4b626a104a5380a6a2ba3eddcd2e','菜单新增',3,'0,22000000,22020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:menu:add, RD:system:authority:menu:add, RD:tenant:tenant:tenant:list, RD:system:authority:module:list',NULL,2,'0','按钮:系统服务 | 权限模块 | 菜单管理 | 菜单新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020300,22020000,'af5e2c3864eb4ef3a602f3d5619a53d4','菜单修改',3,'0,22000000,22020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:menu:edit, RD:system:authority:menu:edit, RD:system:authority:menu:single, RD:tenant:tenant:tenant:list, RD:system:authority:module:list',NULL,3,'0','按钮:系统服务 | 权限模块 | 菜单管理 | 菜单修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020400,22020000,'69ebe38822d54e81b828c36a8dfdb479','菜单状态修改',3,'0,22000000,22020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:menu:es, RD:system:authority:menu:es',NULL,4,'0','按钮:系统服务 | 权限模块 | 菜单管理 | 菜单状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22020500,22020000,'ed7bdf2e22244a72b3963e9750d2df98','菜单删除',3,'0,22000000,22020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:menu:del, RD:system:authority:menu:del',NULL,5,'0','按钮:系统服务 | 权限模块 | 菜单管理 | 菜单删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030000,22000000,'1915bdb53937486a82d9ce9d380d41b1','权限组管理',2,'0,22000000','authGroup',NULL,'system/authority/authGroup/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:authority:authGroup:list, RD:system:authority:authGroup:list','ant-design:property-safety-outlined',1,'0','菜单:系统服务 | 权限模块 | 租户权限组管理 | 权限组管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030100,22030000,'8630a790fd5c40c4b21f4236d60f7e75','权限组详情',3,'0,22000000,22030000','authGroupDetail/:id',NULL,'system/authority/authGroup/AuthGroupDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:authority:authGroup:single, RD:system:authority:authGroup:single',NULL,2,'0','详情:系统服务 | 权限模块 | 租户权限组管理 | 权限组详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030200,22030000,'a681492a05154ac1b066d6bce0ef4dd1','权限组新增',3,'0,22000000,22030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:authGroup:add, RD:system:authority:authGroup:add',NULL,3,'0','按钮:系统服务 | 权限模块 | 租户权限组管理 | 权限组新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030300,22030000,'127e52a2169443bbaffddbbf409ef322','权限组修改',3,'0,22000000,22030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:authGroup:edit, RD:system:authority:authGroup:edit, RD:system:authority:authGroup:single',NULL,4,'0','按钮:系统服务 | 权限模块 | 租户权限组管理 | 权限组修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030400,22030000,'77a7e74eb44a4c2fb1fd27e6d2dab94c','权限组状态修改',3,'0,22000000,22030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:authGroup:es, RD:system:authority:authGroup:es',NULL,5,'0','按钮:系统服务 | 权限模块 | 租户权限组管理 | 权限组状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(22030500,22030000,'78f27eab2bf240cdbbb6dc33a11e9730','权限组删除',3,'0,22000000,22030000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:authority:authGroup:del, RD:system:authority:authGroup:del',NULL,6,'0','按钮:系统服务 | 权限模块 | 租户权限组管理 | 权限组删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23000000,0,'fe8bad86fdf34ed7ab584887e1e0f786','系统管理',1,'0','system',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:key-outlined',4,'0','目录:系统管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010000,23000000,'b08569b9c6044608913cae26f427f842','字典管理',2,'0,23000000','dict',NULL,'system/dict/dict/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:dict:dict:list, RD:system:dict:dict:list, RD:tenant:tenant:tenant:list','ant-design:file-text-outlined',1,'0','菜单:系统服务 | 字典模块 | 字典管理 | 字典管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010100,23010000,'bf7ca66a708f4ee88d5e58df65b558fb','字典详情',3,'0,23000000,23010000','dictDetail/:id',NULL,'system/dict/dict/DictDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:dict:dict:single, RD:system:dict:dict:single',NULL,1,'0','详情:系统服务 | 字典模块 | 字典管理 | 字典详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010200,23010000,'71b7dcc4d4a74f5fb78b1d4457c6829e','字典新增',3,'0,23000000,23010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:dict:add, RD:system:dict:dict:add, RD:tenant:tenant:tenant:list',NULL,2,'0','按钮:系统服务 | 字典模块 | 字典管理 | 字典新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010300,23010000,'72f4012cdc744c048367c50de2bf603b','字典数据',3,'0,23000000,23010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:dict:dict, RD:system:dict:dict:dict, RD:tenant:tenant:tenant:list, RD:system:dict:dict:list',NULL,3,'0','按钮:系统服务 | 字典模块 | 字典管理 | 字典数据',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010400,23010000,'2df41ed5aed44909ab4ea3f66ea5f4d4','字典修改',3,'0,23000000,23010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:dict:edit, RD:system:dict:dict:edit, RD:system:dict:dict:single',NULL,4,'0','按钮:系统服务 | 字典模块 | 字典管理 | 字典修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010500,23010000,'bfa0a7b8d5be4189acf8933b4dd9bf94','字典状态修改',3,'0,23000000,23010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:dict:es, RD:system:dict:dict:es',NULL,5,'0','按钮:系统服务 | 字典模块 | 字典管理 | 字典状态修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23010600,23010000,'8808bad6108546439e560ead38715aba','字典删除',3,'0,23000000,23010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:dict:del, RD:system:dict:dict:del',NULL,6,'0','按钮:系统服务 | 字典模块 | 字典管理 | 字典删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23020000,23000000,'71c9c40055b44052a27c5b3775c42054','参数管理',2,'0,23000000','config',NULL,'system/dict/config/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:system:dict:config:list, RD:system:dict:config:list, RD:tenant:tenant:tenant:list','ant-design:function-outlined',2,'0','菜单:系统服务 | 字典模块 | 参数管理 | 参数管理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23020100,23020000,'9fdfef2871974adcb8dcd546af0f4aa2','参数详情',3,'0,23000000,23020000','configDetail/:id',NULL,'system/dict/config/ConfigDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:system:dict:config:single, RD:system:dict:config:single',NULL,1,'0','详情:系统服务 | 字典模块 | 参数管理 | 参数详情',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23020200,23020000,'504ce298004f4a7fa93794ea974500cf','参数新增',3,'0,23000000,23020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:config:add, RD:system:dict:config:add, RD:tenant:tenant:tenant:list',NULL,2,'0','按钮:系统服务 | 字典模块 | 参数管理 | 参数新增',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23020300,23020000,'aa46bd42a43b4921afa392107b062749','参数修改',3,'0,23000000,23020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:config:edit, RD:system:dict:config:edit, RD:system:dict:config:single, RD:tenant:tenant:tenant:list',NULL,3,'0','按钮:系统服务 | 字典模块 | 参数管理 | 参数修改',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(23020400,23020000,'25c63be4b66e4b05b40c7393ac773fd3','参数删除',3,'0,23000000,23020000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:system:dict:config:del, RD:system:dict:config:del',NULL,4,'0','按钮:系统服务 | 字典模块 | 参数管理 | 参数删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(24000000,0,'e24afc855afc485d96135999811','系统监控',1,'0','monitor',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:eye-outlined',5,'0','目录:系统监控',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(24010000,24000000,'8fa8a4e494594496a89635028483d28b','流量监控',2,'0,24000000','sentinel','http://localhost:8718',NULL,NULL,NULL,'N','N','N','N','1','C','0','0','0','0','0',1,NULL,'monitor:sentinel:list','ant-design:fund-projection-screen-outlined',1,'0','菜单:流量监控',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(24020000,24000000,'8fa8a4e494594496a865a6428483d28b','服务治理',2,'0,24000000',NULL,'http://127.0.0.1:8848/nacos/#/',NULL,NULL,NULL,'N','N','N','N','2','C','0','0','0','0','0',1,NULL,'monitor:nacos:list','ant-design:control-outlined',2,'0','菜单:服务治理',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(24030000,24000000,'8fa8a4e494594496a32586534583d28b','服务监控',2,'0,24000000','server','http://localhost:9100/applications',NULL,NULL,NULL,'N','N','N','N','1','C','0','0','0','0','0',1,NULL,'monitor:server:list','ant-design:radar-chart-outlined',3,'0','菜单:服务监控',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25000000,0,'fbaad9604c74427592126a9a0e756067','系统工具',1,'0','generate',NULL,'',NULL,NULL,'N','N','N','N','0','M','0','0','0','0','0',1,NULL,'','ant-design:setting-outlined',1,'0','目录:系统工具',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010000,25000000,'ebd46c2fd3c3429896de95a82bcf1d8b','代码生成',2,'0,25000000','gen',NULL,'gen/generate/gen/index',NULL,NULL,'N','N','N','N','0','C','0','0','0','0','0',1,NULL,'FE:gen:generate:gen:list, RD:gen:generate:gen:list','ant-design:experiment-outlined',1,'0','菜单:代码生成 | 代码生成管理 | 代码生成',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010100,25010000,'b489b7b0e645471eb42ed8b1f0365d32','代码生成配置',3,'0,25000000,25010000','generateDetail/:id',NULL,'gen/generate/gen/GenDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:gen:generate:gen:edit, RD:gen:generate:gen:edit, RD:gen:generate:gen:single, RD:system:authority:module:list, RD:system:authority:menu:list',NULL,1,'0','详情:代码生成 | 代码生成管理 | 代码生成配置',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010200,25010000,'c49d2b1d7d6640e7bd331aa494b05e58','代码生成预览',3,'0,25000000,25010000','codeDetail/:id',NULL,'gen/generate/gen/CodeDetail',NULL,NULL,'N','Y','N','N','0','X','0','1','0','0','0',5,NULL,'FE:gen:generate:gen:preview, RD:gen:generate:gen:preview, RD:gen:generate:gen:single',NULL,2,'0','详情:代码生成 | 代码生成管理 | 代码生成预览',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010300,25010000,'f264257d3d4948bd9204ee5ba39b1661','代码生成导入',3,'0,25000000,25010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:gen:generate:gen:import, RD:gen:generate:gen:import, RD:tenant:tenant:source:list, RD:gen:generate:gen:list',NULL,3,'0','按钮:代码生成 | 代码生成管理 | 代码生成导入',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010400,25010000,'828aa317e06b48a2adc9d783ee968d99','代码生成下载',3,'0,25000000,25010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:gen:generate:gen:code, RD:gen:generate:gen:code',NULL,4,'0','按钮:代码生成 | 代码生成管理 | 代码生成下载',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25010500,25010000,'29db55acc054471f8b68411f99be1dee','代码生成删除',3,'0,25000000,25010000',NULL,NULL,NULL,NULL,NULL,'N','N','N','N','0','F','0','0','0','0','0',1,NULL,'FE:gen:generate:gen:del, RD:gen:generate:gen:del',NULL,5,'0','按钮:代码生成 | 代码生成管理 | 代码生成删除',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2),(25020000,25000000,'8fa8a4e494594496a865ab028483d28b','系统接口',2,'0,25000000','swagger','http://localhost:8080/webjars/swagger-ui/index.html',NULL,NULL,NULL,'N','N','N','N','1','C','0','0','0','0','0',1,NULL,'monitor:swagger:list','ant-design:api-twotone',2,'0','菜单:系统接口',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0,2);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_module`
--

DROP TABLE IF EXISTS `sys_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_module` (
                              `id` bigint NOT NULL COMMENT '模块Id',
                              `name` varchar(50) NOT NULL COMMENT '模块名称',
                              `logo` varchar(200) DEFAULT NULL COMMENT '模块logo',
                              `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
                              `param_path` varchar(255) DEFAULT NULL COMMENT '路由参数',
                              `type` char(1) NOT NULL DEFAULT '0' COMMENT '模块类型（0常规 1内嵌 2外链）',
                              `hide_module` char(1) NOT NULL DEFAULT '0' COMMENT '模块显隐状态（0显示 1隐藏）',
                              `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                              `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                              `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                              `create_by` bigint DEFAULT NULL COMMENT '创建者',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_by` bigint DEFAULT NULL COMMENT '更新者',
                              `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `is_common` char(1) NOT NULL DEFAULT '1' COMMENT '公共模块（0公共 1私有）',
                              `is_default` char(1) NOT NULL DEFAULT 'N' COMMENT '默认模块（Y是 N否）',
                              `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='模块信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_module`
--

LOCK TABLES `sys_module` WRITE;
/*!40000 ALTER TABLE `sys_module` DISABLE KEYS */;
INSERT INTO `sys_module` VALUES (1,'基础平台','https://images.gitee.com/uploads/images/2021/1101/141155_f3dfce1d_7382127.jpeg','','','0','0',0,'0','基础平台',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0),(2,'开发者平台','https://images.gitee.com/uploads/images/2021/1101/141601_d68e92a4_7382127.jpeg','','','0','0',0,'0','开发者平台',NULL,'2024-09-15 18:11:54',NULL,NULL,'0','Y',0);
/*!40000 ALTER TABLE `sys_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
                              `id` bigint NOT NULL COMMENT '公告Id',
                              `name` varchar(50) NOT NULL COMMENT '公告标题',
                              `type` char(1) NOT NULL DEFAULT '0' COMMENT '公告类型（0通知 1公告）',
                              `content` longblob COMMENT '公告内容',
                              `status` char(1) DEFAULT '0' COMMENT '公告状态（0待发送 1已发送 2已关闭 3发送失败 4发送异常）',
                              `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                              `create_by` bigint DEFAULT NULL COMMENT '创建者',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_by` bigint DEFAULT NULL COMMENT '更新者',
                              `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice_log`
--

DROP TABLE IF EXISTS `sys_notice_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice_log` (
                                  `id` bigint NOT NULL COMMENT 'id',
                                  `notice_id` bigint NOT NULL COMMENT '公告Id',
                                  `user_id` bigint NOT NULL COMMENT '用户Id',
                                  `receive_status` char(1) NOT NULL COMMENT '发送状态（0成功 1失败）',
                                  `status` char(1) DEFAULT '0' COMMENT '阅读状态（0未读 1已读）',
                                  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice_log`
--

LOCK TABLES `sys_notice_log` WRITE;
/*!40000 ALTER TABLE `sys_notice_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oauth_client`
--

DROP TABLE IF EXISTS `sys_oauth_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oauth_client` (
                                    `id` bigint NOT NULL COMMENT '租户Id',
                                    `client_id` varchar(32) NOT NULL COMMENT '客户端Id',
                                    `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源列表',
                                    `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密钥',
                                    `scope` varchar(256) DEFAULT NULL COMMENT '域',
                                    `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '认证类型',
                                    `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '重定向地址',
                                    `authorities` varchar(256) DEFAULT NULL COMMENT '角色列表',
                                    `access_token_validity` int DEFAULT NULL COMMENT 'token 有效期',
                                    `refresh_token_validity` int DEFAULT NULL COMMENT '刷新令牌有效期',
                                    `additional_information` varchar(4096) DEFAULT NULL COMMENT '令牌扩展字段JSON',
                                    `auto_approve` varchar(256) DEFAULT NULL COMMENT '是否自动放行',
                                    `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                                    `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                    `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                    `create_by` bigint DEFAULT NULL COMMENT '创建者',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_by` bigint DEFAULT NULL COMMENT '更新者',
                                    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='终端信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oauth_client`
--

LOCK TABLES `sys_oauth_client` WRITE;
/*!40000 ALTER TABLE `sys_oauth_client` DISABLE KEYS */;
INSERT INTO `sys_oauth_client` VALUES (1,'app',NULL,'app','server','app,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',1,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(2,'client',NULL,'client','server','client_credentials',NULL,NULL,NULL,NULL,NULL,'true',1,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(3,'daemon',NULL,'daemon','server','password,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',1,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(4,'xueyi',NULL,'xueyi','server','password,app,refresh_token,authorization_code,client_credentials','https://xueyitt.cn',NULL,NULL,NULL,NULL,'true',1,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(5,'gen',NULL,'gen','server','password,refresh_token',NULL,NULL,NULL,NULL,NULL,'true',1,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_oauth_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operate_log`
--

DROP TABLE IF EXISTS `sys_operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operate_log` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                   `title` varchar(50) DEFAULT '' COMMENT '模块标题',
                                   `business_type` char(2) DEFAULT '00' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
                                   `method` varchar(100) DEFAULT '' COMMENT '方法名称',
                                   `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
                                   `operate_type` char(2) DEFAULT '00' COMMENT '操作类别（00其它 01后台 02手机端）',
                                   `user_id` bigint NOT NULL DEFAULT '-2' COMMENT '操作人员',
                                   `user_name` varchar(50) DEFAULT NULL COMMENT '操作人员账号',
                                   `user_nick` varchar(50) DEFAULT NULL COMMENT '操作人员名称',
                                   `url` varchar(255) DEFAULT '' COMMENT '请求URL',
                                   `ip` varchar(128) DEFAULT '' COMMENT '主机地址',
                                   `param` varchar(2000) DEFAULT '' COMMENT '请求参数',
                                   `location` varchar(255) DEFAULT '' COMMENT '操作地点',
                                   `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
                                   `status` char(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
                                   `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
                                   `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
                                   `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                   `del_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
                                   `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                                   PRIMARY KEY (`id`),
                                   KEY `idx_sys_operate_log_bt` (`business_type`),
                                   KEY `idx_sys_operate_log_s` (`status`),
                                   KEY `idx_sys_operate_log_ot` (`operate_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1835279526679031809 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operate_log`
--

LOCK TABLES `sys_operate_log` WRITE;
/*!40000 ALTER TABLE `sys_operate_log` DISABLE KEYS */;
INSERT INTO `sys_operate_log` VALUES (1835279031193317376,'字典类型','16','com.xueyi.system.dict.controller.inner.ISysDictTypeController.refreshCommonCacheInner()','GET','01',1,'admin','admin','/inner/dict/type/common/refresh','127.0.0.1','{}','','{\"code\":200,\"fail\":false,\"ok\":true}','0','',88,'2024-09-15 19:26:33',NULL,0),(1835279031579193344,'参数管理','16','com.xueyi.system.dict.controller.inner.ISysConfigController.refreshCommonCacheInner()','GET','01',1,'admin','admin','/inner/config/common/refresh','127.0.0.1','{}','','{\"code\":200,\"fail\":false,\"ok\":true}','0','',31,'2024-09-15 19:26:33',NULL,0),(1835279387935649792,'菜单管理','08','com.xueyi.system.authority.controller.admin.ASysMenuController.batchRemove()','DELETE','01',1,'admin','admin','/admin/menu/batch/21000000','127.0.0.1','{}','','','1','删除失败，所有待删除菜单皆存在子菜单！',6,'2024-09-15 19:27:58',NULL,0),(1835279512225460224,'菜单管理','08','com.xueyi.system.authority.controller.admin.ASysMenuController.batchRemove()','DELETE','01',1,'admin','admin','/admin/menu/batch/21010100,21010200,21010300,21010400,21010500,21010600,21020100,21020200,21020300,21020400,21020500,21030100,21030200,21030300,21030400,21030500','127.0.0.1','{}','','','1','无待删除菜单！',31,'2024-09-15 19:28:28',NULL,0),(1835279526679031808,'菜单管理','08','com.xueyi.system.authority.controller.admin.ASysMenuController.batchRemove()','DELETE','01',1,'admin','admin','/admin/menu/batch/21010100,21010200,21010300,21010400,21010500,21010600,21020100,21020200,21020300,21020400,21020500,21030100,21030200,21030300,21030400,21030500','127.0.0.1','{}','','','1','无待删除菜单！',25,'2024-09-15 19:28:31',NULL,0);
/*!40000 ALTER TABLE `sys_operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organize_role_merge`
--

DROP TABLE IF EXISTS `sys_organize_role_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_organize_role_merge` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                           `dept_id` bigint DEFAULT NULL COMMENT '部门id',
                                           `post_id` bigint DEFAULT NULL COMMENT '岗位id',
                                           `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                           `role_id` bigint NOT NULL COMMENT '角色Id',
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `dept_id` (`dept_id`,`post_id`,`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组织和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organize_role_merge`
--

LOCK TABLES `sys_organize_role_merge` WRITE;
/*!40000 ALTER TABLE `sys_organize_role_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_organize_role_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
                            `id` bigint NOT NULL COMMENT '岗位Id',
                            `dept_id` bigint NOT NULL COMMENT '部门Id',
                            `code` varchar(64) DEFAULT NULL COMMENT '岗位编码',
                            `name` varchar(50) NOT NULL COMMENT '岗位名称',
                            `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,99,'ceo','超级管理员',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(2,100,'ceo','董事长',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(3,100,'se','项目经理',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(4,100,'hr','人力资源',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0),(5,100,'user','普通员工',0,'0',NULL,NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL COMMENT '角色Id',
                            `code` varchar(64) DEFAULT NULL COMMENT '角色编码',
                            `name` varchar(30) NOT NULL COMMENT '角色名称',
                            `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
                            `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）',
                            `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1删除）',
                            `tenant_id` bigint NOT NULL COMMENT '租户Id',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'001','超级管理员','admin','1',0,'0','超级管理员',NULL,'2024-09-15 18:11:54',NULL,NULL,0,1),(2,'002','管理员','common','1',0,'0','普通角色',NULL,'2024-09-15 18:11:54',NULL,NULL,0,1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept_merge`
--

DROP TABLE IF EXISTS `sys_role_dept_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept_merge` (
                                       `id` bigint NOT NULL COMMENT 'id',
                                       `role_id` bigint NOT NULL COMMENT '角色Id',
                                       `dept_id` bigint NOT NULL COMMENT '部门Id',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `role_id` (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和部门-岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept_merge`
--

LOCK TABLES `sys_role_dept_merge` WRITE;
/*!40000 ALTER TABLE `sys_role_dept_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_dept_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu_merge`
--

DROP TABLE IF EXISTS `sys_role_menu_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu_merge` (
                                       `id` bigint NOT NULL COMMENT 'id',
                                       `role_id` bigint NOT NULL COMMENT '角色Id',
                                       `menu_id` bigint NOT NULL COMMENT '菜单Id',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu_merge`
--

LOCK TABLES `sys_role_menu_merge` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_module_merge`
--

DROP TABLE IF EXISTS `sys_role_module_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_module_merge` (
                                         `id` bigint NOT NULL COMMENT 'id',
                                         `role_id` bigint NOT NULL COMMENT '角色Id',
                                         `module_id` bigint NOT NULL COMMENT '模块Id',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `role_id` (`role_id`,`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和模块关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_module_merge`
--

LOCK TABLES `sys_role_module_merge` WRITE;
/*!40000 ALTER TABLE `sys_role_module_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_module_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_post_merge`
--

DROP TABLE IF EXISTS `sys_role_post_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_post_merge` (
                                       `id` bigint NOT NULL COMMENT 'id',
                                       `role_id` bigint NOT NULL COMMENT '角色Id',
                                       `post_id` bigint NOT NULL COMMENT '岗位Id',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `role_id` (`role_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和部门-岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_post_merge`
--

LOCK TABLES `sys_role_post_merge` WRITE;
/*!40000 ALTER TABLE `sys_role_post_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_post_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL COMMENT '用户Id',
                            `code` varchar(64) DEFAULT NULL COMMENT '用户编码',
                            `user_name` varchar(30) NOT NULL COMMENT '用户账号',
                            `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
                            `user_type` varchar(2) DEFAULT '01' COMMENT '用户类型（00超管用户 01普通用户）',
                            `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
                            `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
                            `sex` char(1) DEFAULT '2' COMMENT '用户性别（0男 1女 2保密）',
                            `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
                            `profile` varchar(100) DEFAULT '这个人很懒，暂未留下什么' COMMENT '个人简介',
                            `password` varchar(100) DEFAULT '' COMMENT '密码',
                            `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
                            `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
                            `sort` int unsigned DEFAULT '0' COMMENT '显示顺序',
                            `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                            `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新者',
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志(0正常 1删除)',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'001','admin','admin','00','','','2','https://images.gitee.com/uploads/images/2021/1101/141155_f3dfce1d_7382127.jpeg','这个人很懒，暂未留下什么','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','',NULL,0,'0','超级管理员',NULL,'2024-09-15 18:11:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post_merge`
--

DROP TABLE IF EXISTS `sys_user_post_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post_merge` (
                                       `id` bigint NOT NULL COMMENT 'id',
                                       `user_id` bigint NOT NULL COMMENT '用户Id',
                                       `post_id` bigint NOT NULL COMMENT '职位Id',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `user_id` (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post_merge`
--

LOCK TABLES `sys_user_post_merge` WRITE;
/*!40000 ALTER TABLE `sys_user_post_merge` DISABLE KEYS */;
INSERT INTO `sys_user_post_merge` VALUES (1,1,1),(2,2,2),(3,3,3);
/*!40000 ALTER TABLE `sys_user_post_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'yunque-cloud'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-15 19:35:56
