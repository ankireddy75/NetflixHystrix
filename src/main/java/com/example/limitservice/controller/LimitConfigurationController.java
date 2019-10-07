package com.example.limitservice.controller;

import com.example.limitservice.Configuration;
import com.example.limitservice.controller.response.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {
    @Autowired
    private Configuration configuration;

    @GetMapping(path = "/limits")
    public LimitConfiguration getConfiguration(){

        return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());

    }

    @GetMapping(path = "/fault-tolerance/limits")
    @HystrixCommand(fallbackMethod = "getConfigurationFallbackMethod")
    public LimitConfiguration getConfigurationFaultTolerance(){

        throw new RuntimeException("Not available");

    }

    public LimitConfiguration getConfigurationFallbackMethod (){

        return new LimitConfiguration(999,9);

    }


}
