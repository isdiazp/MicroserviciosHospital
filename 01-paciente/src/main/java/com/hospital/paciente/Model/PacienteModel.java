package com.hospital.paciente.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name= "paciente")
public class PacienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPaciente")
    private Long idPaciente;

    @NotBlank(message="El rut debe ser Obligatorio")
    @Column(name = "rutPaciente" ,unique=true, length=13, nullable=false)
    private String rutPaciente;

    @NotBlank(message="El nombre del paciente es Obligatorio")
    @Column(name="nombrePaciente",nullable=false, length=50)
    private String nombrePaciente;


    @NotBlank(message="El apellido del paciente es Obligatorio")
    @Column(name="apellidoPaciente" ,nullable=false, length=50)
    private String apellidoPaciente;

    @NotNull(message="La fecha de nacimiento del paciente es Opcional")
    @Column(name="fechaNacimiento",nullable=true)
    private LocalDate fechaNacimiento;

    @NotBlank(message="El sexo del paciente es Obligatorio")
    @Column(name="sexoPaciente",nullable=false, length=20)
    private String sexoPaciente;

    @NotBlank(message="El correo del paciente es Obligatorio")
    @Column(name="correoPaciente",nullable=false,  length=60)
    private String correoPaciente;

    @NotBlank(message="El telefono del paciente es Obligatorio")
    @Column(name="telefonoPaciente",nullable=false, length=15)
    private String telefonoPaciente;

    @NotBlank(message="La direccion del paciente es Opcional")
    @Column(name="direccionPaciente",nullable=true,  length=50)
    private String direccionPaciente;


    public PacienteModel(Long idPaciente, String rutPaciente, String nombrePaciente, String apellidoPaciente, LocalDate fechaNacimiento, String sexoPaciente, String correoPaciente, String telefonoPaciente, String direccionPaciente) {
        this.idPaciente = idPaciente;
        this.rutPaciente = rutPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.fechaNacimiento = fechaNacimiento;
        this.sexoPaciente = sexoPaciente;
        this.correoPaciente = correoPaciente;
        this.telefonoPaciente = telefonoPaciente;
        this.direccionPaciente = direccionPaciente;
    }

    public PacienteModel() {
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getRutPaciente() {
        return rutPaciente;
    }

    public void setRutPaciente(String rutPaciente) {
        this.rutPaciente = rutPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(String sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }

    public String getCorreoPaciente() {
        return correoPaciente;
    }

    public void setCorreoPaciente(String correoPaciente) {
        this.correoPaciente = correoPaciente;
    }

    public String getTelefonoPaciente() {
        return telefonoPaciente;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
    }

    public String getDireccionPaciente() {
        return direccionPaciente;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
    }
}
