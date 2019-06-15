package br.com.davsantos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.davsantos.services.DBService;

@Configuration
@Profile("hml")
public class HMLDBConfig {
	
	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		dbService.instantiateHmlDataBase();
		
		return true;
	}
}
