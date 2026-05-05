package com.shortener.domain.ports.out;

import com.shortener.domain.model.UrlStatistics;
import java.util.concurrent.CompletableFuture;

public interface UrlStatisticsRepositoryPort {
    CompletableFuture<Void> saveAsync(UrlStatistics statistics);
}
