package com.hospital.paciente.Service;

import com.hospital.paciente.Model.Paciente;
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

    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }

    public Paciente findById(Long idPaciente){
        return pacienteRepository.findById(idPaciente).get();
    }

    public Paciente save(Paciente paciente){
        return pacienteRepository.save(paciente);
    }


    public void delete(Long id){
        pacienteRepository.deleteById(id);
    }
}
