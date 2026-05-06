CREATE TABLE medico(
    idMedico INTEGER PRIMARY KEY AUTO_INCREMENT,
    rutMedico VARCHAR(13) UNIQUE NOT NULL,
    nombreMedico VARCHAR(50) NOT NULL,
    apellidoMedico VARCHAR(50) NOT NULL,
    telefonoMedico VARCHAR(15) NOT NULL,
    correoMedico VARCHAR(50) NOT NULL,
    idEspecialidad INTEGER NOT NULL,
    anniosExperiencia INT NOT NULL




);