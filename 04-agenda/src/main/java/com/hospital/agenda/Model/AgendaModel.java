package com.hospital.agenda.Model;

import jakarta.persistence.*;
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

    @Column(name="idMedico",nullable=false)
    private Long idMedico;

    @Column(name="fecha",nullable=false)
    private LocalDate fecha;

    @Column(name="horaInicio",nullable=false)
    private LocalTime horaInicio;

    @Column(name="horaFin",nullable=false)
    private LocalTime horaFin;

    @Column(name="duracionMinutos",nullable=false)
    private Integer duracionMinutos;

    @Column(name="cuposDisponibles",nullable=false)
    private Integer cuposDisponibles;

    @Column(name="estado",nullable=false)
    private String estado;

    @Column(name="activo",nullable=false)
    private boolean activo;
}
