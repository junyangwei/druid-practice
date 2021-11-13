package practice.druidpractice.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 第四步：使用 SpringBoot 的 Import 选择器去自定义一个注册器
 *  - 目的是获取启动类注解 @AppDataSource 配置的数据源，方便做下一步初始化的操作
 *  - 这里获取到了设置的数据源后，调用了 DataSourceContextHolder 的方法，添加数据源
 * @author junyangwei
 * @date 2021-11-13
 */
public class DataSourceConfigRegister implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        // 获取 @AppDataSource 注解中的属性
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(
                        annotationMetadata.getAnnotationAttributes(
                                AppDataSource.class.getName()));

        System.out.println("###### datasource import ######");

        if (null != attributes) {
            // 获取属性中配置的 dataSourceType 值（SupportDataSourceEnum[]的枚举值）
            SupportDataSourceEnum[] enums = (SupportDataSourceEnum[]) attributes.get("dataSourceType");

            // 添加配置的数据源
            for (SupportDataSourceEnum supportDataSourceEnum : enums) {
                DataSourceContextHolder.addDataSource(supportDataSourceEnum);
            }
        }
        return new String[0];
    }
}
