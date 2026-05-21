package com.hospital.medico.Dto;

import com.hospital.medico.Model.MedicoModel;

public class MedicoDetalleDTO {

    private MedicoModel medico;
    private EspecialidadDTO especialidad;

    public MedicoDetalleDTO(MedicoModel medico, EspecialidadDTO especialidad) {
        this.medico = medico;
        this.especialidad = especialidad;
    }

    public MedicoModel getMedico() {
        return medico;
    }

    public EspecialidadDTO getEspecialidad() {
        return especialidad;
    }
}
