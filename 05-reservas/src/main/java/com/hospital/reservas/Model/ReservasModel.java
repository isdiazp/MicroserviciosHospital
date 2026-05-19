package com.hospital.reservas.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name="reservas")

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

    public ReservasModel() {
    }

    public ReservasModel(Long idPaciente, Long idMedico, Long idAgenda, LocalDate fechaAtencion, LocalTime horaAtencion, String motivoConsulta, String estado, String observacion) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idAgenda = idAgenda;
        this.fechaAtencion = fechaAtencion;
        this.horaAtencion = horaAtencion;
        this.motivoConsulta = motivoConsulta;
        this.estado = estado;
        this.observacion = observacion;
    }

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(Long idReservas) {
        this.idReservas = idReservas;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalTime getHoraAtencion() {
        return horaAtencion;
    }

    public void setHoraAtencion(LocalTime horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
