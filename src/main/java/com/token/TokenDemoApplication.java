package com.token;

import com.token.Model.UserJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
@EnableScheduling
@ComponentScan("com.token")
@ConditionalOnProperty(value = "token.authentication.enabled")
@RefreshScope
public class TokenDemoApplication {
	public int counter=4;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		System.out.println("Rest Template Bean initialized...");
		return new RestTemplate();
	}

	@Bean("sprxTaskScheduler")
	public TaskScheduler taskScheduler(){
		return new ConcurrentTaskScheduler();
	}

	@PostConstruct
	private void init(){
		System.out.println("Token Generator service initialized...");
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	private void cronJobSch() throws Exception {
		saveAutoUser(counter++);
		System.out.println("Token scheduler service job run at ::"+java.time.LocalTime.now());
	}

	void saveAutoUser(int counter) {
		String sql = "insert into users( name, email) values (?, ?)";
		jdbcTemplate.update(sql, "JobUser"+counter, "Job"+counter+"@gmail.com");
	}
}
