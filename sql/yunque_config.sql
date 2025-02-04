# DROP DATABASE IF EXISTS `yunque-config`;
#
# CREATE DATABASE  `yunque-config` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# SET NAMES utf8mb4;
# SET FOREIGN_KEY_CHECKS = 0;
#
# USE `yunque-config`;

-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yunque-config
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
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                               `group_id` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
                               `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                               `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
                               `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                               `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
                               `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
                               `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
                               `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
                               `c_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL,
                               `c_use` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
                               `effect` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
                               `type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
                               `c_schema` text COLLATE utf8mb3_bin,
                               `encrypted_data_key` text COLLATE utf8mb3_bin COMMENT '秘钥',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` VALUES (1,'application-dev.yml','DEFAULT_GROUP','spring:\r\n  mvc:\r\n    pathmatch:\r\n      matching-strategy: ant_path_matcher\r\n  # feign 配置\r\n  cloud:\r\n    openfeign:\r\n      okhttp:\r\n        enabled: true\r\n      httpclient:\r\n        enabled: false\r\n      client:\r\n        config:\r\n          default:\r\n            connectTimeout: 10000\r\n            readTimeout: 10000\r\n      compression:\r\n        request:\r\n          enabled: true\r\n        response:\r\n          enabled: true\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n\r\n# 暴露监控端点\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \' *\'\r\n','fbdbd8e046cef466dc1c71f0d66051c2','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','通用配置',NULL,NULL,'yaml',NULL,NULL),(2,'application-secret-dev.yml','DEFAULT_GROUP','secret:\n  # redis参数信息\n  redis:\n    host: 127.0.0.1\n    port: 6379\n    password:\n    database: 0\n  # 服务状态监控参数信息\n  monitor:\n    name: xueyi\n    password: xueyi123\n    title: 服务状态监控\n  # swagger参数信息\n  swagger:\n    author:\n      name: xueyi\n      email: xueyitt@qq.com\n    version: v3.0.2\n    title: 接口文档\n    license: Powered By xueyi\n    licenseUrl: https://doc.xueyitt.cn\n  # datasource主库参数信息\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/yunque-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: 123456\n  # nacos参数信息\n  nacos:\n    serverAddr: 127.0.0.1:8848\n    namespace:\n    username:\n    password:','b76ee577da59add8ff90ce9e977ae227','2024-03-18 09:14:31','2024-09-15 09:34:49',NULL,'0:0:0:0:0:0:0:1','','','通用参数配置','','','yaml','',''),(3,'application-datasource-dev.yml','DEFAULT_GROUP','# spring配置\r\nspring:\r\n  data:\r\n    redis:\r\n      host: ${secret.redis.host}\r\n      port: ${secret.redis.port}\r\n      password: ${secret.redis.password}\r\n      database: ${secret.redis.database}\r\n  datasource:\r\n    dynamic:\r\n      hikari:\r\n        # 连接超时时间：毫秒\r\n        connection-timeout: 30000\r\n        # 连接测试活性最长时间：毫秒\r\n        validation-timeout: 5000\r\n        # 空闲连接最大存活时间\r\n        idle-timeout: 300000\r\n        # 连接最大存活时间\r\n        max-lifetime: 600000\r\n        # 最大连接数\r\n        max-pool-size: 20\r\n        # 最小空闲连接\r\n        min-idle: 10\r\n      datasource:\r\n          # 主库数据源\r\n          master:\r\n            driver-class-name: ${secret.datasource.driver-class-name}\r\n            url: ${secret.datasource.url}\r\n            username: ${secret.datasource.username}\r\n            password: ${secret.datasource.password}\r\n          # 数据源信息会通过master库进行获取并生成，请在主库的xy_tenant_source中配置即可\r\n      # seata: true    # 开启seata代理，开启后默认每个数据源都代理，如果某个不需要代理可单独关闭\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  global-config:\r\n    # 是否控制台 print mybatis-plus 的 LOGO\r\n    banner: false\r\n    db-config:\r\n      # 字段验证策略之 select\r\n      selectStrategy: NOT_EMPTY\r\n      # 字段验证策略之 insert\r\n      insertStrategy: NOT_NULL\r\n      # 字段验证策略之 update\r\n      updateStrategy: IGNORED\r\n      # 全局逻辑删除的实体字段名\r\n      logic-delete-field: delFlag\r\n      # 逻辑已删除值\r\n      logic-delete-value: 1\r\n      # 逻辑未删除值\r\n      logic-not-delete-value: 0\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n\r\n# seata配置\r\nseata:\r\n  # 默认关闭，如需启用spring.datasource.dynami.seata需要同时开启\r\n  enabled: false\r\n  # Seata 应用编号，默认为 ${spring.application.name}\r\n  application-id: ${spring.application.name}\r\n  # Seata 事务组编号，用于 TC 集群名\r\n  tx-service-group: ${spring.application.name}-group\r\n  # 关闭自动代理\r\n  enable-auto-data-source-proxy: false\r\n  config:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${secret.nacos.serverAddr}\r\n      namespace: ${secret.nacos.namespace}\r\n      username: ${secret.nacos.username}\r\n      password: ${secret.nacos.password}\r\n      group: SEATA_GROUP\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      application: seata-server\r\n      server-addr: ${secret.nacos.serverAddr}\r\n      namespace: ${secret.nacos.namespace}\r\n      username: ${secret.nacos.username}\r\n      password: ${secret.nacos.password}\r\n\r\n\r\n## springdoc 配置\r\nspringdoc:\r\n  info:\r\n    title: ${application.title}${secret.swagger.title}\r\n    license:\r\n      name: ${secret.swagger.license}\r\n      url: ${secret.swagger.licenseUrl}\r\n    contact:\r\n      name: ${secret.swagger.author.name}\r\n      email: ${secret.swagger.author.email}\r\n    version: ${secret.swagger.version}\r\n    description: ${application.title}${secret.swagger.title}\r\n    termsOfService: ${secret.swagger.licenseUrl}\r\n  api-docs:\r\n    path: /v3/api-docs\r\n    enabled: true\r\n  group-configs:\r\n    - group: \'default\'\r\n      paths-to-match: \'/**\'\r\n      packages-to-scan:\r\n        - com.xueyi\r\n      display-name: ${application.title}-${secret.swagger.version}\r\n','96c83b173d9f4a5ca10cd971efdc81e8','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','通用动态多数据源配置',NULL,NULL,'yaml',NULL,NULL),(4,'yunque-gateway-dev.yml','DEFAULT_GROUP','# spring配置\nspring:\n  data:\n    redis:\n      host: ${secret.redis.host}\n      port: ${secret.redis.port}\n      password: ${secret.redis.password}\n      database: ${secret.redis.database}\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          # 启用服务发现\n          enabled: true\n          lowerCaseServiceId: true\n      # 网关路由\n      routes:\n        # 认证中心\n        - id: yunque-auth\n          uri: lb://yunque-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - CacheRequestFilter\n            - ValidateCodeFilter\n            - StripPrefix=1\n        # 代码生成\n        - id: yunque-gen\n          uri: lb://yunque-gen\n          predicates:\n            - Path=/code/**\n          filters:\n            - StripPrefix=1\n        # 定时任务\n        - id: yunque-job\n          uri: lb://yunque-job\n          predicates:\n            - Path=/schedule/**\n          filters:\n            - StripPrefix=1\n        # 系统模块\n        - id: yunque-system\n          uri: lb://yunque-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - StripPrefix=1\n        # 租户模块\n        - id: yunque-tenant\n          uri: lb://yunque-tenant\n          predicates:\n            - Path=/tenant/**\n          filters:\n            - StripPrefix=1\n        # 店铺模块\n        - id: yunque-store\n          uri: lb://yunque-store\n          predicates:\n            - Path=/store/**\n          filters:\n            - StripPrefix=1\n        # 会员模块\n        - id: yunque-member\n          uri: lb://yunque-store\n          predicates:\n            - Path=/member/**\n          filters:\n            - StripPrefix=1\n        # 商品模块\n        - id: yunque-product\n          uri: lb://yunque-store\n          predicates:\n            - Path=/product/**\n          filters:\n            - StripPrefix=1\n        # 订单模块\n        - id: yunque-order\n          uri: lb://yunque-store\n          predicates:\n            - Path=/order/**\n          filters:\n            - StripPrefix=1\n        # 营销模块\n        - id: yunque-market\n          uri: lb://yunque-market\n          predicates:\n            - Path=/market/**\n          filters:\n            - StripPrefix=1\n        # 文件服务\n        - id: yunque-file\n          uri: lb://yunque-file\n          predicates:\n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n\n# knife4j 网关聚合\nknife4j:\n  gateway:\n    enabled: true\n    # 指定服务发现的模式聚合微服务文档，并且是默认 default 分组\n    strategy: discover\n    discover:\n      # OpenAPI 3.0 规范 \n      version: openapi3\n      enabled: true\n      # 需要排除的微服务\n      excluded-services:\n        - yunque-auth\n        - yunque-monitor\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /code\n      - /auth/oauth2/token\n      - /auth/logout\n      - /doc.html\n      - /webjars/**\n      - /v3/api-docs/*\n      - /*/v3/api-docs\n      - /auth/token/register\n      - /system/admin/login/admin/getEnterpriseByDomainName\n      - /csrf','28c07124b11a3f6587e6ae562066d192','2024-03-18 09:14:31','2024-09-22 10:33:10',NULL,'0:0:0:0:0:0:0:1','','','网关模块','','','yaml','',''),(5,'yunque-auth-dev.yml','DEFAULT_GROUP','# spring配置\r\nspring:\r\n  data:\r\n    redis:\r\n      host: ${secret.redis.host}\r\n      port: ${secret.redis.port}\r\n      password: ${secret.redis.password}\r\n      database: ${secret.redis.database}\r\n','74142af8af5c8621fbefb00c2f176ba7','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','认证中心',NULL,NULL,'yaml',NULL,NULL),(6,'yunque-monitor-dev.yml','DEFAULT_GROUP','# spring配置\r\nspring:\r\n  security:\r\n    user:\r\n      name: ${secret.monitor.name}\r\n      password: ${secret.monitor.password}\r\n  boot:\r\n    admin:\r\n      ui:\r\n        title: ${secret.security.title}\r\n','84c63fcf0c9fadc553a51f32c4a8e7bd','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','监控中心',NULL,NULL,'yaml',NULL,NULL),(7,'yunque-tenant-dev.yml','DEFAULT_GROUP','xueyi:\r\n  # 租户配置\r\n  tenant:\r\n    # 公共表配置\r\n    common-table:\r\n      - sys_menu\r\n      - sys_module\r\n    # 非租户表配置\r\n    exclude-table:\r\n      - te_tenant\r\n      - te_strategy\r\n      - te_source\r\n\r\n# 租户库必需数据表\r\nsource-config:\r\n  slave-table:\r\n    - sys_dept\r\n    - sys_post\r\n    - sys_user\r\n    - sys_user_post_merge\r\n    - sys_role\r\n    - sys_role_module_merge\r\n    - sys_role_menu_merge\r\n    - sys_role_dept_merge\r\n    - sys_role_post_merge\r\n    - sys_organize_role_merge\r\n    - sys_operate_log\r\n    - sys_login_log\r\n    - sys_notice\r\n    - sys_notice_log\r\n    - sys_job_log\r\n    - sys_file\r\n    - sys_file_folder\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  # 搜索指定包别名\r\n  typeAliasesPackage: com.xueyi.tenant\r\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\r\n  mapperLocations: classpath:mapper/**/*.xml\r\n\r\n#seata配置\r\nseata:\r\n  # 服务配置项\r\n  service:\r\n    # 虚拟组和分组的映射\r\n    vgroup-mapping:\r\n      xueyi-tenant-group: default\r\n','78eae442b7312725bb57a933d5b55ba1','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','租户模块',NULL,NULL,'yaml',NULL,NULL),(8,'yunque-system-dev.yml','DEFAULT_GROUP','xueyi:\r\n  # 租户配置\r\n  tenant:\r\n    # 公共表配置\r\n    common-table:\r\n      - sys_menu\r\n      - sys_module\r\n    # 非租户表配置\r\n    exclude-table:\r\n      - te_tenant\r\n      - te_strategy\r\n      - sys_auth_group\r\n      - sys_auth_group_menu_merge\r\n      - sys_auth_group_module_merge\r\n      - sys_tenant_auth_group_merge\r\n      - sys_oauth_client\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  # 搜索指定包别名\r\n  typeAliasesPackage: com.xueyi.system\r\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\r\n  mapperLocations: classpath:mapper/**/*.xml\r\n\r\n#seata配置\r\nseata:\r\n  # 服务配置项\r\n  service:\r\n    # 虚拟组和分组的映射\r\n    vgroup-mapping:\r\n      xueyi-system-group: default\r\n','af0880f5bbead5f15cfb84903665fd4e','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','系统模块',NULL,NULL,'yaml',NULL,NULL),(9,'yunque-gen-dev.yml','DEFAULT_GROUP','xueyi:\r\n  # 租户配置\r\n  tenant:\r\n    # 非租户表配置\r\n    exclude-table:\r\n      - gen_table\r\n      - gen_table_column\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  # 搜索指定包别名\r\n  typeAliasesPackage: com.xueyi.gen\r\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\r\n  mapperLocations: classpath:mapper/**/*.xml\r\n  configuration:\r\n    jdbc-type-for-null: \'null\'\r\n\r\n# 代码生成\r\ngen: \r\n  # 作者\r\n  author: xueyi\r\n  # ui路径（空代表生成在后端主路径下，可设置为ui项目地址如：C:UsersxueyiMultiSaas-UI）\r\n  uiPath: \r\n  # 自动去除表前缀，默认是true\r\n  autoRemovePre: true\r\n  # 数据库映射\r\n  data-base:\r\n    # 字符串类型\r\n    type-str: [\"char\", \"varchar\", \"nvarchar\", \"varchar2\"]\r\n    # 文本类型\r\n    type-text: [\"tinytext\", \"text\", \"mediumtext\", \"longtext\"]\r\n    # 日期类型\r\n    type-date: [\"datetime\", \"time\", \"date\", \"timestamp\"]\r\n    # 时间类型\r\n    type-time: [\"datetime\", \"time\", \"date\", \"timestamp\"]\r\n    # 数字类型\r\n    type-number: [\"tinyint\", \"smallint\", \"mediumint\", \"int\", \"number\", \"integer\"]\r\n    # 长数字类型\r\n    type-long: [\"bigint\"]\r\n    # 浮点类型\r\n    type-float: [\"float\", \"double\", \"decimal\"]\r\n  # 字段配置\r\n  operate:\r\n    # 隐藏详情显示\r\n    not-view: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\r\n    # 隐藏新增显示\r\n    not-insert: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\r\n    # 隐藏编辑显示\r\n    not-edit: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\r\n    # 隐藏列表显示\r\n    not-list: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"remark\"]\r\n    # 隐藏查询显示\r\n    not-query: [\"id\", \"sort\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"remark\"]\r\n    # 隐藏导入显示\r\n    not-import: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\r\n    # 隐藏导出显示\r\n    not-export: [\"id\", \"sort\", \"createBy\", \"updateBy\"]\r\n  # 基类配置\r\n  entity:\r\n    # 必定隐藏字段（前后端均隐藏）\r\n    must-hide: [\"delFlag\", \"tenantId\", \"ancestors\"]\r\n    # 后端基类\r\n    back:\r\n      base: [\"id\", \"name\", \"status\", \"sort\", \"remark\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"delFlag\"]\r\n      tree: [\"parentId\", \"ancestors\", \"level\"]\r\n      tenant: [\"tenantId\"]\r\n      common: [\"isCommon\"]\r\n    # 前端基类\r\n    front:\r\n      base: [\"sort\", \"remark\", \"createBy\", \"createName\", \"createTime\", \"updateBy\", \"updateName\", \"updateTime\", \"delFlag\"]\r\n      tree: [\"parentId\", \"ancestors\", \"level\"]\r\n      tenant: [\"tenantId\"]\r\n      common: [\"isCommon\"]\r\n  # 表前缀（与remove-lists对应）\r\n  dict-type-remove: [\"sys_\", \"te_\"]\r\n  # 表更替配置\r\n  remove-lists:\r\n      # 表前缀（生成类名不会包含表前缀）\r\n    - prefix: sys_\r\n      # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\r\n      rdPackageName: com.xueyi.system\r\n      fePackageName: system\r\n      backPackageRoute: /xueyi-modules/xueyi-system\r\n    - prefix: te_\r\n      rdPackageName: com.xueyi.tenant\r\n      fePackageName: tenant\r\n      backPackageRoute: /xueyi-modules/xueyi-tenant\r\n','6958933075c2485ae7d720331854daca','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','代码生成',NULL,NULL,'yaml',NULL,NULL),(10,'yunque-job-dev.yml','DEFAULT_GROUP','# mybatis-plus配置\r\nmybatis-plus:\r\n  # 搜索指定包别名\r\n  typeAliasesPackage: com.xueyi.job\r\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\r\n  mapperLocations: classpath:mapper/**/*.xml\r\n\r\n# seata配置\r\nseata:\r\n  # 服务配置项\r\n  service:\r\n    # 虚拟组和分组的映射\r\n    vgroup-mapping:\r\n      xueyi-job-group: default\r\n','e12647d92e460c8fabec88a2eaec11a8','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','定时任务',NULL,NULL,'yaml',NULL,NULL),(11,'yunque-file-dev.yml','DEFAULT_GROUP','# spring配置\r\nspring:\r\n  data:\r\n    redis:\r\n      host: ${secret.redis.host}\r\n      port: ${secret.redis.port}\r\n      password: ${secret.redis.password}\r\n      database: ${secret.redis.database}\r\n\r\n# 本地文件上传\r\nfile:\r\n  domain: http://127.0.0.1:9300\r\n  path: D:/xueyi/uploadPath\r\n  prefix: /statics\r\n\r\n# FastDFS配置\r\nfdfs:\r\n  domain: http://8.129.231.12\r\n  soTimeout: 3000\r\n  connectTimeout: 2000\r\n  trackerList: 8.129.231.12:22122\r\n\r\n# Minio配置\r\nminio:\r\n  url: http://8.129.231.12:9000\r\n  accessKey: minioadmin\r\n  secretKey: minioadmin\r\n  bucketName: test\r\n\r\nsecurity:\r\n  oauth2:\r\n    ignore:\r\n      whites:\r\n        routine:\r\n          - ${file.prefix}/**\r\n','dedd21923e079b46c9989ddd634abad5','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','文件服务',NULL,NULL,'yaml',NULL,NULL),(12,'sentinel-xueyi-gateway','DEFAULT_GROUP','[\r\n    {\r\n        \"resource\": \"xueyi-auth\",\r\n        \"count\": 500,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"xueyi-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"xueyi-tenant\",\r\n        \"count\": 500,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"xueyi-gen\",\r\n        \"count\": 200,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },\r\n	{\r\n        \"resource\": \"xueyi-job\",\r\n        \"count\": 300,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]','ef3f8fbcef9fde149d2b6b36cf857b30','2024-03-18 09:14:31','2024-03-18 09:14:31',NULL,'10.161.9.10','','','限流策略',NULL,NULL,'json',NULL,NULL);
