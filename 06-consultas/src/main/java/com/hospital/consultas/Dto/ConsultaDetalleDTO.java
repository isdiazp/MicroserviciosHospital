package com.hospital.consultas.Dto;

import com.hospital.consultas.model.ConsultasModel;

public class ConsultaDetalleDTO {

    private ConsultasModel consulta;
    private PacienteDTO paciente;

    public ConsultaDetalleDTO(ConsultasModel consulta, PacienteDTO paciente) {
        this.consulta = consulta;
        this.paciente = paciente;
    }

    public ConsultasModel getConsulta() {
        return consulta;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }
}
