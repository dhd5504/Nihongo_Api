package com.app.nihongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NihongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NihongoApplication.class, args);
	}

}
