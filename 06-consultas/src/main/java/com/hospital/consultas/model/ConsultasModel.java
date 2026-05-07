package com.hospital.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Importante
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del box es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_box", nullable = false, length = 100)
    private String nombreBox;

    @NotNull(message = "El número de piso es obligatorio")
    @Min(value = 1, message = "El piso debe ser mayor o igual a 1")
    @Column(nullable = false)
    private Integer piso;

    @NotBlank(message = "El tipo de box es obligatorio")
    @Size(max = 50)
    @Column(name = "tipo_box", length = 50)
    private String tipoBox;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible;
}
