package com.faccat.sistemasdistribuidos.g2.jogodistribuido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JogodistribuidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JogodistribuidoApplication.class, args);
	}

}
