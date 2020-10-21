package com.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
@EnableAsync
@EnableScheduling
public class TokenDemoApplication {

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	@PostConstruct
	private void init(){
		System.out.println("Token Generator service initialized...");
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	public void cronJobSch() throws Exception {
		System.out.println("Token scheduler service job run at ::"+java.time.LocalTime.now());
	}
}
