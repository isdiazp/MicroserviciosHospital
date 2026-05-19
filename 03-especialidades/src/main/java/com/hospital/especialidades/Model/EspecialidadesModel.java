package com.hospital.especialidades.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name="especialidades")
public class EspecialidadesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idEspecialidad")
    private Long idEspecialidad;

    @NotBlank(message="El nombre de la especialidad es Obligatorio")
    @Column(name="nombreEspecialidad",nullable = false,  length=50)
    private String nombreEspecialidad;

    @NotBlank(message="La descripcion de la especilidad es Obligatorio")
    @Column(name="descripcionEspecialidad",nullable = false, length=200)
    private String descripcionEspecialidad;

    @NotBlank(message="El area medica es Obligatorio")
    @Column(name="areaMedica",nullable = false, length=40)
    private String areaMedica;

    public EspecialidadesModel() {
    }

    public EspecialidadesModel(String areaMedica, String descripcionEspecialidad, String nombreEspecialidad) {
        this.areaMedica = areaMedica;
        this.descripcionEspecialidad = descripcionEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getDescripcionEspecialidad() {
        return descripcionEspecialidad;
    }

    public void setDescripcionEspecialidad(String descripcionEspecialidad) {
        this.descripcionEspecialidad = descripcionEspecialidad;
    }

    public String getAreaMedica() {
        return areaMedica;
    }

    public void setAreaMedica(String areaMedica) {
        this.areaMedica = areaMedica;
    }
}
