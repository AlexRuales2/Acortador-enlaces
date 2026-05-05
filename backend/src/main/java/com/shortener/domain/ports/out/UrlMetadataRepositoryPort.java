package com.shortener.domain.ports.out;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UrlMetadataRepositoryPort {
    CompletableFuture<Void> saveMetadataAsync(String shortCode, String imageUrl, String description);
    Optional<UrlMetadataDto> getMetadataByShortCode(String shortCode);
    
    // A simple DTO inside the port to return the tuple
    record UrlMetadataDto(String imageUrl, String description) {}
}
