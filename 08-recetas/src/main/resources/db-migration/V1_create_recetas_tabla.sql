CREATE TABLE recetas (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
paciente_id BIGINT NOT NULL,
medico_id BIGINT NOT NULL,
reserva_id BIGINT NOT NULL,
fecha_emision DATE NOT NULL,
diagnostico TEXT,
medicamentos TEXT NOT NULL,
indicaciones TEXT,
vigencia_dias INT
);