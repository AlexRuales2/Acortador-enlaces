package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "url_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlStatisticsDocument {
    @Id
    private String id;

    @Indexed
    private String shortCode;
    
    private LocalDateTime clickTimestamp;
    
    private String ipAddress;
}
