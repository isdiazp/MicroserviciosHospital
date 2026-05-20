package com.hospital.historialclinico.service;

import com.hospital.historialclinico.model.HCModel;
import com.hospital.historialclinico.repository.HCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class HCService {
    @Autowired
    private HCRepository repo;

    public List<HCModel> listarTodos() {
        return repo.findAll();
    }

    public HCModel guardar(HCModel historial) {
        return repo.save(historial);
    }

    public HCModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public HCModel actualizar(Long id, HCModel datosActualizados) {
        return repo.findById(id).map(historialExistente -> {
            historialExistente.setIdPaciente(datosActualizados.getIdPaciente());
            historialExistente.setIdMedico(datosActualizados.getIdMedico());
            historialExistente.setIdReserva(datosActualizados.getIdReserva());
            historialExistente.setFechaAtencion(datosActualizados.getFechaAtencion());
            historialExistente.setMotivoConsulta(datosActualizados.getMotivoConsulta());
            historialExistente.setDiagnostico(datosActualizados.getDiagnostico());
            historialExistente.setTratamiento(datosActualizados.getTratamiento());
            historialExistente.setObservaciones(datosActualizados.getObservaciones());
            historialExistente.setAlergias(datosActualizados.getAlergias());
            historialExistente.setAntecedentesMedicos(datosActualizados.getAntecedentesMedicos());

            return repo.save(historialExistente);
        }).orElseThrow(() -> new RuntimeException("Historial Clínico no encontrado con el ID: " + id));
    }
}
