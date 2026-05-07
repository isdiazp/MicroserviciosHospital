package com.hospital.recetas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "recetas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetasModel {
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

    @NotNull(message = "La fecha de emisión es obligatoria")
    @PastOrPresent(message = "La fecha de emisión no puede ser futura")
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @NotBlank(message = "La lista de medicamentos no puede estar vacía")
    @Column(name = "medicamentos", columnDefinition = "TEXT", nullable = false)
    private String medicamentos;

    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    @Positive(message = "La vigencia en días debe ser un número positivo")
    @Column(name = "vigencia_dias")
    private Integer vigenciaDias;
}