package com.hospital.reservas.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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


    @NotNull(message="El id del paciente es Obligatorio")
    @Column(name="idPaciente", nullable=false)
    private Long idPaciente;

    @NotNull(message="El id del medico es Obligatorio")
    @Column(name="idMedico", nullable=false)
    private Long idMedico;

    @NotNull(message="El id de la agenda es Obligatorio")
    @Column(name="idAgenda", nullable=false)
    private Long idAgenda;

    @NotNull(message="la fecha de atencion es Obligatorio")
    @Column(name="fechaAtencion",nullable=false)
    private LocalDate fechaAtencion;

    @NotNull(message="La hora de atencion es Obligatorio")
    @Column(name="horaAtencion",nullable =false)
    private LocalTime horaAtencion;

    @NotBlank(message="El motivo de la consulta es Obligatorio")
    @Column(name="motivoConsulta", nullable=false, length=100)
    private String motivoConsulta;

    @NotBlank(message="El estado de reserva es Obligatorio")
    @Column(name="estado", nullable=false, length=20)
    private String estado; //PENDIENTE, CONFIRMADO, CANCELADO, ANTENTIDO

    @NotBlank(message="La observacion de la reserva es Obligatorio")
    @Column(name="observacion", nullable=false, length=255)
    private String observacion;
}
