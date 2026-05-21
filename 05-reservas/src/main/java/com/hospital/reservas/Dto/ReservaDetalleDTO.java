package com.hospital.reservas.Dto;

import com.hospital.reservas.Model.ReservasModel;

public class ReservaDetalleDTO {

    private ReservasModel reserva;
    private MedicoDTO medico;
    private PacienteDTO paciente;
    private AgendaDTO agenda;

    public ReservaDetalleDTO(ReservasModel reserva, MedicoDTO medico, PacienteDTO paciente, AgendaDTO agenda) {
        this.reserva = reserva;
        this.medico = medico;
        this.paciente = paciente;
        this.agenda = agenda;
    }


    public ReservasModel getReserva() {
        return reserva;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public AgendaDTO getAgenda() {
        return agenda;
    }
}
