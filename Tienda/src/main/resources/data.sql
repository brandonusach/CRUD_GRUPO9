-- Insertar datos en tabla ROL
INSERT INTO rol (nombre_rol) VALUES
('Cliente'),
('Jefe'),
('Administrador');

-- Insertar datos en tabla PERMISOS
INSERT INTO permisos (descripcion_permiso) VALUES
('Gestionar usuarios'),
('Ver productos'),
('Crear productos'),
('Eliminar productos'),
('Procesar pagos'),
('Ver reportes'),
('Gestionar inventario'),
('Acceder a configuración'),
('Enviar notificaciones'),
('Generar facturas');

-- Insertar datos en tabla USUARIO
INSERT INTO usuario (id_rol, nombre, apellido, email, contrasena) VALUES
(1, 'Carlos', 'González', 'carlos.gonzalez@email.com', 'password123'),
(1, 'María', 'López', 'maria.lopez@email.com', 'password123'),
(1, 'José', 'Martínez', 'jose.martinez@email.com', 'password123'),
(1, 'Ana', 'Rodríguez', 'ana.rodriguez@email.com', 'password123'),
(1, 'Luis', 'Hernández', 'luis.hernandez@email.com', 'password123'),
(1, 'Carmen', 'García', 'carmen.garcia@email.com', 'password123'),
(1, 'Pedro', 'Sánchez', 'pedro.sanchez@email.com', 'password123'),
(1, 'Elena', 'Muñoz', 'elena.munoz@email.com', 'password123'),
(2, 'Miguel', 'Torres', 'miguel.torres@email.com', 'password123'),
(3, 'Sofía', 'Jiménez', 'sofia.jimenez@email.com', 'password123');

-- Insertar datos en tabla DIRECCION
INSERT INTO direccion (calle, numero, comuna, ciudad, region) VALUES
('Av. Providencia', 1234, 'Providencia', 'Santiago', 'Region_Metropolitana'),
('Av. Las Condes', 5678, 'Las Condes', 'Santiago', 'Region_Metropolitana'),
('Av. Vicuña Mackenna', 9012, 'Ñuñoa', 'Santiago', 'Region_Metropolitana'),
('Av. Irarrázaval', 3456, 'Ñuñoa', 'Santiago', 'Region_Metropolitana'),
('Av. Grecia', 7890, 'Peñalolén', 'Santiago', 'Region_Metropolitana'),
('Av. Tobalaba', 2345, 'La Reina', 'Santiago', 'Region_Metropolitana'),
('Av. Américo Vespucio', 6789, 'La Florida', 'Santiago', 'Region_Metropolitana'),
('Av. Santa Rosa', 1357, 'La Pintana', 'Santiago', 'Region_Metropolitana'),
('Av. Gran Avenida', 8024, 'San Miguel', 'Santiago', 'Region_Metropolitana'),
('Av. Macul', 4680, 'Macul', 'Santiago', 'Region_Metropolitana');

-- Insertar datos en tabla LISTAS_DE_DESEOS
INSERT INTO listas_de_deseos (nombres, id_usuario) VALUES
('Magic: The Gathering & Pokémon', 1),
('Catan & Ticket to Ride', 2),
('Pokémon & Yu-Gi-Oh!', 3),
('Azul & Splendor', 4),
('Dragon Ball & One Piece', 5),
('Wingspan', 6),
('Magic & Dragon Ball', 7),
('Azul & One Piece', 8),
('Magic & Yu-Gi-Oh!', 9),
('Wingspan & Splendor', 10);

