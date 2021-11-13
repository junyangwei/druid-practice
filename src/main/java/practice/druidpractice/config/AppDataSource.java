package practice.druidpractice.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第一步：注入数据源
 *  - 我们需要一个自定义数据源信息，能让我们自己指定需要的数据源
 *  - 参考：https://developpaper.com/spring-boot-aop-build-multi-data-source-switching-practice/
 * @author junyangwei
 * @date 2021-11-13
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataSourceConfigRegister.class)
public @interface AppDataSource {

    // 用于指定数据库名称
    SupportDataSourceEnum[] dataSourceType();
}
