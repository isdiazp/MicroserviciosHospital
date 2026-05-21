package com.hospital.recetas.service;
import com.hospital.recetas.Dto.MedicoDTO;
import com.hospital.recetas.Dto.PacienteDTO;
import com.hospital.recetas.Dto.RecetasDetalleDTO;
import com.hospital.recetas.Dto.ReservaDTO;
import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.repository.RecetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RecetasService {

    @Autowired
    private RecetasRepository repo;

    public List<RecetasModel> listarTodas() {
        return repo.findAll();
    }

    public RecetasModel guardar(RecetasModel receta) {
        return repo.save(receta);
    }

    public RecetasModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public RecetasModel actualizar(Long id, RecetasModel datosActualizados) {
        // 1. Buscamos si la receta existe por su ID
        return repo.findById(id).map(recetaExistente -> {
            // 2. Mapeamos uno a uno todos los campos de tu modelo
            recetaExistente.setIdPaciente(datosActualizados.getIdPaciente());
            recetaExistente.setIdMedico(datosActualizados.getIdMedico());
            recetaExistente.setIdReserva(datosActualizados.getIdReserva());
            recetaExistente.setFechaEmision(datosActualizados.getFechaEmision());
            recetaExistente.setDiagnostico(datosActualizados.getDiagnostico());
            recetaExistente.setMedicamentos(datosActualizados.getMedicamentos());
            recetaExistente.setIndicaciones(datosActualizados.getIndicaciones());
            recetaExistente.setVigenciaDias(datosActualizados.getVigenciaDias());

            // 3. Guardamos los cambios
            return repo.save(recetaExistente);
        }).orElseThrow(() -> new RuntimeException("Receta no encontrada con el ID: " + id));
    }



    private final WebClient webClient;

    public RecetasService(WebClient webClient, RecetasRepository repo) {
        this.webClient = webClient;
        this.repo = repo;
    }

    public Mono<RecetasDetalleDTO> obtenerDetalleRecetas(Long id) {


        Mono<RecetasModel> llamadaRecetas =
                Mono.fromCallable(() -> repo.findById(id)
                        .orElseThrow());


        return llamadaRecetas.flatMap(recetas -> {

            Mono<PacienteDTO> llamadaPaciente = webClient.get()
                    .uri("http://localhost:8081/api/v1/pacientes/{id}",
                            recetas.getIdPaciente())
                    .retrieve()
                    .bodyToMono(PacienteDTO.class);

            Mono<MedicoDTO> llamadaMedico = webClient.get()
                    .uri("http://localhost:8082/api/v1/medicos/{id}",
                            recetas.getIdMedico())
                    .retrieve()
                    .bodyToMono(MedicoDTO.class);

            Mono<ReservaDTO> llamadaReserva = webClient.get()
                    .uri("http://localhost:8085/api/v1/reservas/{id}",
                            recetas.getIdReserva())
                    .retrieve()
                    .bodyToMono(ReservaDTO.class);


            return Mono.zip(Mono.just(recetas), llamadaPaciente,llamadaMedico,llamadaReserva)
                    .map(tupla -> {

                        RecetasModel recetasResult = tupla.getT1();
                        PacienteDTO paciente = tupla.getT2();
                        MedicoDTO medico = tupla.getT3();
                        ReservaDTO reserva = tupla.getT4();

                        return new RecetasDetalleDTO(
                                recetasResult,
                                paciente,
                                medico,
                                reserva
                        );
                    });
        });
    }
}