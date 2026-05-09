CREATE TABLE agenda(
    id_agenda BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_medico BIGINT NOT NULL,
    fecha DATE NOT NULL,
    fecha_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    duracion_minutos INT NOT NULL,
    cupos_disponibles INT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);