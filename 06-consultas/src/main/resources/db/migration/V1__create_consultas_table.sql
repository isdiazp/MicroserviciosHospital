CREATE TABLE consultas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    nombre_box VARCHAR(100) NOT NULL,
    piso INTEGER NOT NULL,
    tipo_box VARCHAR(50),
    disponible BOOLEAN
);