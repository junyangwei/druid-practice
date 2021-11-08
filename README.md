## 目标

1. 下载 druid 源码
2. 结合 mybatis / jdbc / ORM 框架实现简单的CRUD
3. 实践 druid 的参数配置
4. 额外：druid 的监控

## 下载 Druid 源码

源码仓库地址：https://github.com/alibaba/druid

本地拉代码命令：`git@github.com:alibaba/druid.git`

## 结合 mybatis / jdbc / ORM 框架实现简单的 CRUD
这个工程以 SpringBoot + Mybatis + Druid 实现了简单的用户表(user)的增删查改操作

数据库用户表结构如下
```mysql
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码（使用MD5+Salt保存）',
  `nickname` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(15) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 0无效 1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

## 实践 Druid 的配置参数
[详见配置文件的Druid属性配置](https://github.com/junyangwei/druid-practice/blob/main/src/main/resources/application.yml)

## 试一试
项目启动后，可以通过一下操作进行测试：
- 查用户：http://localhost:8088/api/user/get?id=1
- 增加用户：http://localhost:8088/api/user/insert
- 更新用户：http://localhost:8088/api/user/update?id=23&nickname=updatetest
- 删除用户：http://localhost:8088/api/user/delete?id=23

注意：因为时间匆忙，因此上述接口使用的都是 GET 请求

获取Druid的监控数据
- 工程启动后访问链接：http://localhost:8088/api/druid/stat