package com.hospital.notificaciones.repository;
import com.hospital.notificaciones.model.NotificacionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NotificacionesRepository extends JpaRepository<NotificacionesModel, Long> {
}
