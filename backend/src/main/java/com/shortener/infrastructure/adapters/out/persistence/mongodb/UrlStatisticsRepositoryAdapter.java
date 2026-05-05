package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import com.shortener.domain.model.UrlStatistics;
import com.shortener.domain.ports.out.UrlStatisticsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class UrlStatisticsRepositoryAdapter implements UrlStatisticsRepositoryPort {

    private final SpringDataUrlStatisticsRepository repository;

    @Override
    @Async
    public CompletableFuture<Void> saveAsync(UrlStatistics statistics) {
        UrlStatisticsDocument doc = UrlStatisticsDocument.builder()
                .shortCode(statistics.getShortCode())
                .clickTimestamp(statistics.getClickTimestamp())
                .ipAddress(statistics.getIpAddress())
                .build();
        repository.save(java.util.Objects.requireNonNull(doc));
        return CompletableFuture.completedFuture(null);
    }
}
