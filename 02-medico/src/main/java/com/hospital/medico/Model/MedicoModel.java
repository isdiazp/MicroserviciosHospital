package com.hospital.medico.Model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
@Entity
@Data
@Table(name="medico")

public class MedicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idMedico")
    private Long idMedico;

    @NotBlank(message="El rut del medico es Obligatorio")
    @Column(name="rutMedico",unique=true, length=13, nullable=false)
    private String rutMedico;

    @NotBlank(message="EL nombre del medico es Obligatorio")
    @Column(name="nombreMedico",nullable=false, length=50)
    private String nombreMedico;

    @NotBlank(message="EL apellido del medico es Obligatorio")
    @Column(name="apellidoMedico",nullable=false, length=50)
    private String apellidoMedico;

    @NotBlank(message="El telefono del medico es Obligatorio")
    @Column(name="telefonoMedico",nullable=false, length=15)
    private String telefonoMedico;

    @NotBlank(message="El correo del medico es Obligatorio")
    @Column(name="correoMedico",nullable=false, length=50)
    private String correoMedico;

    @NotNull(message="El id de Especialidad es Obligatorio")
    @Column(name="idEspecialidad",nullable=false)
    private Long idEspecialidad;

    @NotNull(message="EL annio de experiencia es Obligatorio")
    @Column(name="anniosExperiencia",nullable=false)
    private Integer anniosExperiencia;

    public MedicoModel() {
    }

    public MedicoModel(String rutMedico, String nombreMedico, String apellidoMedico, String telefonoMedico, String correoMedico, Long idEspecialidad, Integer anniosExperiencia) {
        this.rutMedico = rutMedico;
        this.nombreMedico = nombreMedico;
        this.apellidoMedico = apellidoMedico;
        this.telefonoMedico = telefonoMedico;
        this.correoMedico = correoMedico;
        this.idEspecialidad = idEspecialidad;
        this.anniosExperiencia = anniosExperiencia;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getRutMedico() {
        return rutMedico;
    }

    public void setRutMedico(String rutMedico) {
        this.rutMedico = rutMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getTelefonoMedico() {
        return telefonoMedico;
    }

    public void setTelefonoMedico(String telefonoMedico) {
        this.telefonoMedico = telefonoMedico;
    }

    public String getCorreoMedico() {
        return correoMedico;
    }

    public void setCorreoMedico(String correoMedico) {
        this.correoMedico = correoMedico;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getAnniosExperiencia() {
        return anniosExperiencia;
    }

    public void setAnniosExperiencia(Integer anniosExperiencia) {
        this.anniosExperiencia = anniosExperiencia;
    }
}
