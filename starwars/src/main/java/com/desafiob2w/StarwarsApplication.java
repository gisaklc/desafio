package com.desafiob2w;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.desafiob2w.controller.PlanetaController;

@SpringBootApplication
@EnableScheduling
public class StarwarsApplication{ //implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StarwarsApplication.class, args);
	}




}
