package fr.slux.springcloud.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyExchangeController.class);
    private Environment environment;

    private CurrencyExchangeRepository repository;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from, @PathVariable String to) {
        LOG.info("retrieveExchangeValue called with from={}, to={}", from, to);

        CurrencyExchange ce = this.repository.findByFromAndTo(from, to);
        if (ce == null) {
            throw new RuntimeException(String.format("Cannot find exchange with from %s and to %s", from, to));
        }
        ce.setEnvironment(this.environment.getProperty("local.server.port"));

        return ce;
    }
}
