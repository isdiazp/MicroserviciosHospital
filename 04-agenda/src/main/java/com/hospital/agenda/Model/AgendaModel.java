package com.hospital.agenda.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@Table(name="agenda")
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

    public AgendaModel() {
    }

    public AgendaModel(Long idMedico, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Integer duracionMinutos, Integer cuposDisponibles, String estado, boolean activo) {
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.duracionMinutos = duracionMinutos;
        this.cuposDisponibles = cuposDisponibles;
        this.estado = estado;
        this.activo = activo;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(Integer cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }


}
