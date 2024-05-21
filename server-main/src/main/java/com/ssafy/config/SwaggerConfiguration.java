package com.ssafy.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("enjoytrips-all").pathsToMatch("/**").build();
	}

	@Bean
	public GroupedOpenApi boardApi() {
		return GroupedOpenApi.builder().group("enjoytrips-board").pathsToMatch("/board/**").build();
	}
	
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("enjoytrips-user").pathsToMatch("/user/**").build();
	}
	
	@Bean
	public GroupedOpenApi mapApi() {
		return GroupedOpenApi.builder().group("enjoytrips-plan").pathsToMatch("/plan/**").build();
	}


}
