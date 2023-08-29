package com.confeccaosocorro.controleestoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class ConfeccaoSocorroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfeccaoSocorroApplication.class, args);
	}

}
