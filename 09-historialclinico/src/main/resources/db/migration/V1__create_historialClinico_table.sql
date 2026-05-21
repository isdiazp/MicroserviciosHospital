CREATE TABLE historiales_clinicos(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    reserva_id BIGINT NOT NULL,
    fecha_atencion DATE NOT NULL,
    motivo_consulta TEXT NOT NULL,
    diagnostico TEXT NOT NULL,
    tratamiento TEXT,
    observaciones TEXT,
    alergias TEXT,
    antecedentes_medicos TEXT
);
