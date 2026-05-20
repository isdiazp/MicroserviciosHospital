package com.hospital.historialclinico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "historiales_clinicos")
@Data
public class HCModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long idPaciente;

    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "medico_id", nullable = false)
    private Long idMedico;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(name = "reserva_id", nullable = false)
    private Long idReserva;

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

    public HCModel() {
    }

    public HCModel(Long idPaciente, Long idMedico, Long idReserva, String motivoConsulta, LocalDate fechaAtencion, String diagnostico, String tratamiento, String observaciones, String alergias, String antecedentesMedicos) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idReserva = idReserva;
        this.motivoConsulta = motivoConsulta;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.alergias = alergias;
        this.antecedentesMedicos = antecedentesMedicos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntecedentesMedicos() {
        return antecedentesMedicos;
    }

    public void setAntecedentesMedicos(String antecedentesMedicos) {
        this.antecedentesMedicos = antecedentesMedicos;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
}