<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">yunque-cloud</h1>
<h4 align="center">基于 Vue3/TypeScript/Ant-Design UI 和 Spring Cloud & Alibaba/Mybatis-Plus 的开发框架。</h4>

## 简介

基于SpringBoot | Spring Cloud & Alibaba | Mybatis-Plus | Vue3 | vite4 | TypeScript | Ant-Design-Vue UI
的微服务开发框架，为企业级应用提供快速开发解决方案。

本项目基于[XueYi-MultiSaas](https://gitee.com/xueyitiantang/XueYi-MultiSaas)开发，去掉了多租户功能，并针对企业级业务进行优化。

## 交流

- 请移步右上角  **一键三连** :kissing_heart:
- 点击链接加入群聊【yunque-cloud交流群】：https://qm.qq.com/q/MFAIcNIZk4
- 若发现bug，请提Issues。

## 预览

- **普通账户** 
  账号：admin   
  密码：admin123

- **文档**
  > [doc.xueyitt.cn](https://doc.xueyitt.cn)

## 结构

* 采用前后端分离的模式，微服务版本前端。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。

~~~
com.xueyi     
├── multi-ui              // vue3前端 [3000]
├── xueyi-gateway         // 网关模块 [8080]
├── xueyi-auth            // 认证中心 [9200]
├── xueyi-api             // 接口模块
│       ├── xueyi-api-file                            // 文件接口
│       ├── xueyi-api-system                          // 系统接口
│       └── xueyi-api-job                             // 调度接口
├── xueyi-common          // 通用模块
│       ├── xueyi-common-cache                        // 缓存管理
│       ├── xueyi-common-core                         // 核心模块
│       ├── xueyi-common-datascope                    // 权限范围
│       ├── xueyi-common-datasource                   // 多数据源
│       ├── xueyi-common-dependency                   // 基础依赖
│       ├── xueyi-common-log                          // 日志记录
│       ├── xueyi-common-redis                        // 缓存服务
│       ├── xueyi-common-mail                         // 邮件模块
│       ├── xueyi-common-seata                        // 事务模块
│       ├── xueyi-common-mq                           // 消息队列
│               ├── xueyi-common-mq-redis             // 消息队列 - Redis
│               ├── xueyi-common-mq-mqtt              // 消息队列 - EMQ-X
│               ├── xueyi-common-mq-rabbit            // 消息队列 - RabbitMQ
│               ├── xueyi-common-mq-rocket            // 消息队列 - RocketMQ
│               └── xueyi-common-mq-kafka             // 消息队列 - Kafka
│       ├── xueyi-common-sms                          // 短信模块
│       ├── xueyi-common-security                     // 安全模块
│       ├── xueyi-common-swagger                      // 系统接口
│       └── xueyi-common-web                          // 租户模块
├── xueyi-modules         // 业务模块
│       ├── xueyi-file                                // 文件服务 [9300]
│       ├── xueyi-gen                                 // 代码生成 [9400]
│       ├── xueyi-job                                 // 定时任务 [9500]
│       ├── xueyi-system                              // 系统模块 [9600]
│               ├── authority                         // 权限模块
│               ├── dict                              // 参数字典
│               ├── file                              // 文件模块
│               ├── monitor                           // 监控模块
│               ├── notice                            // 公告模块
│               └── organize                          // 组织模块
├── xueyi-visual          // 图形化管理模块
│       └── xueyi-visual-monitor                      // 监控中心 [9100]
└── pom.xml                // 公共依赖
~~~

## 架构

<img src="https://images.gitee.com/uploads/images/2021/1108/172436_9deff9ff_7382127.png"/>

## 演示

<table>
    <tr>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/1.png" alt=""/></td>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/2.png" alt=""/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/3.png" alt=""/></td>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/4.png" alt=""/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/5.png" alt=""/></td>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/6.png" alt=""/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/7.png" alt=""/></td>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/8.png" alt=""/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/9.png" alt=""/></td>
        <td><img src="https://gitee.com/xueyitiantang/images/raw/master/10.png" alt=""/></td>
    </tr>
</table>

## 开源

**源于开源，回归开源**

* 感谢雪忆天堂开源的[XueYi-MultiSaas](https://gitee.com/xueyitiantang/XueYi-MultiSaas)
* 感谢Vben开源的[vue-vben-admin](https://github.com/vbenjs/vue-vben-admin)
* 感谢苞米豆开源的[mybatis-plus](https://github.com/baomidou/mybatis-plus)
* 感谢小锅盖开源的[dynamic](https://gitee.com/baomidou/dynamic-datasource-spring-boot-starter)