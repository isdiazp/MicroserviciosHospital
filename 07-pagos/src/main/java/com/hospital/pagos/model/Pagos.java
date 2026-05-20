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

    @NotBlank(message = "El ID del paciente es obligatorio")
    @Column(name = "paciente_id", nullable = false)
    private Long idPaciente;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor a cero")
    @Column(name = "monto", nullable = false)
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @NotBlank(message = "El medio de pago es obligatorio")
    @Column(name = "medio_pago", nullable = false)
    private String medioPago;

    @NotBlank(message = "El estado de pago es obligatorio")
    @Column(name = "estado_pago", nullable = false)
    private String estadoPago;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    public Pagos() {
    }

    public Pagos(Boolean activo, String estadoPago, String medioPago, LocalDateTime fechaPago, Double monto, Long idPaciente) {
        this.activo = activo;
        this.estadoPago = estadoPago;
        this.medioPago = medioPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.idPaciente = idPaciente;
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

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
}
