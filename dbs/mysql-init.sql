CREATE DATABASE IF NOT EXISTS shortener_db;
USE shortener_db;

CREATE TABLE IF NOT EXISTS urls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_code VARCHAR(15) UNIQUE NOT NULL
);

-- Índices para búsqueda rápida
CREATE INDEX idx_short_code ON urls(short_code);

-- Datos de prueba iniciales (opcional)
INSERT INTO urls (original_url, short_code) 
VALUES 
('https://spring.io/projects/spring-boot', 'springbt'),
('https://www.tooltyp.com/beneficios-imagenes-web', 'imgweb1'),
('https://unsplash.com/es/fotos/corazon', 'heart26');
