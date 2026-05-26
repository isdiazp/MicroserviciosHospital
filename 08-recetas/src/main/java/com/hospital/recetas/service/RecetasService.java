package com.hospital.recetas.service;
import com.hospital.recetas.Dto.MedicoDTO;
import com.hospital.recetas.Dto.PacienteDTO;
import com.hospital.recetas.Dto.RecetaConMedicoDTO;
import com.hospital.recetas.Dto.RecetasDetalleDTO;
import com.hospital.recetas.Dto.ReservaDTO;
import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.repository.RecetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecetasService {

    @Autowired
    private RecetasRepository repo;

    public List<RecetasModel> listarTodas() {
        return repo.findAll();
    }

    public List<RecetaConMedicoDTO> listarTodasConMedico() {
        List<RecetasModel> recetas = repo.findAll();
        List<RecetaConMedicoDTO> recetasConMedico = new java.util.ArrayList<>();

        for (RecetasModel receta : recetas) {
            try {
                MedicoDTO medico = webClient.get()
                        .uri(medicoUrl + "/api/v1/medicos/{id}", receta.getIdMedico())
                        .retrieve()
                        .bodyToMono(MedicoDTO.class)
                        .block();

                RecetaConMedicoDTO dto = new RecetaConMedicoDTO(
                        receta.getId(),
                        receta.getIdPaciente(),
                        receta.getIdMedico(),
                        medico != null ? medico.getNombreMedico() : null,
                        medico != null ? medico.getApellidoMedico() : null,
                        receta.getIdReserva(),
                        receta.getFechaEmision(),
                        receta.getDiagnostico(),
                        receta.getMedicamentos(),
                        receta.getIndicaciones(),
                        receta.getVigenciaDias()
                );
                recetasConMedico.add(dto);
            } catch (Exception e) {
                // Si falla la llamada al microservicio de médicos, agregar con nombre null
                RecetaConMedicoDTO dto = new RecetaConMedicoDTO(
                        receta.getId(),
                        receta.getIdPaciente(),
                        receta.getIdMedico(),
                        null,
                        null,
                        receta.getIdReserva(),
                        receta.getFechaEmision(),
                        receta.getDiagnostico(),
                        receta.getMedicamentos(),
                        receta.getIndicaciones(),
                        receta.getVigenciaDias()
                );
                recetasConMedico.add(dto);
            }
        }
        return recetasConMedico;
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

    @Value("${servicio.paciente.url}")
    private String pacienteUrl;

    @Value("${servicio.medico.url}")
    private String medicoUrl;

    @Value("${servicio.reservas.url}")
    private String reservasUrl;

    public RecetasService(WebClient webClient, RecetasRepository repo) {
        this.webClient = webClient;
        this.repo = repo;
    }

    public List<RecetasDetalleDTO> obtenerDetalleRecetasPorMedico(Long idMedico) {
        List<RecetasModel> recetas = repo.findByIdMedico(idMedico);
        List<RecetasDetalleDTO> resultado = new ArrayList<>();

        for (RecetasModel receta : recetas) {

            PacienteDTO paciente = webClient.get()
                    .uri(pacienteUrl + "/api/v1/pacientes/{id}", receta.getIdPaciente())
                    .retrieve()
                    .bodyToMono(PacienteDTO.class)
                    .block();

            MedicoDTO medico = webClient.get()
                    .uri(medicoUrl + "/api/v1/medicos/{id}", receta.getIdMedico())
                    .retrieve()
                    .bodyToMono(MedicoDTO.class)
                    .block();

            ReservaDTO reserva = webClient.get()
                    .uri(reservasUrl + "/api/v1/reservas/{id}", receta.getIdReserva())
                    .retrieve()
                    .bodyToMono(ReservaDTO.class)
                    .block();

            RecetasDetalleDTO dto = new RecetasDetalleDTO(
                    receta,
                    paciente,
                    medico,
                    reserva
            );

            resultado.add(dto);
        }

        return resultado;

    }
}