-- Insertar datos en tabla PRODUCTO
INSERT INTO producto (nombre, descripcion, precio, stock, url_imagen, tipo_producto) VALUES
('Magic: The Gathering - Dominaria United', 'Sobre de cartas coleccionables MTG', 4500.00, 98, 'https://example.com/mtg1.jpg', 'Carta'),
('Pokémon TCG - Scarlet & Violet', 'Sobre de cartas Pokémon', 3200.00, 147, 'https://example.com/pokemon1.jpg', 'Carta'),
('Catan - Juego Base', 'Juego de mesa estratégico para 3-4 jugadores', 35000.00, 24, 'https://example.com/catan.jpg', 'Juego de Mesa'),
('Ticket to Ride', 'Juego de mesa de trenes', 28000.00, 28, 'https://example.com/ticket.jpg', 'Juego de Mesa'),
('Yu-Gi-Oh! - Duelist Pack', 'Cartas coleccionables Yu-Gi-Oh', 2800.00, 76, 'https://example.com/yugioh1.jpg', 'Carta'),
('Azul - Juego de Azulejos', 'Juego de mesa familiar', 25000.00, 19, 'https://example.com/azul.jpg', 'Juego de Mesa'),
('Dragon Ball Super - Booster Pack', 'Cartas coleccionables Dragon Ball', 3500.00, 87, 'https://example.com/dbs1.jpg', 'Carta'),
('Splendor', 'Juego de mesa de gemas', 22000.00, 33, 'https://example.com/splendor.jpg', 'Juego de Mesa'),
('One Piece - Romance Dawn', 'Cartas coleccionables One Piece', 4000.00, 59, 'https://example.com/onepiece1.jpg', 'Carta'),
('Wingspan', 'Juego de mesa de aves', 45000.00, 14, 'https://example.com/wingspan.jpg', 'Juego de Mesa');

-- Insertar datos en tabla CATEGORIA_MESA
INSERT INTO categoria_mesa (nombre) VALUES
('Estrategia'),
('Familiar'),
('Cooperativo'),
('Competitivo'),
('Temático'),
('Abstracto'),
('Deck Building'),
('Worker Placement'),
('Area Control'),
('Engine Building');

-- Insertar datos en tabla CATEGORIA_CARTA
INSERT INTO categoria_carta (rareza, estado, ano) VALUES
('Común', 'Nuevo', '2024'),
('Poco Común', 'Nuevo', '2024'),
('Rara', 'Nuevo', '2024'),
('Súper Rara', 'Nuevo', '2023'),
('Ultra Rara', 'Nuevo', '2023'),
('Común', 'Usado', '2022'),
('Poco Común', 'Usado', '2022'),
('Rara', 'Usado', '2021'),
('Mítica', 'Nuevo', '2024'),
('Legendaria', 'Nuevo', '2023');

-- Insertar datos en tabla TIENDA
INSERT INTO tienda (nombre_tienda, telefono, email, id_jefe, id_direccion) VALUES
('GameStore Providencia', '+56912345678', 'contacto@gamestore.cl', 9, 1),
('TCG Center Las Condes', '+56987654321', 'info@tcgcenter.cl', 9, 2),
('Mundo Gamer Ñuñoa', '+56911223344', 'ventas@mundogamer.cl', 9, 3),
('Cartas y Juegos', '+56955667788', 'hola@cartasyjuegos.cl', 9, 4),
('Epic Games Store', '+56933445566', 'contacto@epicgames.cl', 9, 5),
('La Guarida del Dragón', '+56977889900', 'info@guaridadragon.cl', 9, 6),
('Nexus Gaming', '+56922334455', 'ventas@nexusgaming.cl', 9, 7),
('TCG Paradise', '+56966778899', 'contacto@tcgparadise.cl', 9, 8),
('Juegos & Más', '+56944556677', 'info@juegosmás.cl', 9, 9),
('TCG World', '+56911223355', 'ventas@tcgworld.cl', 9, 10);

-- Insertar datos en tabla BOLETA
INSERT INTO boleta (id_usuario, fecha, total) VALUES
(1, '2024-06-15', 9000.00),
(2, '2024-06-14', 9600.00),
(3, '2024-06-13', 35000.00),
(4, '2024-06-12', 56000.00),
(5, '2024-06-11', 11200.00),
(6, '2024-06-10', 25000.00),
(7, '2024-06-09', 10500.00),
(8, '2024-06-08', 44000.00),
(9, '2024-06-07', 4000.00),
(10, '2024-06-06', 45000.00);


