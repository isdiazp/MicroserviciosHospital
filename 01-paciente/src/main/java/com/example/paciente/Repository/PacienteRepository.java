package com.example.paciente.Repository;
import com.example.paciente.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    // Encuentra pacientes por nombre
    List<Paciente> findByNombrePaciente(String nombrePaciente);

    // Encuentra pacientes por nombre y apellido
    List<Paciente> findByNombrePacienteAndApellidoPaciente(String nombrePaciente, String apellidoPaciente);

    // Encuentra pacientes por correo electronico
    Paciente findByCorreoPaciente(String correoPaciente);
}
