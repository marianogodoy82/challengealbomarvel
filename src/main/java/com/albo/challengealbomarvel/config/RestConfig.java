package com.albo.challengealbomarvel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RestConfig {
	@Bean
	public RestTemplate marvelApiRestTemplate() {
		log.debug("Inicializando cliente Marvel...");

		// Inicializando el template
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate;
	}
}
