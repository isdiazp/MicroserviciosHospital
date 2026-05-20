package com.hospital.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "consultas")
@Data
public class ConsultasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long idPaciente;

    @NotBlank(message = "El nombre del box es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_box", nullable = false, length = 100)
    private String nombreBox;

    @NotNull(message = "El número de piso es obligatorio")
    @Min(value = 1, message = "El piso debe ser mayor o igual a 1")
    @Column(name = "piso", nullable = false)
    private Integer piso;

    @NotBlank(message = "El tipo de box es obligatorio")
    @Size(max = 50)
    @Column(name = "tipo_box", length = 50)
    private String tipoBox;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    @Column(name = "disponible", length = 50)
    private Boolean disponible;

    public ConsultasModel() {
    }

    public ConsultasModel(Long idPaciente, Boolean disponible, String tipoBox, Integer piso, String nombreBox) {
        this.disponible = disponible;
        this.tipoBox = tipoBox;
        this.piso = piso;
        this.nombreBox = nombreBox;
        this.idPaciente = idPaciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreBox() {
        return nombreBox;
    }

    public void setNombreBox(String nombreBox) {
        this.nombreBox = nombreBox;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public String getTipoBox() {
        return tipoBox;
    }

    public void setTipoBox(String tipoBox) {
        this.tipoBox = tipoBox;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
