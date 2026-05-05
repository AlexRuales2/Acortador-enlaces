package com.shortener.infrastructure.adapters.out.persistence.redis;

import com.shortener.domain.ports.out.UrlCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisUrlCacheAdapter implements UrlCachePort {

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "url:";

    @Override
    public void saveIfEligible(String shortCode, String originalUrl) {
        // Regla de Negocio en la Infraestructura de Caché:
        if (originalUrl != null && originalUrl.length() >= 50) {
            redisTemplate.opsForValue().set(PREFIX + shortCode, originalUrl, java.util.Objects.requireNonNull(Duration.ofDays(7)));
        }
    }

    @Override
    public Optional<String> getOriginalUrl(String shortCode) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(PREFIX + shortCode));
    }
}
