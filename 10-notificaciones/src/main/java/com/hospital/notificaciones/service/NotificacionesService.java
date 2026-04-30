package com.hospital.notificaciones.service;

import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.repository.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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

}
