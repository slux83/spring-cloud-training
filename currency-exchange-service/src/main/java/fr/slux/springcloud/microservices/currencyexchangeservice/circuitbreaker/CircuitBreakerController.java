package fr.slux.springcloud.microservices.currencyexchangeservice.circuitbreaker;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    private static final Logger LOG = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    // it will retry on failure (3 times by default -> see app.properties)
    //@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    //@RateLimiter(name = "sample-api")
    // How many concurrent calls can hit this service
    @Bulkhead(name = "sample-api")
    public String sampleApi() {
        LOG.info("sample api called received");
        //ResponseEntity<String> resp = new RestTemplate().getForEntity("http://localhost:8080/some-dummy", String.class);
        //    return resp.getBody();
        return "sample api";
    }

    public String hardcodedResponse(Exception ex) {
        LOG.info("fallback exception: {}", ex.toString());
        return "fallback-response";
    }


}
