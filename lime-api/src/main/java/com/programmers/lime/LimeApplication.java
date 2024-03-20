package com.programmers.lime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan({"com.programmers.lime.global.config.security.jwt", "com.programmers.lime.domains.auth"})
public class LimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimeApplication.class, args);
	}

}
