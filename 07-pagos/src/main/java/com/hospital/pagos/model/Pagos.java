package com.hospital.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Importante para las validaciones
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    @Size(min = 9, max = 12)
    @Column(nullable = false)
    private String rutPaciente;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor a cero")
    @Column(nullable = false)
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @NotBlank(message = "El medio de pago es obligatorio")
    @Column(nullable = false)
    private String medioPago;

    @NotBlank(message = "El estado de pago es obligatorio")
    @Column(nullable = false)
    private String estadoPago;

    @NotNull
    @Column(nullable = false)
    private Boolean activo;

    public Pagos() {
    }

    public Pagos(Boolean activo, String estadoPago, String medioPago, LocalDateTime fechaPago, Double monto, String rutPaciente) {
        this.activo = activo;
        this.estadoPago = estadoPago;
        this.medioPago = medioPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.rutPaciente = rutPaciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getRutPaciente() {
        return rutPaciente;
    }

    public void setRutPaciente(String rutPaciente) {
        this.rutPaciente = rutPaciente;
    }
}
