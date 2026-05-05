package com.shortener.domain.ports.in;

public interface RedirectUrlUseCase {
    String getOriginalUrlAndRecordVisit(String shortCode, String ipAddress);
}
