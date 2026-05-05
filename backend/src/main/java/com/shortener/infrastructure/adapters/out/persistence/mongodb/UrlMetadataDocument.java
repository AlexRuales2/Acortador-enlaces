package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "url_metadata")
public class UrlMetadataDocument {
    @Id
    private String id;
    private String shortCode;
    private String imageUrl;
    private String description;
}
