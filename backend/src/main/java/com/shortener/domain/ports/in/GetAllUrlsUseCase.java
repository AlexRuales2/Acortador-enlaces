package com.shortener.domain.ports.in;

import com.shortener.domain.model.Url;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetAllUrlsUseCase {
    CompletableFuture<List<Url>> getAll();
}
