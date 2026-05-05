CREATE DATABASE IF NOT EXISTS shortener_db;
USE shortener_db;

CREATE TABLE IF NOT EXISTS urls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_code VARCHAR(15) UNIQUE NOT NULL,
    image_url VARCHAR(2048) NOT NULL,
    description VARCHAR(500) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    visits BIGINT DEFAULT 0
);

-- Índices para búsqueda rápida
CREATE INDEX idx_short_code ON urls(short_code);

-- Datos de prueba iniciales (opcional)
INSERT INTO urls (original_url, short_code, image_url, description, visits) 
VALUES 
('https://spring.io/projects/spring-boot', 'springbt', 'https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg', 'Página oficial del framework Spring Boot para desarrollo rápido de microservicios.', 0);
