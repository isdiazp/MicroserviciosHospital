package com.hospital.notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class NotificacionesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long idPaciente;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(name = "mensaje", columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @NotBlank(message = "El tipo de notificación (EMAIL/SMS/ETC) es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    public NotificacionesModel() {
    }

    public NotificacionesModel(Long idPaciente, String mensaje, String tipo, String estado, LocalDateTime fechaEnvio) {
        this.idPaciente = idPaciente;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
}