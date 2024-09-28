package com.codeWithSaad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeycloakSecurityConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakSecurityConfigApplication.class, args);
		System.out.println("Keycloak security learning");
	}

}
