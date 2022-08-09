package com.example.rqchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import okhttp3.OkHttpClient;

@SpringBootApplication
public class RqChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RqChallengeApplication.class, args);
	}

	@Bean
	public OkHttpClient getOkHttpClient() {
		return new OkHttpClient();
	}
}
