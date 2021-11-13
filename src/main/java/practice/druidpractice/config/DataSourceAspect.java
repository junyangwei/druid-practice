package practice.druidpractice.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * 第九步：配置 AOP 的切面
 * @author junyangwei
 * @date 2021-11-13
 */
@Aspect
@Configuration
public class DataSourceAspect {
    public DataSourceAspect(){
        System.out.println("this is init");
    }

    /**
     * 指定触发时机是使用 @UsingDataSource 注解
     */
    @Pointcut("@within(practice.druidpractice.config.UsingDataSource) || " +
            "@annotation(practice.druidpractice.config.UsingDataSource)")
    public void pointCut(){

    }

    /**
     * 在执行方法之前，指定数据源
     * @param usingDataSource 使用注解
     */
    @Before("pointCut() && @annotation(usingDataSource)")
    public void doBefore(UsingDataSource usingDataSource){
        System.out.println("select datasource ----" + usingDataSource.type());
        DataSourceContextHolder.setDatabaseHolder(usingDataSource.type());
    }

    /**
     * 在当前线程执行完毕后，销毁本地线程变量中的副本，防止出错
     */
    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
