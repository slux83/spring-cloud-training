package fr.slux.springcloud.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    /**
     * Spring JPA will implement this for us, as long as the input arg names match the entity
     *
     * @param from
     * @param to
     * @return
     */
    CurrencyExchange findByFromAndTo(String from, String to);
}
