CREATE TABLE reservas(
    id_reservas BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    id_agenda BIGINT NOT NULL,
    fecha_atencion DATE NOT NULL,
    hora_atencion TIME NOT NULL,
    motivo_consulta VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    observacion VARCHAR(255) NOT NULL

);