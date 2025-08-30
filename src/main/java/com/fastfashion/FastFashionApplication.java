package com.fastfashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FastFashionApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastFashionApplication.class, args);
    }
}
