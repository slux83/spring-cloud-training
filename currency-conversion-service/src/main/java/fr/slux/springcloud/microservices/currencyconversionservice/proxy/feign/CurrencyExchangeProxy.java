package fr.slux.springcloud.microservices.currencyconversionservice.proxy.feign;

import fr.slux.springcloud.microservices.currencyconversionservice.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
// Because we are using Eureka naming service client, passing only the name of the service is enough to perform
// service discovery and client-side load balancing too. It will do round-robin
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
