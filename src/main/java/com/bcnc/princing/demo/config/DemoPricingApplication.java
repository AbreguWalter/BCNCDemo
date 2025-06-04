package com.bcnc.princing.demo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.bcnc.princing.demo.infrastructure.adapter.entity")
@EnableJpaRepositories(basePackages = "com.bcnc.princing.demo.infrastructure.adapter.repository")
@SpringBootApplication(scanBasePackages = "com.bcnc.princing.demo")
public class DemoPricingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPricingApplication.class, args);
    }
}
