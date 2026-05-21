package com.hospital.historialclinico.service;

import com.hospital.historialclinico.Dto.HCDetalleDTO;
import com.hospital.historialclinico.Dto.MedicoDTO;
import com.hospital.historialclinico.Dto.PacienteDTO;
import com.hospital.historialclinico.Dto.ReservaDTO;
import com.hospital.historialclinico.model.HCModel;
import com.hospital.historialclinico.repository.HCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HCService {
    @Autowired
    private HCRepository repo;

    public List<HCModel> listarTodos() {
        return repo.findAll();
    }

    public HCModel guardar(HCModel historial) {
        return repo.save(historial);
    }

    public HCModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public HCModel actualizar(Long id, HCModel datosActualizados) {
        return repo.findById(id).map(historialExistente -> {
            historialExistente.setIdPaciente(datosActualizados.getIdPaciente());
            historialExistente.setIdMedico(datosActualizados.getIdMedico());
            historialExistente.setIdReserva(datosActualizados.getIdReserva());
            historialExistente.setFechaAtencion(datosActualizados.getFechaAtencion());
            historialExistente.setMotivoConsulta(datosActualizados.getMotivoConsulta());
            historialExistente.setDiagnostico(datosActualizados.getDiagnostico());
            historialExistente.setTratamiento(datosActualizados.getTratamiento());
            historialExistente.setObservaciones(datosActualizados.getObservaciones());
            historialExistente.setAlergias(datosActualizados.getAlergias());
            historialExistente.setAntecedentesMedicos(datosActualizados.getAntecedentesMedicos());

            return repo.save(historialExistente);
        }).orElseThrow(() -> new RuntimeException("Historial Clínico no encontrado con el ID: " + id));
    }


    private final WebClient webClient;

    public HCService(WebClient webClient, HCRepository repo) {
        this.webClient = webClient;
        this.repo = repo;
    }

    public Mono<HCDetalleDTO> obtenerHistorialMedicoGeneral(Long id) {


        Mono<HCModel> llamadaHistorialClinico =
                Mono.fromCallable(() -> repo.findById(id)
                        .orElseThrow());


        return llamadaHistorialClinico.flatMap(historial -> {

            Mono<PacienteDTO> llamadaPaciente = webClient.get()
                    .uri("http://localhost:8081/api/v1/pacientes/{id}",
                            historial.getIdPaciente())
                    .retrieve()
                    .bodyToMono(PacienteDTO.class);

            Mono<MedicoDTO> llamadaMedico = webClient.get()
                    .uri("http://localhost:8082/api/v1/medicos/{id}",
                            historial.getIdMedico())
                    .retrieve()
                    .bodyToMono(MedicoDTO.class);

            Mono<ReservaDTO> llamadaReserva = webClient.get()
                    .uri("http://localhost:8085/api/v1/reservas/{id}",
                            historial.getIdReserva())
                    .retrieve()
                    .bodyToMono(ReservaDTO.class);


            return Mono.zip(Mono.just(historial), llamadaPaciente,llamadaMedico, llamadaReserva)
                    .map(tupla -> {

                        HCModel historialResult = tupla.getT1();
                        PacienteDTO paciente = tupla.getT2();
                        MedicoDTO medico = tupla.getT3();
                        ReservaDTO reserva = tupla.getT4();

                        return new HCDetalleDTO(
                                historialResult,
                                paciente,
                                medico,
                                reserva
                        );
                    });
        });
    }
}
