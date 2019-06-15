package br.com.davsantos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.davsantos.services.DBService;

@Configuration
@Profile("prd")
public class PRDDBConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		if (!"none".equals(strategy)) {
			return false;
		}
		
		dbService.instantiateHmlDataBase();
		
		return true;
	}
}