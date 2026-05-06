package com.hospital.medico.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="medico")
@AllArgsConstructor
@NoArgsConstructor

public class MedicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedico;

    @Column(name="rutMedico",unique=true, length=13, nullable=false)
    private String rutMedico;

    @Column(name="nombreMedico",nullable=false)
    private String nombreMedico;

    @Column(name="apellidoMedico",nullable=false)
    private String apellidoMedico;

    @Column(name="telefonoMedico",nullable=false)
    private String telefonoMedico;

    @Column(name="correoMedico",nullable=false)
    private String correoMedico;

    @Column(name="idEspecialidad",nullable=false)
    private Integer idEspecialidad;

    @Column(name="anniosExperiencia",nullable=false)
    private Integer anniosExperiencia;



}
