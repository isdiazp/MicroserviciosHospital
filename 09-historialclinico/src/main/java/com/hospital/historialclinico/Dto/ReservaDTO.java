package com.hospital.historialclinico.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDTO {

    private Long idReservas;
    private LocalDate fechaAtencion;
    private LocalTime horaAtencion;
    private String motivoConsulta;
    private String observacion;


    // Getters y setters


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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
