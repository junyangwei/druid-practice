package practice.druidpractice;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器
 * 	- 使用 shardingsphere-jdbc 来手动构造数据源，因此关闭 Druid 自动配置
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("practice.druidpractice.mapper")
public class DruidPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DruidPracticeApplication.class, args);
	}

}
