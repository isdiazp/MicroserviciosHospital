package com.hospital.consultas.service;

import com.hospital.consultas.model.ConsultasModel;
import com.hospital.consultas.repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;

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

    @Transactional
    public ConsultasModel actualizar(Long id, ConsultasModel datosActualizados) {
        // 1. Buscamos si la consulta existe
        return repo.findById(id).map(consultaExistente -> {
            // 2. Actualizamos campo por campo según tu modelo
            consultaExistente.setNombreBox(datosActualizados.getNombreBox());
            consultaExistente.setPiso(datosActualizados.getPiso());
            consultaExistente.setTipoBox(datosActualizados.getTipoBox());
            consultaExistente.setDisponible(datosActualizados.getDisponible());

            // 3. Guardamos los cambios
            return repo.save(consultaExistente);
        }).orElseThrow(() -> new RuntimeException("Consulta/Box no encontrado con el ID: " + id));
    }
}