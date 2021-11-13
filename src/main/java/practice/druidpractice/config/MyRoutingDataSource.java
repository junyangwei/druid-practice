package practice.druidpractice.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 配置自定义的数据源路由
 * @author junyangwei
 * @date 2021-11-13
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String language = LocaleContextHolder.getLocale().getLanguage();
        System.out.println("Language obtained: " + language);
        return language;
    }
}
