package com.hospital.notificaciones.model;
import jakarta.persistence.*;
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
    @Column(name = "id")
    private Long id;

    // Solo necesitamos el ID del paciente (del microservicio 1)
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "mensaje", columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    // Ej: EMAIL, SMS, WHATSAPP
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    // Ej: ENVIADA, PENDIENTE, ERROR
    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
}
