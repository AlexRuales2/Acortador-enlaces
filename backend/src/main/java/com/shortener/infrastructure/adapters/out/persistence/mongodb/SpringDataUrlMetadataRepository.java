package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUrlMetadataRepository extends MongoRepository<UrlMetadataDocument, String> {
    Optional<UrlMetadataDocument> findByShortCode(String shortCode);
}
