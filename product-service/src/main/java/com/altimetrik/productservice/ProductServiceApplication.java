package com.altimetrik.productservice;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Product Service REST API",
				description = "Product Service REST API Documentation",
				contact = @Contact(name = "Dharmendra Kumar",email = "dharkumar@altimetrik.com",
						url = "http://localhost:8080/api/products"),
				license = @License(name = "Apache 2.0"),
				version = "v1.0"
		),externalDocs = @ExternalDocumentation(url = "http://localhost:8080/api/products")
)
@SpringBootApplication
public class ProductServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
