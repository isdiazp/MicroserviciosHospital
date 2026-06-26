package com.hospital.paciente.Service;

import com.hospital.paciente.Model.PacienteModel;
import com.hospital.paciente.Repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@Transactional
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteModel> listarTodos(){
        return pacienteRepository.findAll();
    }

    public PacienteModel buscarPorId(Long idPaciente){
        return pacienteRepository.findById(idPaciente).get();
    }

    public PacienteModel guardar(PacienteModel paciente){
        return pacienteRepository.save(paciente);
    }

    public PacienteModel actualizar(Long idPaciente, PacienteModel paciente){
        PacienteModel existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        existente.setRutPaciente(paciente.getRutPaciente());
        existente.setNombrePaciente(paciente.getNombrePaciente());
        existente.setApellidoPaciente(paciente.getApellidoPaciente());
        existente.setFechaNacimiento(paciente.getFechaNacimiento());
        existente.setSexoPaciente(paciente.getSexoPaciente());
        existente.setCorreoPaciente(paciente.getCorreoPaciente());
        existente.setTelefonoPaciente(paciente.getTelefonoPaciente());
        existente.setDireccionPaciente(paciente.getDireccionPaciente());

        return pacienteRepository.save(existente);
    }


    public void delete(Long id){
        pacienteRepository.deleteById(id);
    }


}
