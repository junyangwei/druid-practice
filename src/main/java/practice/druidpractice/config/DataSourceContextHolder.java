package practice.druidpractice.config;

import java.util.HashSet;

/**
 * 第五步：
 *  - 在多并发场景中，为了防止数据源被不同的线程相互调用，使用本地线程来处理
 *  - 每个线程被分配一个属于其内部副本的指定副本变量
 *  - 在当前线程结束之前，需要记住，要销毁相应的线程副本
 * @author junyangwei
 * @date 2021-11-13
 */
public class DataSourceContextHolder {

    /**
     * 存储数据源枚举信息的集合
     */
    private static final HashSet<SupportDataSourceEnum> dataSourceSet = new HashSet<>();

    /**
     * 用于记录需要的数据库配置信息的本地线程
     */
    private static final ThreadLocal databaseHolder = new ThreadLocal<>();

    /**
     * 将需要的数据库配置信息，记录到本地线程中变量中存储
     *
     * @param supportDataSourceEnum 需要的数据库配置枚举信息
     */
    public static void setDatabaseHolder(SupportDataSourceEnum supportDataSourceEnum) {
        databaseHolder.set(supportDataSourceEnum.toString());
    }

    /**
     * 获取本地线程变量存储的数据库配置信息
     */
    public static String getDatabaseHolder() {
        return (String) databaseHolder.get();
    }

    /**
     * 添加数据库配置枚举信息到 dataSourceSet 集合中
     *
     * @param supportDataSourceEnum 需要的数据库配置枚举信息
     */
    public static void addDataSource(SupportDataSourceEnum supportDataSourceEnum) {
        dataSourceSet.add(supportDataSourceEnum);
    }

    /**
     * 存储数据源枚举信息的集合
     */
    public static HashSet<SupportDataSourceEnum> getDataSourceSet() {
        return dataSourceSet;
    }

    /**
     * 清除数据内容
     */
    public static void clear() {
        databaseHolder.remove();
    }
}
