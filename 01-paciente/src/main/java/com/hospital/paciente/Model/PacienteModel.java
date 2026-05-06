package com.hospital.paciente.Model;

import jakarta.persistence.*;
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

    @Column(name = "rutPaciente" ,unique=true, length=13, nullable=false)
    private String rutPaciente;

    @Column(name="nombrePaciente",nullable=false)
    private String nombrePaciente;

    @Column(name="apellidoPaciente" ,nullable=false)
    private String apellidoPaciente;

    @Column(name="fechaNacimiento",nullable=true)
    private LocalDate fechaNacimiento;

    @Column(name="sexoPaciente",nullable=false)
    private String sexoPaciente;

    @Column(name="correoPaciente",nullable=false)
    private String correoPaciente;

    @Column(name="telefonoPaciente",nullable=false)
    private String telefonoPaciente;

    @Column(name="direccionPaciente",nullable=true)
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
