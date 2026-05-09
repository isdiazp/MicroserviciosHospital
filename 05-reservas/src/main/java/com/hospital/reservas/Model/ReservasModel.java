package com.hospital.reservas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name="reservas")
@AllArgsConstructor
@NoArgsConstructor

public class ReservasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idReservas")
    private Long idReservas;

    @Column(name="idPaciente", nullable=false)
    private Long idPaciente;

    @Column(name="idMedico", nullable=false)
    private Long idMedico;

    @Column(name="idAgenda", nullable=false)
    private Long idAgenda;

    @Column(name="fechaAtencion",nullable=false)
    private LocalDate fechaAtencion;

    @Column(name="horaAtencion",nullable =false)
    private LocalTime horaAtencion;

    @Column(name="motivoConsulta", nullable=false)
    private String motivoConsulta;

    @Column(name="estado", nullable=false)
    private String estado; //PENDIENTE, CONFIRMADO, CANCELADO, ANTENTIDO

    @Column(name="observacion", nullable=false)
    private String observacion;




}
