-- Crear la tabla para almacenar detalles de usuario
CREATE TABLE user_detail (
    id BIGSERIAL PRIMARY KEY, -- Identificador único autoincremental para cada usuario
    username VARCHAR(50) NOT NULL, -- Nombre de usuario, debe ser único y no nulo
    password VARCHAR(100) NOT NULL, -- Contraseña del usuario, almacenada de forma segura
    role VARCHAR(100) NOT NULL, -- Rol del usuario en el sistema
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora de creación del registro, se establece automáticamente al insertar
    updated_at TIMESTAMP DEFAULT NULL, -- Fecha y hora de la última actualización del registro, inicialmente nula
    active BOOLEAN DEFAULT TRUE NOT NULL -- Estado del usuario, por defecto activo
);

-- Agregar comentarios a la tabla y a las columnas
COMMENT ON TABLE user_detail IS 'Tabla para almacenar detalles de usuario';
COMMENT ON COLUMN user_detail.id IS 'Identificador único autoincremental para cada usuario';
COMMENT ON COLUMN user_detail.username IS 'Nombre de usuario, debe ser único y no nulo';
COMMENT ON COLUMN user_detail.password IS 'Contraseña del usuario, almacenada de forma segura';
COMMENT ON COLUMN user_detail.role IS 'Rol del usuario en el sistema';
COMMENT ON COLUMN user_detail.created_at IS 'Fecha y hora de creación del registro, se establece automáticamente al insertar';
COMMENT ON COLUMN user_detail.updated_at IS 'Fecha y hora de la última actualización del registro, inicialmente nula';
COMMENT ON COLUMN user_detail.active IS 'Estado del usuario, por defecto activo';