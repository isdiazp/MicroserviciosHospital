package com.hospital.agenda.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@Table(name="agenda")
@AllArgsConstructor
@NoArgsConstructor
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idAgenda")
    private Long idAgenda;

    @NotNull(message="EL id del medico es Obligatorio")
    @Column(name="idMedico",nullable=false)
    private Long idMedico;

    @NotNull(message="La fecha es Obligatorio")
    @Column(name="fecha",nullable=false)
    private LocalDate fecha;

    @NotNull(message="La hora de inicio es Obligatorio")
    @Column(name="horaInicio",nullable=false)
    private LocalTime horaInicio;

    @NotNull(message="La hora fin es Obligatorio")
    @Column(name="horaFin",nullable=false)
    private LocalTime horaFin;

    @NotNull(message="La duracion en minutos es Obligatorio")
    @Column(name="duracionMinutos",nullable=false)
    private Integer duracionMinutos;

    @NotNull(message="El cupo disponible es Obligatorio")
    @Column(name="cuposDisponibles",nullable=false)
    private Integer cuposDisponibles;

    @NotBlank(message="El estado es Obligatorio")
    @Column(name="estado",nullable=false, length=50)
    private String estado;

    @NotNull(message="Activo o inactivo es Obligatorio")
    @Column(name="activo",nullable=false)
    private boolean activo;
}
