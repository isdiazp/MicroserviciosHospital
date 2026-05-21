package com.hospital.notificaciones.service;

import com.hospital.notificaciones.Dto.NotificacionesDetalleDTO;
import com.hospital.notificaciones.Dto.PacienteDTO;
import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.repository.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepository repo;

    public List<NotificacionesModel> listarTodas() {
        return repo.findAll();
    }

    public NotificacionesModel guardar(NotificacionesModel notificacion) {
        return repo.save(notificacion);
    }

    public NotificacionesModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public NotificacionesModel actualizar(Long id, NotificacionesModel datosActualizados) {
        return repo.findById(id).map(notificacionExistente -> {
            notificacionExistente.setIdPaciente(datosActualizados.getIdPaciente());
            notificacionExistente.setMensaje(datosActualizados.getMensaje());
            notificacionExistente.setTipo(datosActualizados.getTipo());
            notificacionExistente.setEstado(datosActualizados.getEstado());
            notificacionExistente.setFechaEnvio(datosActualizados.getFechaEnvio());

            return repo.save(notificacionExistente);
        }).orElseThrow(() -> new RuntimeException("Notificación no encontrada con el ID: " + id));
    }


    private final WebClient webClient;



    public NotificacionesService(WebClient webClient, NotificacionesRepository repo) {
        this.webClient = webClient;
        this.repo = repo;
    }

    public Mono<NotificacionesDetalleDTO> obtenerAgendaConMedico(Long id) {


        Mono<NotificacionesModel> llamadaNotificaciones =
                Mono.fromCallable(() -> repo.findById(id)
                        .orElseThrow());


        return llamadaNotificaciones.flatMap(notificaciones -> {

            Mono<PacienteDTO> llamadaPaciente = webClient.get()
                    .uri("http://localhost:8081/api/v1/pacientes/{id}",
                            notificaciones.getIdPaciente())
                    .retrieve()
                    .bodyToMono(PacienteDTO.class);


            return Mono.zip(Mono.just(notificaciones), llamadaPaciente)
                    .map(tupla -> {

                        NotificacionesModel ntResult = tupla.getT1();
                        PacienteDTO paciente = tupla.getT2();

                        return new NotificacionesDetalleDTO(
                                ntResult,
                                paciente
                        );
                    });
        });
    }

}
