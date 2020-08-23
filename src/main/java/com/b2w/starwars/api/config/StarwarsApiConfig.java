package com.b2w.starwars.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

//classe de configuração usada para definir Beans
@PropertySource("classpath:application.properties")
@Configuration
public class StarwarsApiConfig {	
	
	@Value("${swapi.base.url}")
    private String swApiBaseUrl;
	
	@Bean
	public WebClient webClientSwApi(WebClient.Builder builder) {
		return builder
			.baseUrl(swApiBaseUrl)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

}
