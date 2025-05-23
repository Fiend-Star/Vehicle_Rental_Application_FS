package com.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Main application class for the Car Rental System.
 * Configured with reactive support, AOP capabilities and R2DBC repositories.
 */
@SpringBootApplication
@EnableWebFlux
@EnableAspectJAutoProxy
@EnableR2dbcRepositories(basePackages = "com.intern.repository")
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }
    
    // Data initialization code has been moved to DataMigrationUtility
}