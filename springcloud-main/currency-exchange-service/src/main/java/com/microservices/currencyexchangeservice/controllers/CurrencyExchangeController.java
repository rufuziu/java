package com.microservices.currencyexchangeservice.controllers;

import com.microservices.currencyexchangeservice.entities.CurrencyExchange;
import com.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Optional;

@RestController
public class CurrencyExchangeController {
  @Autowired
  private CurrencyExchangeRepository currencyExchangeRepository;
  @Autowired
  private Environment environment;

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange retrieveExchangeValue(
          @PathVariable String from,
          @PathVariable String to
  ) throws FileNotFoundException {
    Optional<CurrencyExchange> currencyExchange =
            currencyExchangeRepository.findByFromAndTo(from, to);
//    if(currencyExchange.isEmpty())
//      throw new FileNotFoundException();
    currencyExchange.ifPresent(exchange -> exchange.setEnvironment(
            environment.getProperty("local.server.port")));
    return currencyExchange.orElseThrow(() ->
            new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Not found for "+ from + " to "+ to+ "."));
//    return currencyExchange;
  }

}
