package com.ftk.pg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ftk.pg.restcontroller.PingController;

@SpringBootApplication
//We use direct @Import instead of @ComponentScan to speed up cold starts
//@ComponentScan(basePackages = "com.controller")
@Import({ PingController.class })
@EnableJpaAuditing
//@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableAsync
public class PaymentpagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentpagesApplication.class, args);
	}

}
