package com.shortener.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    private Long id;
    private String originalUrl;
    private String shortCode;
    private String imageUrl;
    private String description;
}
