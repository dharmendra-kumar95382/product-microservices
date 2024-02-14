package com.altimetrik.inventoryservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Inventory Service REST API",
				description = "Inventory Service REST API Documentation",
				contact = @Contact(name = "Dharmendra Kumar",email = "dharkumar@altimetrik.com",
						url = "http://localhost:8081/api/inventory"),
				license = @License(name = "Apache 2.0"),
				version = "v1.0"
		),externalDocs = @ExternalDocumentation(url = "http://localhost:8081/api/inventory")
)
@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
