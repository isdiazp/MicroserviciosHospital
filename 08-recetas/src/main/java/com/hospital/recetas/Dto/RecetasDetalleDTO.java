package com.hospital.recetas.Dto;

import com.hospital.recetas.model.RecetasModel;

public class RecetasDetalleDTO {

    private RecetasModel recetas;
    private PacienteDTO paciente;
    private MedicoDTO medico;
    private ReservaDTO reserva;

    //Constructor

    public RecetasDetalleDTO(RecetasModel recetas, PacienteDTO paciente, MedicoDTO medico, ReservaDTO reserva) {
        this.recetas = recetas;
        this.paciente = paciente;
        this.medico = medico;
        this.reserva = reserva;
    }

    // Solo getters
    public RecetasModel getRecetas() {
        return recetas;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }
}
