package com.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableScheduling
public class TokenDemoApplication {

	@Bean
	public RestTemplate getRestTemplate(){
		System.out.println("Rest Template Bean initialized...");
		return new RestTemplate();
	}

}
