-- Forzamos el uso de UTF8 para que la ñ no sea un problema
SET NAMES utf8mb4;
USE convive_db;

-- Limpiamos tablas para evitar errores de duplicados (Duplicate entry)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE chat_mensaje;
TRUNCATE TABLE publication;
TRUNCATE TABLE profile;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. PUBLICACIONES 
INSERT INTO publication (title, description, type, price, city, `baños`, habitaciones, metros)
VALUES 
('Piso céntrico luminoso', 'Piso de 3 habitaciones cerca de la universidad', 'ALQUILER', 750, 'Valencia', 2, 3, 90),
('Habitación en piso compartido', 'Habitación amplia con escritorio', 'COMPARTIDO', 350, 'Madrid', 1, 1, 15),
('Estudio moderno', 'Estudio reformado ideal para estudiante', 'ALQUILER', 600, 'Barcelona', 1, 1, 40);

-- 2. CHATS
INSERT INTO chat_mensaje (chat_id, message_number, sender, receiver, message, timestamp)
VALUES
(1, 1, 'juan123', 'maria89', 'Hola, ¿sigue disponible el piso?', NOW()),
(1, 2, 'maria89', 'juan123', 'Sí, todavía está disponible.', NOW());

-- 3. PROFILES
INSERT INTO profile (name, description, city, piso, price, sex, visible, age) VALUES 
('Alejandro García', 'Estudiante de máster buscando convivencia.', 'Madrid', 1, 450, 'Hombre', 1, 24),
('Lucía Fernández', 'Trabajadora en sector IT. Ordenada.', 'Barcelona', 0, 600, 'Mujer', 1, 29),
('Marcos Ruiz', 'Busco habitación cerca del centro.', 'Sevilla', 1, 320, 'Hombre', 1, 22),
('Elena Martínez', 'Profesora de primaria.', 'Valencia', 1, 380, 'Mujer', 1, 31),
('Carlos Peña', 'Nómada digital. Busco silencio.', 'Málaga', 0, 500, 'Hombre', 0, 35),
('Sofía Castro', 'Estudiante de Bellas Artes.', 'Granada', 1, 275, 'Mujer', 1, 20);