-- Insertar datos en tabla METODO_DE_PAGO
INSERT INTO metodo_de_pago (id_usuario, tipo_de_pago, detalle, titular, fecha_de_vencimiento) VALUES
(1, 'Tarjeta de Crédito', 'Visa ****1234', 'Carlos González', '12/2026'),
(2, 'Tarjeta de Débito', 'Mastercard ****5678', 'María López', '08/2025'),
(3, 'Transferencia', 'Banco de Chile', 'José Martínez', 'N/A'),
(4, 'Tarjeta de Crédito', 'Visa ****9012', 'Ana Rodríguez', '05/2027'),
(5, 'Efectivo', 'Pago en efectivo', 'Luis Hernández', 'N/A'),
(6, 'Tarjeta de Crédito', 'Mastercard ****3456', 'Carmen García', '11/2025'),
(7, 'Tarjeta de Débito', 'Visa ****7890', 'Pedro Sánchez', '03/2026'),
(8, 'Transferencia', 'Banco Santander', 'Elena Muñoz', 'N/A'),
(9, 'Tarjeta de Crédito', 'Visa ****2345', 'Miguel Torres', '09/2027'),
(10, 'Tarjeta de Débito', 'Mastercard ****6789', 'Sofía Jiménez', '07/2025');



-- Insertar carritos activos para algunos usuarios
INSERT INTO carrito (id_usuario, estado, total_estimado) VALUES
(1, 'activo', 12200.00),
(2, 'activo', 63000.00),
(3, 'activo', 8400.00),
(4, 'activo', 47000.00),
(5, 'activo', 11000.00);

-- Insertar carritos procesados (compras pasadas)
INSERT INTO carrito (id_usuario, estado, total_estimado) VALUES
(1, 'procesado', 9000.00),    -- id_carrito = 6
(2, 'procesado', 9600.00),    -- id_carrito = 7
(3, 'procesado', 35000.00),   -- id_carrito = 8
(4, 'procesado', 56000.00),   -- id_carrito = 9
(5, 'procesado', 11200.00),   -- id_carrito = 10
(6, 'procesado', 25000.00),   -- id_carrito = 11
(7, 'procesado', 10500.00),   -- id_carrito = 12
(8, 'procesado', 44000.00),   -- id_carrito = 13
(9, 'procesado', 4000.00),    -- id_carrito = 14
(10, 'procesado', 45000.00);  -- id_carrito = 15

-- Insertar items en carritos activos
INSERT INTO carrito_item (id_carrito, id_producto, cantidad, precio_unitario) VALUES
-- Carrito activo de Carlos (id_carrito = 1)
(1, 1, 2, 4500.00), -- MTG x2
(1, 2, 1, 3200.00), -- Pokémon x1
-- Carrito activo de María (id_carrito = 2)
(2, 3, 1, 35000.00), -- Catan x1
(2, 4, 1, 28000.00), -- Ticket to Ride x1
-- Carrito activo de José (id_carrito = 3)
(3, 5, 3, 2800.00), -- Yu-Gi-Oh x3
-- Carrito activo de Ana (id_carrito = 4)
(4, 6, 1, 25000.00), -- Azul x1
(4, 8, 1, 22000.00), -- Splendor x1
-- Carrito activo de Luis (id_carrito = 5)
(5, 7, 2, 3500.00), -- Dragon Ball x2
(5, 9, 1, 4000.00); -- One Piece x1

