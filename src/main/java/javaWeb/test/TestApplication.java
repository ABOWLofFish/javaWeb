package javaWeb.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("javaWeb.test.mapper")
@EnableCaching//开启缓存
//@EnableScheduling//后台开启定时任务
public class TestApplication {

	public static void main(String[] args) {
		System.out.println("======main Run======");
		SpringApplication.run(TestApplication.class, args);
	}

}