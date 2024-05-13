-- Definición de la tabla faction
CREATE TABLE faction (
    code VARCHAR(50) PRIMARY KEY, -- Código único de la facción
    description VARCHAR(200) NOT NULL, -- Nombre de la facción
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora de creación del registro
    updated_at TIMESTAMP DEFAULT null, -- Fecha y hora de la última actualización del registro
    active BOOLEAN DEFAULT TRUE NOT NULL -- Estado de la facción
);

COMMENT ON TABLE faction IS 'Tabla que almacena información sobre las facciones.';
COMMENT ON COLUMN faction.code IS 'Código único de la facción.';
COMMENT ON COLUMN faction.description IS 'Nombre de la facción.';

----

CREATE TABLE space_ship (
    id BIGSERIAL PRIMARY KEY, -- Identificador único de la nave
    name VARCHAR(500) NOT NULL, -- Nombre de la nave
    faction_code VARCHAR(50) NOT NULL, -- Código de la facción a la que pertenece la nave
    description VARCHAR(1000), -- Descripción detallada de la nave
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora de creación del registro
    updated_at TIMESTAMP DEFAULT null, -- Fecha y hora de la última actualización del registro
    active BOOLEAN DEFAULT TRUE NOT NULL, -- Estado de la nave
    FOREIGN KEY (faction_code) REFERENCES faction(code) -- Clave foránea que referencia la facción a la que pertenece la nave
);

COMMENT ON TABLE space_ship IS 'Tabla que almacena información sobre naves espaciales.';
COMMENT ON COLUMN space_ship.id IS 'Identificador único de la nave.';
COMMENT ON COLUMN space_ship.name IS 'Nombre de la nave.';
COMMENT ON COLUMN space_ship.faction_code IS 'Código de la facción a la que pertenece la nave.';
COMMENT ON COLUMN space_ship.description IS 'Descripción detallada de la nave.';