package com.hospital.recetas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "recetas")
@Data
public class RecetasModel {
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

    public RecetasModel() {
    }

    public RecetasModel(LocalDate fechaEmision, Long idReserva, Long idMedico, Long idPaciente, String diagnostico, String medicamentos, String indicaciones, Integer vigenciaDias) {
        this.fechaEmision = fechaEmision;
        this.idReserva = idReserva;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.diagnostico = diagnostico;
        this.medicamentos = medicamentos;
        this.indicaciones = indicaciones;
        this.vigenciaDias = vigenciaDias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVigenciaDias() {
        return vigenciaDias;
    }

    public void setVigenciaDias(Integer vigenciaDias) {
        this.vigenciaDias = vigenciaDias;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
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