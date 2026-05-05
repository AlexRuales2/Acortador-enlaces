package com.shortener.domain.ports.in;

import com.shortener.domain.model.Url;
import java.util.concurrent.CompletableFuture;

public interface ShortenUrlUseCase {
    CompletableFuture<Url> shorten(String originalUrl, String imageUrl, String description);
}
