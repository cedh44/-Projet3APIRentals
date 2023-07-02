package com.openclassrooms.rentals;

import com.openclassrooms.rentals.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class) // Enable configuration properties
@SpringBootApplication
public class RentalsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RentalsApplication.class, args);
	}

}
