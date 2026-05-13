package com.hospital.especialidades.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="especialidades")
@AllArgsConstructor
@NoArgsConstructor
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
}
