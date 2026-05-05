package com.shortener.application.usecases;

import com.shortener.domain.model.Url;
import com.shortener.domain.ports.in.ShortenUrlUseCase;
import com.shortener.domain.ports.out.ShortCodeGeneratorPort;
import com.shortener.domain.ports.out.UrlCachePort;
import com.shortener.domain.ports.out.UrlMetadataRepositoryPort;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ShortenUrlUseCaseImpl implements ShortenUrlUseCase {

    private final UrlRepositoryPort urlRepository;
    private final UrlCachePort urlCache;
    private final UrlMetadataRepositoryPort metadataRepository;
    private final ShortCodeGeneratorPort codeGenerator;

    @Override
    @Async
    public CompletableFuture<Url> shorten(String originalUrl, String imageUrl, String description) {
        String shortCode = codeGenerator.generate();
        
        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .imageUrl(imageUrl)
                .description(description)
                .build();

        // 1. Guardar en Base de Datos Principal (MySQL)
        Url savedUrl = urlRepository.save(url);
        
        // Restaurar la metadata en el objeto de dominio devuelto
        savedUrl.setImageUrl(url.getImageUrl());
        savedUrl.setDescription(url.getDescription());

        // 2. Guardar Metadata en Base de Datos Secundaria (MongoDB)
        metadataRepository.saveMetadataAsync(savedUrl.getShortCode(), url.getImageUrl(), url.getDescription());

        // 3. Evaluamos y Guardamos asíncronamente en Caché (Redis) si aplica la regla (>50 chars)
        CompletableFuture.runAsync(() -> urlCache.saveIfEligible(savedUrl.getShortCode(), savedUrl.getOriginalUrl()));

        return CompletableFuture.completedFuture(savedUrl);
    }
}
