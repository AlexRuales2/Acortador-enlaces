package com.shortener.infrastructure.adapters.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUrlStatisticsRepository extends MongoRepository<UrlStatisticsDocument, String> {
}
