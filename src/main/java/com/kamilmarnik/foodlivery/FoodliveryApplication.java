package com.kamilmarnik.foodlivery;

import com.kamilmarnik.foodlivery.infrastructure.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class FoodliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodliveryApplication.class, args);
	}

}
