package com.shortener.application.usecases;

import com.shortener.domain.model.Url;
import com.shortener.domain.ports.in.ShortenUrlUseCase;
import com.shortener.domain.ports.out.ShortCodeGeneratorPort;
import com.shortener.domain.ports.out.UrlCachePort;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ShortenUrlUseCaseImpl implements ShortenUrlUseCase {

    private final UrlRepositoryPort urlRepository;
    private final UrlCachePort urlCache;
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
                .creationDate(LocalDateTime.now())
                .visits(0L)
                .build();

        // 1. Guardar en Base de Datos Principal (MySQL)
        Url savedUrl = urlRepository.save(url);

        // 2. Evaluamos y Guardamos asíncronamente en Caché (Redis) si aplica la regla (>50 chars)
        // Usamos otro hilo para no bloquear el guardado inicial, aunque @Async ya lo hace asíncrono.
        CompletableFuture.runAsync(() -> urlCache.saveIfEligible(savedUrl.getShortCode(), savedUrl.getOriginalUrl()));

        return CompletableFuture.completedFuture(savedUrl);
    }
}
