package com.shortener.infrastructure.config;

import com.shortener.infrastructure.adapters.out.persistence.mongodb.SpringDataUrlMetadataRepository;
import com.shortener.infrastructure.adapters.out.persistence.mongodb.UrlMetadataDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final SpringDataUrlMetadataRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(java.util.Objects.requireNonNull(List.of(
                    UrlMetadataDocument.builder()
                            .shortCode("springbt")
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQp6BkVWS78O8Weh7wp5Wd7NcA42UDGJ3elGw&s")
                            .description("Página oficial del framework Spring Boot para desarrollo rápido de microservicios.")
                            .build(),
                    UrlMetadataDocument.builder()
                            .shortCode("imgweb1")
                            .imageUrl("https://media.vandal.net/m/10-2023/17/20231017163114_4.jpg")
                            .description("Conoce los principales beneficios de utilizar imágenes de alta calidad en el diseño de tus sitios web modernos.")
                            .build(),
                    UrlMetadataDocument.builder()
                            .shortCode("heart26")
                            .imageUrl("https://image.api.playstation.com/vulcan/ap/rnd/202510/1420/e8037b12eeddb3a920459a3cc69c5f1df87d17fa1f3386bd.jpg")
                            .description("Hermosa silueta de manos formando un corazón durante el atardecer en la playa. Excelente foto.")
                            .build()
            )));
            System.out.println("✅ Base de datos MongoDB inicializada con datos semilla.");
        }
    }
}
