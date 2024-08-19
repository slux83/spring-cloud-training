package fr.slux.springcloud.microservices.limitsservice.controller;

import fr.slux.springcloud.microservices.limitsservice.bean.Limits;
import fr.slux.springcloud.microservices.limitsservice.configuration.LimitsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    private LimitsConfiguration limitsConfiguration;

    public LimitsController(LimitsConfiguration limitsConfiguration) {
        this.limitsConfiguration = limitsConfiguration;
    }

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        //return new Limits(1, 1000);
        return new Limits(this.limitsConfiguration.getMinimum(), this.limitsConfiguration.getMaximum());
    }
}