/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8mb3_bin,
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (2,1,'application-secret-dev.yml','DEFAULT_GROUP','','secret:\r\n  # redis参数信息\r\n  redis:\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    password:\r\n    database: 0\r\n  # 服务状态监控参数信息\r\n  monitor:\r\n    name: xueyi\r\n    password: xueyi123\r\n    title: 服务状态监控\r\n  # swagger参数信息\r\n  swagger:\r\n    author:\r\n      name: xueyi\r\n      email: xueyitt@qq.com\r\n    version: v3.0.2\r\n    title: 接口文档\r\n    license: Powered By xueyi\r\n    licenseUrl: https://doc.xueyitt.cn\r\n  # datasource主库参数信息\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/xy-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: password\r\n  # nacos参数信息\r\n  nacos:\r\n    serverAddr: 127.0.0.1:8848\r\n    namespace:\r\n    username:\r\n    password:','5be582049ec3fdcb391a17807e8cabbd','2024-09-15 17:34:49','2024-09-15 09:34:49',NULL,'0:0:0:0:0:0:0:1','U','',''),(4,2,'xueyi-gateway-dev.yml','DEFAULT_GROUP','','# spring配置\r\nspring:\r\n  data:\r\n    redis:\r\n      host: ${secret.redis.host}\r\n      port: ${secret.redis.port}\r\n      password: ${secret.redis.password}\r\n      database: ${secret.redis.database}\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          # 启用服务发现\r\n          enabled: true\r\n          lowerCaseServiceId: true\r\n      # 网关路由\r\n      routes:\r\n        # 认证中心\r\n        - id: xueyi-auth\r\n          uri: lb://xueyi-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - CacheRequestFilter\r\n            - ValidateCodeFilter\r\n            - StripPrefix=1\r\n        # 代码生成\r\n        - id: xueyi-gen\r\n          uri: lb://xueyi-gen\r\n          predicates:\r\n            - Path=/code/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 定时任务\r\n        - id: xueyi-job\r\n          uri: lb://xueyi-job\r\n          predicates:\r\n            - Path=/schedule/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 系统模块\r\n        - id: xueyi-system\r\n          uri: lb://xueyi-system\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 租户模块\r\n        - id: xueyi-tenant\r\n          uri: lb://xueyi-tenant\r\n          predicates:\r\n            - Path=/tenant/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 店铺模块\r\n        - id: xueyi-store\r\n          uri: lb://xueyi-store\r\n          predicates:\r\n            - Path=/store/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 会员模块\r\n        - id: xueyi-member\r\n          uri: lb://xueyi-store\r\n          predicates:\r\n            - Path=/member/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 商品模块\r\n        - id: xueyi-product\r\n          uri: lb://xueyi-store\r\n          predicates:\r\n            - Path=/product/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 订单模块\r\n        - id: xueyi-order\r\n          uri: lb://xueyi-store\r\n          predicates:\r\n            - Path=/order/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 营销模块\r\n        - id: xueyi-market\r\n          uri: lb://xueyi-market\r\n          predicates:\r\n            - Path=/market/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 文件服务\r\n        - id: xueyi-file\r\n          uri: lb://xueyi-file\r\n          predicates:\r\n            - Path=/file/**\r\n          filters:\r\n            - StripPrefix=1\r\n\r\n# knife4j 网关聚合\r\nknife4j:\r\n  gateway:\r\n    enabled: true\r\n    # 指定服务发现的模式聚合微服务文档，并且是默认 default 分组\r\n    strategy: discover\r\n    discover:\r\n      # OpenAPI 3.0 规范 \r\n      version: openapi3\r\n      enabled: true\r\n      # 需要排除的微服务\r\n      excluded-services:\r\n        - xueyi-auth\r\n        - xueyi-monitor\r\n\r\n# 安全配置\r\nsecurity:\r\n  # 验证码\r\n  captcha:\r\n    enabled: true\r\n    type: math\r\n  # 防止XSS攻击\r\n  xss:\r\n    enabled: true\r\n    excludeUrls:\r\n      - /system/notice\r\n  # 不校验白名单\r\n  ignore:\r\n    whites:\r\n      - /code\r\n      - /auth/oauth2/token\r\n      - /auth/logout\r\n      - /doc.html\r\n      - /webjars/**\r\n      - /v3/api-docs/*\r\n      - /*/v3/api-docs\r\n      - /auth/token/register\r\n      - /system/admin/login/admin/getEnterpriseByDomainName\r\n      - /csrf','896cb4f6a13f3eeafb5186ec5b7991ed','2024-09-22 18:33:10','2024-09-22 10:33:10',NULL,'0:0:0:0:0:0:0:1','U','','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(255) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('nacos','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'yunque-config'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-22 18:46:13
