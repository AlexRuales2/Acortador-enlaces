package com.shortener.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlRequest {
    @NotBlank
    @URL(message = "Debe ser una URL válida (http/https)")
    private String originalUrl;

    @NotBlank
    @URL(message = "Debe ser una URL de imagen válida")
    private String imageUrl;

    @NotBlank
    @Size(min = 10, max = 500, message = "La descripción debe tener máximo 500 caracteres")
    private String description;
}
