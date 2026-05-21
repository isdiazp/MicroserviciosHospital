package com.hospital.historialclinico.Dto;

import com.hospital.historialclinico.model.HCModel;
public class HCDetalleDTO {

    private HCModel hcModel;
    private PacienteDTO paciente;
    private MedicoDTO medico;
    private ReservaDTO reserva;

    // constructor
    public HCDetalleDTO(HCModel hcModel, PacienteDTO paciente, MedicoDTO medico, ReservaDTO reserva) {
        this.hcModel = hcModel;
        this.paciente = paciente;
        this.medico = medico;
        this.reserva = reserva;
    }

    // getters

    public HCModel getHcModel() {
        return hcModel;
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
