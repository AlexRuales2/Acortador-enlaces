package com.shortener.domain.ports.out;

import java.util.Optional;

public interface UrlCachePort {
    void saveIfEligible(String shortCode, String originalUrl);
    Optional<String> getOriginalUrl(String shortCode);
}
