package practice.druidpractice.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 第七步：初始化动态的数据源，使用 Druid 建立数据库连接池
 * @author junyangwei
 * @date 2021-11-13
 */
@Component
public class DynamicDataSourceConfiguration {

    /**
     * 初始化数据库
     * @return DataSource
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        System.out.println("###### 初始化数据源 ######");

        // 构造动态数据源类对象
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 定义需要记录"数据源名称-数据库连接池"键值对的 Map (这里设置为2是因为我只配置了两个数据源)
        HashMap dataSourcesMap = new HashMap<>(2);

        // 获取当前配置的所有数据源集合（本质上获取的是启动器 @AppDataSource 注解中设置的数据库）
        HashSet<SupportDataSourceEnum> set = DataSourceContextHolder.getDataSourceSet();

        // 根据获取的配置信息，建立数据库连接池
        for (SupportDataSourceEnum supportDataSourceEnum : set) {
            // 建立数据库连接池
            DataSource dataSource = this.createDataSourceProperties(supportDataSourceEnum);

            // 记录
            dataSourcesMap.put(supportDataSourceEnum.toString(), dataSource);
        }

        // 设置备选的数据源
        dynamicDataSource.setTargetDataSources(dataSourcesMap);
        // 设置默认的数据源
        dynamicDataSource.setDefaultTargetDataSource(createDataSourceProperties(SupportDataSourceEnum.MASTER));

        return dynamicDataSource;
    }

    /**
     * 创建数据源连接池
     *  - 使用 Druid 作为数据库连接池
     * @param supportDataSourceEnum 数据源配置信息枚举类
     * @return DataSource
     */
    private synchronized DataSource createDataSourceProperties(SupportDataSourceEnum supportDataSourceEnum) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(supportDataSourceEnum.getUrl());
        druidDataSource.setUsername(supportDataSourceEnum.getUsername());
        druidDataSource.setPassword(supportDataSourceEnum.getPassword());
        // 特殊的配置
        druidDataSource.setMaxActive(100);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxWait(30000);
        // 检测空闲连接间隔时间（毫秒)
        druidDataSource.setTimeBetweenConnectErrorMillis(60000);
        return druidDataSource;
    }
}
