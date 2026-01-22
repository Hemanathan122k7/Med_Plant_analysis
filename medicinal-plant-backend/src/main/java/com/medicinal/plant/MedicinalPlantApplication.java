package com.medicinal.plant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Application class for Medicinal Plant Identifier
 * 
 * @author Medicinal Plant Team
 * @version 1.0.0
 */
@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class MedicinalPlantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicinalPlantApplication.class, args);
        log.info("🌿 Medicinal Plant Identifier Backend is running!");
        log.info("📘 Swagger UI: http://localhost:8080/swagger-ui.html");
        log.info("📗 API Docs: http://localhost:8080/v3/api-docs");
    }
}
