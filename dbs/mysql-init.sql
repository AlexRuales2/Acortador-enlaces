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
('https://spring.io/projects/spring-boot', 'springbt', 'https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg', 'Página oficial del framework Spring Boot para desarrollo rápido de microservicios.', 0),
('https://www.tooltyp.com/beneficios-imagenes-web', 'imgweb1', 'https://www.tooltyp.com/wp-content/uploads/2014/10/1900x920-8-beneficios-de-usar-imagenes-en-nuestros-sitios-web.jpg', 'Conoce los principales beneficios de utilizar imágenes de alta calidad en el diseño de tus sitios web modernos.', 1),
('https://unsplash.com/es/fotos/corazon', 'heart26', 'https://media.istockphoto.com/id/636379014/es/foto/manos-la-formaci%C3%B3n-de-una-forma-de-coraz%C3%B3n-con-silueta-al-atardecer.jpg?s=612x612&w=0&k=20&c=R2BE-RgICBnTUjmxB8K9U0wTkNoCKZRi-Jjge8o_OgE=', 'Hermosa silueta de manos formando un corazón durante el atardecer en la playa. Excelente foto.', 1);
