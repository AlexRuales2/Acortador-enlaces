package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import com.shortener.domain.ports.out.UrlMetadataRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class UrlMetadataRepositoryAdapter implements UrlMetadataRepositoryPort {

    private final SpringDataUrlMetadataRepository repository;

    @Override
    @Async
    public CompletableFuture<Void> saveMetadataAsync(String shortCode, String imageUrl, String description) {
        UrlMetadataDocument doc = UrlMetadataDocument.builder()
                .shortCode(shortCode)
                .imageUrl(imageUrl)
                .description(description)
                .build();
        repository.save(java.util.Objects.requireNonNull(doc));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Optional<UrlMetadataDto> getMetadataByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(doc -> new UrlMetadataDto(doc.getImageUrl(), doc.getDescription()));
    }
}
