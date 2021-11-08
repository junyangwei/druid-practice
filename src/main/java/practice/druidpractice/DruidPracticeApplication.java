package practice.druidpractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("practice.druidpractice.mapper")
public class DruidPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DruidPracticeApplication.class, args);
	}

}
