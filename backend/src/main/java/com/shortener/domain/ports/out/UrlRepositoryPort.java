package com.shortener.domain.ports.out;

import com.shortener.domain.model.Url;
import java.util.List;
import java.util.Optional;

public interface UrlRepositoryPort {
    Url save(Url url);
    Optional<Url> findByShortCode(String shortCode);
    void incrementVisits(String shortCode);
    List<Url> findAll();
}