-- Insertar items en carritos procesados
INSERT INTO carrito_item (id_carrito, id_producto, cantidad, precio_unitario) VALUES
-- Carrito procesado de Carlos (id_carrito = 6)
(6, 1, 2, 4500.00),
-- Carrito procesado de María (id_carrito = 7)
(7, 2, 3, 3200.00),
-- Carrito procesado de José (id_carrito = 8)
(8, 3, 1, 35000.00),
-- Carrito procesado de Ana (id_carrito = 9)
(9, 4, 2, 28000.00),
-- Carrito procesado de Luis (id_carrito = 10)
(10, 5, 4, 2800.00),
-- Carrito procesado de Carmen (id_carrito = 11)
(11, 6, 1, 25000.00),
-- Carrito procesado de Pedro (id_carrito = 12)
(12, 7, 3, 3500.00),
-- Carrito procesado de Elena (id_carrito = 13)
(13, 8, 2, 22000.00),
-- Carrito procesado de Miguel (id_carrito = 14)
(14, 9, 1, 4000.00),
-- Carrito procesado de Sofía (id_carrito = 15)
(15, 10, 1, 45000.00);

-- Insertar datos en tabla COMPRA
INSERT INTO compra (id_usuario, id_boleta, id_metodo_pago, id_carrito) VALUES
(1, 1, 1, 6),   -- Carlos - carrito procesado id 6
(2, 2, 2, 7),   -- María - carrito procesado id 7
(3, 3, 3, 8),   -- José - carrito procesado id 8
(4, 4, 4, 9),   -- Ana - carrito procesado id 9
(5, 5, 5, 10),  -- Luis - carrito procesado id 10
(6, 6, 6, 11),  -- Carmen - carrito procesado id 11
(7, 7, 7, 12),  -- Pedro - carrito procesado id 12
(8, 8, 8, 13),  -- Elena - carrito procesado id 13
(9, 9, 9, 14),  -- Miguel - carrito procesado id 14
(10, 10, 10, 15); -- Sofía - carrito procesado id 15

-- Insertar datos en tabla VALORACION
INSERT INTO valoracion (id_usuario, id_producto, puntuacion) VALUES
(1, 1, 5),
(2, 2, 4),
(3, 3, 5),
(4, 4, 4),
(5, 5, 3),
(6, 6, 5),
(7, 7, 4),
(8, 8, 5),
(9, 9, 4),
(10, 10, 5);



-- ROL_PERMISOS
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
(1, 2), -- Cliente: Ver productos
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10), -- Jefe: Todos
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10); -- Admin: Todos

-- PRODUCTO_CATEGORIA_MESA
INSERT INTO producto_categoria_mesa (id_producto, id_mesa) VALUES
(3, 1), (4, 2), (6, 1), (8, 2), (10, 1);

-- PRODUCTO_CATEGORIA_CARTA
INSERT INTO producto_categoria_carta (id_producto, id_carta) VALUES
(1, 3), (2, 2), (5, 1), (7, 4), (9, 5);

-- LISTA_PRODUCTO
INSERT INTO lista_producto (id_deseo, id_producto) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 2), (3, 5), (4, 6), (4, 8), (5, 7), (5, 9),
(6, 10), (7, 1), (7, 7), (8, 6), (8, 9), (9, 1), (9, 5), (10, 10), (10, 8);

-- DIRECCION_USUARIO
INSERT INTO direccion_usuario (id_direccion, id_usuario) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);


-- COMPRA_PRODUCTO
INSERT INTO compra_producto (id_compra, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 2, 4500.00, 9000.00), (2, 2, 3, 3200.00, 9600.00), (3, 3, 1, 35000.00, 35000.00),
(4, 4, 2, 28000.00, 56000.00), (5, 5, 4, 2800.00, 11200.00), (6, 6, 1, 25000.00, 25000.00),
(7, 7, 3, 3500.00, 10500.00), (8, 8, 2, 22000.00, 44000.00), (9, 9, 1, 4000.00, 4000.00),
(10, 10, 1, 45000.00, 45000.00);

-- USUARIO_PRODUCTO
INSERT INTO usuario_producto (id_usuario, id_producto) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- TIENDA_PRODUCTO
INSERT INTO tienda_producto (id_tienda, id_producto) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8), (5, 9), (5, 10),
(6, 1), (6, 5), (7, 2), (7, 7), (8, 3), (8, 9), (9, 4), (9, 6), (10, 8), (10, 10);