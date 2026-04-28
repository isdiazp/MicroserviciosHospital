package com.hospital.consultas.service;

import com.hospital.consultas.model.ConsultasModel;
import com.hospital.consultas.repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultasService {

    @Autowired
    private ConsultasRepository repo;

    public List<ConsultasModel> listarTodos() {
        return repo.findAll(); // [cite: 60]
    }

    public ConsultasModel guardar(ConsultasModel box) {
        return repo.save(box);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public ConsultasModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
}