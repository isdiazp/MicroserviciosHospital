package com.hospital.consultas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_box", nullable = false, length = 100)
    private String nombreBox;

    @Column(name = "ubicacion", length = 255)
    private String ubicacion;

    @Column(name = "piso")
    private Integer piso;

    @Column(name = "tipo_box", length = 50)
    private String tipoBox;

    @Column(name = "equipamiento", columnDefinition = "TEXT")
    private String equipamiento;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "activo")
    private Boolean activo;
}
