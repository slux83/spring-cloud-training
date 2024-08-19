package fr.slux.springcloud.microservices.apigateway;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A global logging filter
 */
@Component
public class LoggingFilter implements GlobalFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("Path of the request received is {}", exchange.getRequest().getPath());

        return chain.filter(exchange); // Continue with the chain
    }
}
