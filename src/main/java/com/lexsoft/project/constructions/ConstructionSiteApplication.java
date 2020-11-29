package com.lexsoft.project.constructions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConstructionSiteApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ConstructionSiteApplication.class, args);
	}

}
