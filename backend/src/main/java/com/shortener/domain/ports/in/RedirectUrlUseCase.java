package com.shortener.domain.ports.in;

import java.util.concurrent.CompletableFuture;

public interface RedirectUrlUseCase {
    CompletableFuture<String> getOriginalUrl(String shortCode);
}
