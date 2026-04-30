package com.hospital.historialClinico.model;

import jakarta.persistence.*;
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
    @Column(name = "id")
    private Long id;

    // Relaciones por ID a los microservicios de tu compañero
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "reserva_id", nullable = false)
    private Long reservaId;

    @Column(name = "fecha_atencion", nullable = false)
    private LocalDate fechaAtencion;

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "antecedentes_medicos", columnDefinition = "TEXT")
    private String antecedentesMedicos;
}
