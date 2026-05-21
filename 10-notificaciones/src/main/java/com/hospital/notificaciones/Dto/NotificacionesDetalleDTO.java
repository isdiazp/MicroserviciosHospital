package com.hospital.notificaciones.Dto;
import com.hospital.notificaciones.model.NotificacionesModel;

public class NotificacionesDetalleDTO {

    private NotificacionesModel notificaciones;
    private PacienteDTO paciente;

    public NotificacionesDetalleDTO(NotificacionesModel notificaciones, PacienteDTO paciente) {
        this.notificaciones = notificaciones;
        this.paciente = paciente;
    }

    public NotificacionesModel getNotificaciones() {
        return notificaciones;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }
}
