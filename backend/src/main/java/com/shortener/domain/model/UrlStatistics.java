package com.shortener.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlStatistics {
    private String id;
    private String shortCode;
    private LocalDateTime clickTimestamp;
    private String ipAddress;
}
