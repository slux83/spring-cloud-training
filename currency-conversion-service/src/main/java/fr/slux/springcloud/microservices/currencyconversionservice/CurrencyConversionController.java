package fr.slux.springcloud.microservices.currencyconversionservice;

import fr.slux.springcloud.microservices.currencyconversionservice.proxy.feign.CurrencyExchangeProxy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

/**
 * We can't create a rest controller the classic java way otherwise micrometer will not pick it up
 * and it will not show in the zipkin tracing of the calls to rest templates.
 * Using the spring boot way will make it work.
 */
@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {
    private final RestTemplate restTemplate;
    private CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy currencyExchangeProxy, RestTemplate restTemplate) {
        this.currencyExchangeProxy = currencyExchangeProxy;
        this.restTemplate = restTemplate;
    }

    /**
     * Left for reference, see one below
     *
     * @param from
     * @param to
     * @param quantity
     * @return
     */
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = Map.of("from", from, "to", to);
        ResponseEntity<CurrencyConversion> response = this.restTemplate.getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

        CurrencyConversion cc = response.getBody();

        return new CurrencyConversion(cc.getId(), from, to, quantity, cc.getConversionMultiple(),
                quantity.multiply(cc.getConversionMultiple()), cc.getEnvironment() + "|rest_template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        // Feign framework avoids all the boilerplate code to do the rest template calls.
        CurrencyConversion cc = this.currencyExchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(cc.getId(), from, to, quantity, cc.getConversionMultiple(),
                quantity.multiply(cc.getConversionMultiple()), cc.getEnvironment() + "|feign");
    }
}
