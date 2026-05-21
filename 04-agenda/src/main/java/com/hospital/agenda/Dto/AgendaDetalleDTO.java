package com.hospital.agenda.Dto;

import com.hospital.agenda.Model.AgendaModel;

public class AgendaDetalleDTO {

    private AgendaModel agenda;
    private MedicoDTO medico;

    public AgendaDetalleDTO(AgendaModel agenda, MedicoDTO medico) {
        this.agenda = agenda;
        this.medico = medico;
    }

    public AgendaModel getAgenda() {
        return agenda;
    }

    public MedicoDTO getMedico() {
        return medico;
    }
}
