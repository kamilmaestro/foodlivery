package com.kamilmarnik.foodlivery;

import com.kamilmarnik.foodlivery.infrastructure.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(SwaggerConfiguration.class)
public class FoodliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodliveryApplication.class, args);
	}

}
