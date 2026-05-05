package com.shortener.application.usecases;

import com.shortener.domain.model.Url;
import com.shortener.domain.ports.in.GetAllUrlsUseCase;
import com.shortener.domain.ports.out.UrlRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GetAllUrlsUseCaseImpl implements GetAllUrlsUseCase {

    private final UrlRepositoryPort urlRepository;

    @Override
    @Async
    public CompletableFuture<List<Url>> getAll() {
        return CompletableFuture.completedFuture(urlRepository.findAll());
    }
}
