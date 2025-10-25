package com.adroitfirm.rydo.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.adroitfirm.rydo.utility.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {

	@Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
	
	@Bean
	ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	JwtUtils jwtUtils(@Value("${jwt.secret}") String secretKey,
	                         @Value("${jwt.expirationMs}") long expirationMs) {
	    return new JwtUtils(secretKey, expirationMs);
	}
}
