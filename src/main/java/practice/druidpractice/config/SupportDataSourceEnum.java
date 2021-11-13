package practice.druidpractice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第二步：配置所有要使用的数据源枚举信息，以及定义连接数据库基本信息
 *  - 这里配置了 MASTER 和 SLAVER
 *  - MASTER 指定的是可读写的账号权限（这里偷懒有 root ）
 *  - SLAVER 指定的是只读的账号权限
 * @author junyangwei
 * @date 2021-11-13
 */
@AllArgsConstructor
@Getter
public enum SupportDataSourceEnum {
    MASTER("jdbc:mysql://127.0.0.1:3306/sell?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull", "root", "123456", "sell"),
    SLAVER("jdbc:mysql://127.0.0.1:3306/sell?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull", "slaver", "112233", "sell");

    String url;
    String username;
    String password;
    String databaseName;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
