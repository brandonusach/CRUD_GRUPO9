
--TABLAS ENTIDADES

-- 1. Tabla ROL
CREATE TABLE IF NOT EXISTS rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL
);

-- 2. Tabla PERMISOS
CREATE TABLE IF NOT EXISTS permisos (
    id_permiso SERIAL PRIMARY KEY,
    descripcion_permiso VARCHAR(100) NOT NULL
);

-- 3. Tabla USUARIO
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario SERIAL PRIMARY KEY,
    id_rol INTEGER NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- 4. Tabla DIRECCION
CREATE TABLE IF NOT EXISTS direccion (
    id_direccion SERIAL PRIMARY KEY,
    calle VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    comuna VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    region VARCHAR(100) NOT NULL
);

-- 5. Tabla METODO_DE_PAGO
CREATE TABLE IF NOT EXISTS metodo_de_pago (
    id_metodo_pago SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    tipo_de_pago VARCHAR(50) NOT NULL,
    detalle VARCHAR(100) NOT NULL,
    titular VARCHAR(50) NOT NULL,
    fecha_de_vencimiento VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 6. Tabla PRODUCTO
CREATE TABLE IF NOT EXISTS producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10,2) NOT NULL,
    stock INTEGER DEFAULT 0,
    url_imagen VARCHAR(500),
    tipo_producto VARCHAR(100) NOT NULL
);

-- 7. Tabla BOLETA
CREATE TABLE IF NOT EXISTS boleta (
    id_boleta SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    fecha DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 8. Tabla CARRITO
CREATE TABLE IF NOT EXISTS carrito (
    id_carrito SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    estado VARCHAR(20) DEFAULT 'activo', -- 'activo', 'procesado', 'abandonado'
    total_estimado DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 9. Tabla COMPRA
CREATE TABLE IF NOT EXISTS compra (
    id_compra SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_boleta INTEGER,
    id_metodo_pago INTEGER NOT NULL,
    id_carrito INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_boleta) REFERENCES boleta(id_boleta),
    FOREIGN KEY (id_metodo_pago) REFERENCES metodo_de_pago(id_metodo_pago),
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito)
);

-- 10. Tabla VALORACION
CREATE TABLE IF NOT EXISTS valoracion (
    id_valoracion SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    puntuacion INTEGER CHECK (puntuacion >= 1 AND puntuacion <= 5),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- 11. Tabla LISTAS_DE_DESEOS
CREATE TABLE IF NOT EXISTS listas_de_deseos (
    id_deseo SERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 12. Tabla CATEGORIA_MESA
CREATE TABLE IF NOT EXISTS categoria_mesa (
    id_mesa SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 13. Tabla CATEGORIA_CARTA
CREATE TABLE IF NOT EXISTS categoria_carta (
    id_carta SERIAL PRIMARY KEY,
    rareza VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    ano VARCHAR(10) NOT NULL
);

-- 14. Tabla TIENDA
CREATE TABLE IF NOT EXISTS tienda (
    id_tienda SERIAL PRIMARY KEY,
    nombre_tienda VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(255),
    id_jefe INTEGER NOT NULL,
    id_direccion INTEGER NOT NULL,
    FOREIGN KEY (id_jefe) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_direccion) REFERENCES direccion(id_direccion)
);


-- Tablas de relación muchos a muchos

-- Relación Rol-Permisos
CREATE TABLE IF NOT EXISTS rol_permisos (
    id_rol INTEGER,
    id_permiso INTEGER,
    PRIMARY KEY (id_rol, id_permiso),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_permiso) REFERENCES permisos(id_permiso)
);

-- Relación dirección-usuario
CREATE TABLE IF NOT EXISTS direccion_usuario (
    id_direccion INTEGER,
    id_usuario INTEGER,
    PRIMARY KEY (id_direccion, id_usuario),
    FOREIGN KEY (id_direccion) REFERENCES direccion(id_direccion),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Relación compra_producto
CREATE TABLE IF NOT EXISTS compra_producto (
    id_compra INTEGER,
    id_producto INTEGER,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_compra, id_producto),
    FOREIGN KEY (id_compra) REFERENCES compra(id_compra),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Relación usuario y producto
CREATE TABLE IF NOT EXISTS usuario_producto (
    id_usuario INTEGER,
    id_producto INTEGER,
    PRIMARY KEY (id_usuario, id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Relación Producto-Categoria_Mesa
CREATE TABLE IF NOT EXISTS producto_categoria_mesa (
    id_producto INTEGER,
    id_mesa INTEGER,
    PRIMARY KEY (id_producto, id_mesa),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_mesa) REFERENCES categoria_mesa(id_mesa)
);

-- Relación Producto-Categoria_Carta
CREATE TABLE IF NOT EXISTS producto_categoria_carta (
    id_producto INTEGER,
    id_carta INTEGER,
    PRIMARY KEY (id_producto, id_carta),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_carta) REFERENCES categoria_carta(id_carta)
);

-- Relación Lista_de_Deseos-Producto
CREATE TABLE IF NOT EXISTS lista_producto (
    id_deseo INTEGER,
    id_producto INTEGER,
    PRIMARY KEY (id_deseo, id_producto),
    FOREIGN KEY (id_deseo) REFERENCES listas_de_deseos(id_deseo),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Relación tienda y producto
CREATE TABLE IF NOT EXISTS tienda_producto (
    id_tienda INTEGER,
    id_producto INTEGER,
    PRIMARY KEY (id_tienda, id_producto),
    FOREIGN KEY (id_tienda) REFERENCES tienda(id_tienda),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);
-- Relación carrito y producto
CREATE TABLE IF NOT EXISTS carrito_item (
    id_carrito INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL, -- Precio al momento de agregar
    PRIMARY KEY (id_carrito, id_producto),
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);