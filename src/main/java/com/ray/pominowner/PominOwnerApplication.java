package com.ray.pominowner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PominOwnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PominOwnerApplication.class, args);
    }

}
