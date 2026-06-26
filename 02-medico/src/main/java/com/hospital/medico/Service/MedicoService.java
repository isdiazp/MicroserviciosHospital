package com.hospital.medico.Service;
import com.hospital.medico.Dto.MedicoDetalleDTO;
import com.hospital.medico.Dto.EspecialidadDTO;
import com.hospital.medico.Repository.MedicoRepository;
import com.hospital.medico.Model.MedicoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import java.util.List;

@Service
@Transactional
public class MedicoService {


    @Autowired
    private MedicoRepository medicoRepository;

    public List<MedicoModel> listarTodo(){
        return medicoRepository.findAll();
    }

    public MedicoModel buscarId(Long idMedico){
        return medicoRepository.findById(idMedico)
                .orElseThrow(()-> new RuntimeException("Medico no encontrado"));
    }

    public MedicoModel guardar(MedicoModel medico){
        return medicoRepository.save(medico);
    }

    public MedicoModel actualizar(Long idMedico, MedicoModel medico){

        MedicoModel existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        existente.setRutMedico(medico.getRutMedico());
        existente.setNombreMedico(medico.getNombreMedico());
        existente.setApellidoMedico(medico.getApellidoMedico());
        existente.setTelefonoMedico(medico.getTelefonoMedico());
        existente.setCorreoMedico(medico.getCorreoMedico());
        existente.setAnniosExperiencia(medico.getAnniosExperiencia());
        existente.setIdEspecialidad(medico.getIdEspecialidad());

        return medicoRepository.save(existente);
    }



    public void delete(Long id){
        medicoRepository.deleteById(id);
    }


    private final WebClient webClient;

    @Value("${servicio.especialidades.url}")
    private String especialidadesUrl;

    public MedicoService(WebClient webClient, MedicoRepository medicoRepository) {
        this.webClient = webClient;
        this.medicoRepository = medicoRepository;
    }

    public Mono<MedicoDetalleDTO> obtenerMedicoConEspecialidad(Long idMedico) {

        // 1.Obtener médico (envolvemos en Mono porque JPA es bloqueante)
        Mono<MedicoModel> llamadaMedico =
                Mono.fromCallable(() -> medicoRepository.findById(idMedico)
                        .orElseThrow());

        // 2.Cuando tenemos el médico, llamamos al microservicio especialidades
        return llamadaMedico.flatMap(medico -> {

            Mono<EspecialidadDTO> llamadaEspecialidad = webClient.get()
                    .uri(especialidadesUrl + "/api/v1/especialidades/{id}",
                            medico.getIdEspecialidad())
                    .retrieve()
                    .bodyToMono(EspecialidadDTO.class);

            // 3️.Se ejecuta en paralelo (aunque aquí solo hay una llamada externa)
            return Mono.zip(Mono.just(medico), llamadaEspecialidad)
                    .map(tupla -> {

                        MedicoModel medicoResult = tupla.getT1();
                        EspecialidadDTO especialidad = tupla.getT2();

                        return new MedicoDetalleDTO(
                                medicoResult,
                                especialidad
                        );
                    });
        });
    }


}
