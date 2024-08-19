package fr.slux.springcloud.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    // Creating route locator
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r
                        .path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "Alessio")
                                .addRequestParameter("MyParam", "MyParamValue"))
                        .uri("http://httpbin.org:80"))

                .route(r -> r.path("/currency-exchange/**")
                        .uri("lb://currency-exchange-service")) // LB stands for load balancer
                .route(r -> r.path("/currency-conversion/**")
                        .uri("lb://currency-conversion-service")) // LB stands for load balancer
                .route(r -> r.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion-service")) // LB stands for load balancer

                // You can even rewrite paths with some regex magic
                .route(r -> r.path("/currency-conversion-new/**")
                        .filters(f -> f
                                .rewritePath("/currency-conversion-new/(?<segment>.*)",
                                        "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion-service")) // LB stands for load balancer
                .build();
    }
}
