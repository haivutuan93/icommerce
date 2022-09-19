package com.nab.icommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcommerceApplication.class, args);
    }

}
