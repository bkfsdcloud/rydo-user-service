package com.adroitfirm.rydo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.adroitfirm.rydo.user.*","com.adroitfirm.rydo.geo.*"})
@SpringBootApplication
public class RydoUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RydoUserServiceApplication.class, args);
	}

}
