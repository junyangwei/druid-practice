package practice.druidpractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import practice.druidpractice.config.AppDataSource;
import practice.druidpractice.config.SupportDataSourceEnum;

/**
 * 启动器
 * 	- 在启动类中需要加载数据库配置（步骤三）
 */
@SpringBootApplication
@MapperScan("practice.druidpractice.mapper")
@AppDataSource(dataSourceType = {SupportDataSourceEnum.MASTER, SupportDataSourceEnum.SLAVER})
public class DruidPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DruidPracticeApplication.class, args);
	}

}
