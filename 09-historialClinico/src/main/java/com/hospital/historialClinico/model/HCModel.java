package com.hospital.historialClinico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "historiales_clinicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HCModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(name = "reserva_id", nullable = false)
    private Long reservaId;

    @NotNull(message = "La fecha de atención es obligatoria")
    @PastOrPresent(message = "La fecha de atención no puede ser futura")
    @Column(name = "fecha_atencion", nullable = false)
    private LocalDate fechaAtencion;

    @NotBlank(message = "El motivo de la consulta es obligatorio")
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "antecedentes_medicos", columnDefinition = "TEXT")
    private String antecedentesMedicos;
}