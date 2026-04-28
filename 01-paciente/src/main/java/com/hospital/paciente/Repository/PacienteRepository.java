package com.hospital.paciente.Repository;
import com.hospital.paciente.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    // Encuentra pacientes por nombre
    List<Paciente> findByNombrePaciente(String nombrePaciente);

    // Encuentra pacientes por nombre y apellido
    List<Paciente> findByNombrePacienteAndApellidoPaciente(String nombrePaciente, String apellidoPaciente);

    // Encuentra pacientes por correo electronico
    Paciente findByCorreoPaciente(String correoPaciente);
}
