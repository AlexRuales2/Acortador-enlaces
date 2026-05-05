package com.shortener.application.usecases;

import com.shortener.domain.model.Url;
import com.shortener.domain.model.UrlStatistics;
import com.shortener.domain.ports.in.RedirectUrlUseCase;
import com.shortener.domain.ports.out.UrlCachePort;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import com.shortener.domain.ports.out.UrlStatisticsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RedirectUrlUseCaseImpl implements RedirectUrlUseCase {

    private final UrlRepositoryPort urlRepository;
    private final UrlCachePort urlCache;
    private final UrlStatisticsRepositoryPort statisticsRepository;

    @Override
    public String getOriginalUrlAndRecordVisit(String shortCode, String ipAddress) {
        // 1. Intentar obtener de Redis (Caché rápida)
        Optional<String> cachedUrl = urlCache.getOriginalUrl(shortCode);
        String originalUrl;

        if (cachedUrl.isPresent()) {
            originalUrl = cachedUrl.get();
        } else {
            // 2. Si no está en caché, buscar en MySQL
            Url url = urlRepository.findByShortCode(shortCode)
                    .orElseThrow(() -> new RuntimeException("URL no encontrada"));
            originalUrl = url.getOriginalUrl();
        }

        // 3. Registrar analíticas asíncronamente (Concurrencia) sin bloquear la redirección
        CompletableFuture.runAsync(() -> {
            // Incrementar contador en MySQL
            urlRepository.incrementVisits(shortCode);

            // Guardar estadística en MongoDB
            UrlStatistics stats = UrlStatistics.builder()
                    .shortCode(shortCode)
                    .clickTimestamp(LocalDateTime.now())
                    .ipAddress(ipAddress)
                    .build();
            statisticsRepository.saveAsync(stats);
        });

        // Retorna inmediatamente sin esperar al paso 3
        return originalUrl;
    }
}
