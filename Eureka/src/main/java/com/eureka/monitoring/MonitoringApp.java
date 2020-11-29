package com.eureka.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MonitoringApp {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringApp.class, args);
    }
}
