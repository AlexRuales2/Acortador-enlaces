package com.shortener.infrastructure.adapters.in.web;

import com.shortener.domain.ports.in.GetAllUrlsUseCase;
import com.shortener.domain.ports.in.RedirectUrlUseCase;
import com.shortener.domain.ports.in.ShortenUrlUseCase;
import com.shortener.infrastructure.adapters.in.web.dto.UrlRequest;
import com.shortener.infrastructure.adapters.in.web.dto.UrlResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final ShortenUrlUseCase shortenUrlUseCase;
    private final RedirectUrlUseCase redirectUrlUseCase;
    private final GetAllUrlsUseCase getAllUrlsUseCase;

    // 1. Acortar URL
    @PostMapping("/api/v1/urls")
    public CompletableFuture<ResponseEntity<UrlResponse>> shorten(@Valid @RequestBody UrlRequest request) {
        // La validación de 5 palabras para la descripción se puede hacer aquí o en el Use Case
        String[] words = request.getDescription().trim().split("\\s+");
        if (words.length < 5) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.badRequest().build()
            );
        }

        return shortenUrlUseCase.shorten(request.getOriginalUrl(), request.getImageUrl(), request.getDescription())
                .thenApply(url -> ResponseEntity.status(HttpStatus.CREATED).body(UrlResponse.fromDomain(url)));
    }

    // 2. Redireccionar URL acortada
    @GetMapping("/{shortCode}")
    public CompletableFuture<ResponseEntity<Void>> redirect(@PathVariable String shortCode) {
        return redirectUrlUseCase.getOriginalUrl(shortCode)
                .thenApply(originalUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(java.util.Objects.requireNonNull(URI.create(originalUrl)))
                        .build());
    }

    // 3. Obtener todas (para la vista de tabla)
    @GetMapping("/api/v1/urls")
    public CompletableFuture<ResponseEntity<List<UrlResponse>>> getAll() {
        return getAllUrlsUseCase.getAll()
                .thenApply(urls -> urls.stream().map(UrlResponse::fromDomain).collect(Collectors.toList()))
                .thenApply(ResponseEntity::ok);
    }
}
