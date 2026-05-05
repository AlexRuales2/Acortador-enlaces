package com.shortener.infrastructure.adapters.out.persistence.mysql;

import com.shortener.domain.model.Url;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UrlRepositoryAdapter implements UrlRepositoryPort {

    private final SpringDataUrlRepository repository;

    @Override
    public Url save(Url url) {
        UrlEntity entity = UrlEntity.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .build();
        UrlEntity saved = repository.save(java.util.Objects.requireNonNull(entity));
        return mapToDomain(saved);
    }

    @Override
    public Optional<Url> findByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode).map(this::mapToDomain);
    }

    @Override
    public List<Url> findAll() {
        return repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    private Url mapToDomain(UrlEntity entity) {
        return Url.builder()
                .id(entity.getId())
                .originalUrl(entity.getOriginalUrl())
                .shortCode(entity.getShortCode())
                .build();
    }
}
