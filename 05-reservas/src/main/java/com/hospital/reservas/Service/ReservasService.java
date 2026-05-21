package com.hospital.reservas.Service;

import com.hospital.reservas.Dto.MedicoDTO;
import com.hospital.reservas.Dto.PacienteDTO;
import com.hospital.reservas.Dto.AgendaDTO;
import com.hospital.reservas.Dto.ReservaDetalleDTO;
import com.hospital.reservas.Model.ReservasModel;
import com.hospital.reservas.Repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
@Transactional
public class ReservasService {

    @Autowired
    private ReservasRepository reservasRepository;

    public List<ReservasModel> findAll(){
        return reservasRepository.findAll();
    }

    public ReservasModel findById(Long idReservas){
        return reservasRepository.findById(idReservas).get();
    }

    public ReservasModel save(ReservasModel reservas){
        return reservasRepository.save(reservas);
    }

    public void delete(Long id){
        reservasRepository.deleteById(id);
    }


    private final WebClient webClient;

    public ReservasService(WebClient webClient, ReservasRepository reservasRepository) {
        this.webClient = webClient;
        this.reservasRepository = reservasRepository;
    }

    public Mono<ReservaDetalleDTO> obtenerReservasConPacienteMedicoAgenda(Long idReservas) {


        Mono<ReservasModel> llamadaReservas =
                Mono.fromCallable(() -> reservasRepository.findById(idReservas)
                        .orElseThrow());


        return llamadaReservas.flatMap(reserva -> {

            Mono<PacienteDTO> llamadaPaciente = webClient.get()
                    .uri("http://localhost:8081/api/v1/pacientes/{id}",
                            reserva.getIdPaciente())
                    .retrieve()
                    .bodyToMono(PacienteDTO.class);

            Mono<MedicoDTO> llamadaMedico = webClient.get()
                    .uri("http://localhost:8082/api/v1/medicos/{id}",
                            reserva.getIdMedico())
                    .retrieve()
                    .bodyToMono(MedicoDTO.class);

            Mono<AgendaDTO> llamadaAgenda = webClient.get()
                    .uri("http://localhost:8084/api/v1/agendas/{id}",
                            reserva.getIdAgenda())
                    .retrieve()
                    .bodyToMono(AgendaDTO.class);


            return Mono.zip(Mono.just(reserva), llamadaMedico,llamadaPaciente,llamadaAgenda)
                    .map(tupla -> {

                        ReservasModel agendaResult = tupla.getT1();
                        MedicoDTO medico = tupla.getT2();
                        PacienteDTO paciente = tupla.getT3();
                        AgendaDTO agenda = tupla.getT4();

                        return new ReservaDetalleDTO(
                                agendaResult,
                                medico,
                                paciente,
                                agenda
                        );
                    });
        });
    }
}
