package ru.tinkoff.yould;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class YouldApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouldApplication.class, args);
	}

}
