package com.hospital.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Importante para las validaciones
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor // Con estas de Lombok ya no necesitas escribir los constructores a mano
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
}
