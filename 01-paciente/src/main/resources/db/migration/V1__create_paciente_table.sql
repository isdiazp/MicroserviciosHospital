CREATE TABLE paciente(
    id_paciente BIGINT PRIMARY KEY AUTO_INCREMENT,
    rut_paciente VARCHAR(13) UNIQUE NOT NULL,
    nombre_paciente VARCHAR(50) NOT NULL,
    apellido_paciente VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    sexo_paciente VARCHAR(20) NOT NULL,
    correo_paciente VARCHAR(60) NOT NULL,
    telefono_paciente VARCHAR(15) NOT NULL,
    direccion_paciente VARCHAR(50)
);