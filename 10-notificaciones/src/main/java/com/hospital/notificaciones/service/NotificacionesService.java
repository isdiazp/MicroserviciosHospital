package com.hospital.notificaciones.service;

import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.repository.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepository repo;

    public List<NotificacionesModel> listarTodas() {
        return repo.findAll();
    }

    public NotificacionesModel guardar(NotificacionesModel notificacion) {
        return repo.save(notificacion);
    }

    public NotificacionesModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public NotificacionesModel actualizar(Long id, NotificacionesModel datosActualizados) {
        return repo.findById(id).map(notificacionExistente -> {
            notificacionExistente.setIdPaciente(datosActualizados.getIdPaciente());
            notificacionExistente.setMensaje(datosActualizados.getMensaje());
            notificacionExistente.setTipo(datosActualizados.getTipo());
            notificacionExistente.setEstado(datosActualizados.getEstado());
            notificacionExistente.setFechaEnvio(datosActualizados.getFechaEnvio());

            return repo.save(notificacionExistente);
        }).orElseThrow(() -> new RuntimeException("Notificación no encontrada con el ID: " + id));
    }

}
