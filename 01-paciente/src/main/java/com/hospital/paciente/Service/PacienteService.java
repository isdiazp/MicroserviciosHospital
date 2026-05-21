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

    public List<PacienteModel> findAll(){
        return pacienteRepository.findAll();
    }

    public PacienteModel findById(Long idPaciente){
        return pacienteRepository.findById(idPaciente).get();
    }

    public PacienteModel save(PacienteModel paciente){
        return pacienteRepository.save(paciente);
    }


    public void delete(Long id){
        pacienteRepository.deleteById(id);
    }


}
