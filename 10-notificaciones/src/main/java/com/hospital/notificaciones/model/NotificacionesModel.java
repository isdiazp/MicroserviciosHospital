package com.hospital.notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

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
}