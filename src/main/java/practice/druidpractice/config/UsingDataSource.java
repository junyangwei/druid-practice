package practice.druidpractice.config;

import java.lang.annotation.*;

/**
 * 第八步：设置一个指定使用数据源的注解，用于给当前线程指定数据源
 * @author junyangwei
 * @date 2021-11-13
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UsingDataSource {
    SupportDataSourceEnum type();
}
