package com.hospital.medico.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Entity
@Data
@Table(name="medico")
@AllArgsConstructor
@NoArgsConstructor

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



}
