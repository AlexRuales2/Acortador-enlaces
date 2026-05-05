package com.shortener.infrastructure.adapters.out.generator;

import com.shortener.domain.ports.out.ShortCodeGeneratorPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Base62ShortCodeGenerator implements ShortCodeGeneratorPort {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generate() {
        // Un generador simple usando UUID para el ejemplo, pero convertido a Base62
        long uuidPart = Math.abs(UUID.randomUUID().getMostSignificantBits());
        StringBuilder hash = new StringBuilder();
        while (uuidPart > 0) {
            hash.append(BASE62.charAt((int) (uuidPart % 62)));
            uuidPart /= 62;
        }
        return hash.length() > 8 ? hash.substring(0, 8) : hash.toString();
    }
}
