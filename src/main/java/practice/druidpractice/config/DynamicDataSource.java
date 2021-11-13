package practice.druidpractice.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 第六步：
 *  - AbstractRoutingDataSource 是 Spring 定义的数据源动态路由的抽象类
 *  - 核心方法是 determineCurrentLookupKey，这个方法适合做一些查询数据源密钥之类的逻辑
 * @author junyangwei
 * @date 2021-11-13
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 这里直接获取本地线程存储的数据源配置信息，得到这部分信息
        String dataSource = DataSourceContextHolder.getDatabaseHolder();
        return dataSource;
    }
}
