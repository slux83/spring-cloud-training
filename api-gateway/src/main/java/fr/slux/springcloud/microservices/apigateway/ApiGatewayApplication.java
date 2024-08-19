package fr.slux.springcloud.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * In the API gateway you can implement all the common features, like rate limits, authentication, monitoring, etc..
 * All the service calls will pass though this app. This can also be created in multiple instances to allow redundancy and resiliency.
 * Same as the Eureka server
 */
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
