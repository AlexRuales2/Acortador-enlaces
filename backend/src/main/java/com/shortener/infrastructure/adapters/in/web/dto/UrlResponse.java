package com.shortener.infrastructure.adapters.in.web.dto;

import com.shortener.domain.model.Url;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UrlResponse {
    private String shortCode;
    private String originalUrl;
    private String imageUrl;
    private String description;

    public static UrlResponse fromDomain(Url url) {
        return UrlResponse.builder()
                .shortCode(url.getShortCode())
                .originalUrl(url.getOriginalUrl())
                .imageUrl(url.getImageUrl())
                .description(url.getDescription())
                .build();
    }
}
