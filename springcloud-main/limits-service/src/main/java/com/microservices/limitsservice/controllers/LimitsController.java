package com.microservices.limitsservice.controllers;

import com.microservices.limitsservice.beans.Limits;
import com.microservices.limitsservice.configs.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
  @Autowired
  private Configuration configuration;
  @GetMapping("/limits")
  public Limits retriveLimits(){
  return new Limits(configuration.getMinimum(),
          configuration.getMaximum());
  }
}
