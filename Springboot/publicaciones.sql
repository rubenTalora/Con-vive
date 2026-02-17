-- Active: 1758530917467@@127.0.0.1@3306

USE convive_db;

INSERT INTO chat_message (chat_id, sender, receiver, message, timestamp) VALUES
(1, 'juan123', 'maria89', 'Hola, ¿sigue disponible el piso?'),
(1, 'maria89', 'juan123', 'Sí, todavía está disponible.'),
(2, 'carlos_dev', 'ana_student', 'Hola, busco compañero para compartir piso.'),
(2, 'ana_student', 'carlos_dev', 'Perfecto, me interesa. ¿Dónde está ubicado?');

USE convive_db;

INSERT INTO publication (title, description, type, price, city, owner_username) VALUES
('Piso luminoso en el centro', 'Piso de 3 habitaciones completamente amueblado, ideal para estudiantes.', 'ALQUILER', 850.00, 'Madrid', 'juan123'),
('Ático con terraza', 'Ático moderno con terraza privada y vistas increíbles.', 'VENTA', 320000.00, 'Barcelona', 'maria89'),
('Habitación en piso compartido', 'Buscamos compañero/a de piso responsable y limpio.', 'COMPAÑERO', 400.00, 'Valencia', 'carlos_dev'),
('Apartamento cerca de la playa', 'Apartamento de 2 habitaciones a 5 minutos andando del mar.', 'VENTA', 210000.00, 'Málaga', 'laura_fit'),
('Piso para estudiantes', 'Zona universitaria, totalmente equipado.', 'ALQUILER', 700.00, 'Sevilla', 'ana_student');
