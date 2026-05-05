package com.shortener.infrastructure.adapters.out.persistence.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 15)
    private String shortCode;

    @Column(nullable = false, length = 2048)
    private String imageUrl;

    @Column(nullable = false, length = 500)
    private String description;

    private LocalDateTime creationDate;

    private Long visits;
}
