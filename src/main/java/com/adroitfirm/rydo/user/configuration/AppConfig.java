package com.adroitfirm.rydo.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

	@Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
	
	@Bean
	ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
