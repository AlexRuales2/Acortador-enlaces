package com.shortener.application.usecases;

import com.shortener.domain.ports.in.RedirectUrlUseCase;
import com.shortener.domain.ports.out.UrlCachePort;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RedirectUrlUseCaseImpl implements RedirectUrlUseCase {

    private final UrlRepositoryPort urlRepository;
    private final UrlCachePort urlCache;

    @Override
    @Async
    public CompletableFuture<String> getOriginalUrl(String shortCode) {
        // 1. Intentar obtener de Redis (Caché rápida)
        Optional<String> cachedUrl = urlCache.getOriginalUrl(shortCode);
        String originalUrl;

        if (cachedUrl.isPresent()) {
            originalUrl = cachedUrl.get();
        } else {
            // 2. Si no está en caché, buscar en MySQL
            originalUrl = urlRepository.findByShortCode(shortCode)
                    .orElseThrow(() -> new RuntimeException("URL no encontrada"))
                    .getOriginalUrl();
        }

        return CompletableFuture.completedFuture(originalUrl);
    }
}
