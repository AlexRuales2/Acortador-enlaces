package com.shortener.infrastructure.adapters.out.persistence.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByShortCode(String shortCode);

    @Modifying
    @Query("UPDATE UrlEntity u SET u.visits = u.visits + 1 WHERE u.shortCode = :shortCode")
    void incrementVisits(String shortCode);
}
