package com.hospital.pagos.Dto;

import com.hospital.pagos.model.Pagos;

public class PagosDetalleDTO {
    private Pagos pago;
    private PacienteDTO paciente;

    public PagosDetalleDTO(Pagos pago, PacienteDTO paciente) {
        this.pago = pago;
        this.paciente = paciente;
    }

    public Pagos getPago() {
        return pago;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }
}
