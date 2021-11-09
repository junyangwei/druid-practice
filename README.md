## 目标

1. 下载 druid 源码
2. 结合 mybatis / jdbc / ORM 框架实现简单的CRUD
3. 实践 druid 的参数配置
4. 额外：druid 的监控

### 下载 durid 源码

源码仓库地址：https://github.com/alibaba/druid

本地拉代码命令：`git clone git@github.com:alibaba/druid.git`

### 结合 mybatis / jdbc / ORM 框架实现简单的 CRUD

今天练习 Druid 采用 `Spring Boot + Mybatis + Druid + MySQL` 来实现简单的增/删/查/改操作

#### 设计数据库表

本次决定设计一个用户表，对用户表的数据进行增/删/查改

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



#### 初始化 Spring Boot 工程

- 在 [Spring 官网](https://start.spring.io/) 初始化一个 Spring Boot 工程



#### 引入基本的依赖

核心依赖包：

- spring-boot-starter-web: 将该工程作为一个普通的后端 API 服务所需要的依赖包
- druid-spring-boot-start: 引入 Druid 兼容 Spring Boot 的依赖包
- mysql-connector-java: 引入连接 MySQL 数据库所需要的依赖包
- mybatis-spring-boot-starter: 引入 Mybatis 兼容 Spring Boot 的依赖包

非核心依赖包：

- lombk: 减少对类的 set 和 get 操作所需要的依赖包

```xml
	<dependencies>
		<!-- SpringBoot 常规依赖 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- 引入SpringBoot的 starter web，将此项目作为API服务 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- 引入 mysql 连接，连接 MySQL 数据库 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.26</version>
		</dependency>
		<!-- 引入 mybatis 连接 MySQL 数据库 -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.2.0</version>
		</dependency>
		<!-- 引入 druid 对 spring boot 的兼容包-->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.2.8</version>
		</dependency>
    
		<!-- 引入 lombok，省去重复写类的 set 和 get 方法 -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.20</version>
			<scope>provided</scope>
		</dependency>
	</dependencies>
```



#### 目录结构概要

```tex
src
  |----main
  |    |----java
  |    |    |----practice
  |    |    |    |----druidpractice
  |    |    |    |    |----controller
  |    |    |    |         |----UserController.java
  |    |    |    |         |----DruidStatController.java
  |    |    |    |    |----mapper
  |    |    |    |         |----UserMapper.java
  |    |    |    |    |----po
  |    |    |    |         |----User.java
  |    |    |    |    |----server
  |    |    |    |         |----impl
  |    |    |    |              |----UserServerImpl.java
  |    |    |    |         |----UserServer.java
  |    |    |    |    |----DruidPracticeApplication.java
  |    |    |    |----resources
  |    |    |    |    |----mybatis
  |    |    |    |         |----UserMapper.xml
  |    |    |    |    |----application.yml
```



#### 配置工程基础属性

##### application.yml 配置文件

```yml
# 配置工程启动端口、访问主路径、UTF-8编码
server:
  port: 8088
  servlet:
    context-path: /api
    encoding:
      force: true
      charset: UTF-8

# 配置 MySQL 数据库连接地址
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/notes?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull
    username: notes
    password: test
    driver-class-name: com.mysql.cj.jdbc.Driver

# 配置 Mybatis 下划线转驼峰、mapper的xml文件路径
mybatis:
  configuration:
    map-underscore-to-camel-case: true
  mapper-locations:
    - classpath:mybatis/*.xml
```

##### 启动类

```java
@SpringBootApplication
@MapperScan("practice.druidpractice.mapper")  // 配置 mapper 扫描路径
public class DruidPracticeApplication {
	public static void main(String[] args) {
		SpringApplication.run(DruidPracticeApplication.class, args);
	}
}
```



#### 编写增删查改代码

##### UserController.java

这里偷懒，全部使用 @GetMapping 注解，而没有遵守标准的 Restful API 风格

```java
@RestController
@RequestMapping("user")
public class UserController {
    @Resource(name = "userServer")
    private UserServer userServer;

    @GetMapping("/get")
    public Object getUser(@Param("id") int id) {
        return this.userServer.getUser(id);
    }

    @GetMapping("/insert")
    public Object insertUser() {
        return this.userServer.insertUser();
    }

    @GetMapping("/delete")
    public Object deleteUser(@Param("id") int id) {
        return this.userServer.deleteUser(id);
    }

    @GetMapping("/update")
    public Object updateUser(@Param("id") int id, @Param("nickname") String nickname) {
        return this.userServer.updateUser(id, nickname);
    }
}
```

##### UserServer.java

```java
public interface UserServer {
    /**
     * 获取用户信息
     */
    User getUser(int id);
    /**
     * 写入数据测试
     */
    int insertUser();
    /**
     * 删除数据测试
     * @param id 用户ID
     */
    int deleteUser(int id);
    /**
     * 更新数据测试
     */
    int updateUser(int id, String nickname);
}
```

##### UserServerImpl.java

````java
@Service("userServer")
public class UserServerImpl implements UserServer {
    @Resource
    private UserMapper userMapper;
    /**
     * 获取用户信息
     */
    @Override
    public User getUser(int id) {
        return this.userMapper.selectUser(id);
    }
    /**
     * 写入数据测试
     */
    @Override
    public int insertUser() {
        User user = new User();
        user.setUsername("test1108");
        user.setPassword("test1108password");
        user.setNickname("test1108nickname");
        user.setPhone("19000000100");
        return this.userMapper.insertUser(user);
    }
    /**
     * 删除数据测试
     * @param id 用户ID
     */
    @Override
    public int deleteUser(int id) {
        return this.userMapper.deleteUser(id);
    }
    /**
     * 更新数据测试
     */
    @Override
    public int updateUser(int id, String nickname) {
        return this.userMapper.updateUser(id, nickname);
    }
}
````

##### UserMapper.java

```java
@Repository
public interface UserMapper {
    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User selectUser(int id);
    /**
     * 写入用户信息
     * @param user 用户类对象
     * @return int
     */
    int insertUser(User user);
    /**
     * 删除用户信息
     * @param id 用户ID
     * @return int
     */
    int deleteUser(int id);
    /**
     * 更新用户信息
     * @param id 用户ID
     * @param nickname 昵称
     * @return int
     */
    int updateUser(@Param("id") int id,
                   @Param("nickname") String nickname);
}
```

##### UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC
        "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="practice.druidpractice.mapper.UserMapper">
    <select id="selectUser" resultType="practice.druidpractice.po.User">
        SELECT *
        FROM user
        WHERE id = #{id};
    </select>

    <insert id="insertUser" useGeneratedKeys="true" keyProperty="id" parameterType="practice.druidpractice.po.User">
        INSERT INTO user
        (username, password, nickname, phone, status)
        VALUES
        (#{username}, #{password}, #{nickname}, #{phone}, 1);
    </insert>

    <delete id="deleteUser" parameterType="int">
        DELETE FROM user
        WHERE id = #{id};
    </delete>

    <update id="updateUser" parameterType="practice.druidpractice.po.User">
        UPDATE user
        SET nickname = #{nickname}
        WHERE id = #{id};
    </update>

</mapper>
```



#### 启动程序测试

启动项目，并且调用增/删/查/改接口，查看结果

- 查用户：http://localhost:8088/api/user/get?id=1
- 增加用户：http://localhost:8088/api/user/insert
- 更新用户：http://localhost:8088/api/user/update?id=23&nickname=updatetest
- 删除用户：http://localhost:8088/api/user/delete?id=23



### 实践 druid 的参数配置

#### application.yml 配置文件

```yml
server:
...


spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/notes?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull
    username: notes
    password: test
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 配置 Druid 的连接池
    druid:
      # 初始化时物理链接个数
      initial-size: 3
      # 最大连接池数量
      max-active: 8
      # 最小连接池数量
      min-idle: 3
      # 获取连接时最大等待时间，单位毫秒
      max-wait: 3000
      # 是否缓存 preparedStatement，MySQL 下建议关闭
      pool-prepared-statements: false
      # 要启用PSCache，配置大于0时上一项会被自动改为 true
      max-pool-prepared-statement-per-connection-size: -1
      # 用来检测连接是否有效的sql
      validation-query:
      # 检测连接是否有效的超时时间，单位秒
      validation-query-timeout: 3
      # 申请连接时执行 validationQuery 检测连接是否有效，会降低性能
      test-on-borrow: false
      # 归还连接时执行 validationQuery 检测连接是否有效，会降低性能
      test-on-return: false
      # 建议配置为 true，不影响性能，并保证安全性；申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
      test-while-idle: true
      # 连接池中的minIdle数量以内的链接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作
      keep-alive: false
      # 两个含义：销毁线程会检测连接间隔时间，testWhileIdle的判断依据
      time-between-eviction-runs-millis: 60000
      # 连接保持空闲而不被驱逐的最小时间
      min-evictable-idle-time-millis: 600000
      # 扩展插件配置，如监控统计stat，日志log4j，防止SQL注入wall
      filters: stat,wall #配置多个英文逗号分隔

mybatis:
...
```

[配置 Druid 参数参考](https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8)



### Druid 的监控

#### 获取 Druid 的监控数据

##### 编写 DruidStatController 类

```java
@RestController
@RequestMapping("druid")
public class DruidStatController {
    @GetMapping("/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
```

#### 访问获取数据接口

- 调用接口：http://localhost:8088/api/druid/stat

[参考链接](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)

