package com.hospital.medico.Service;
import com.hospital.medico.Dto.MedicoDetalleDTO;
import com.hospital.medico.Dto.EspecialidadDTO;
import com.hospital.medico.Repository.MedicoRepository;
import com.hospital.medico.Model.MedicoModel;
import org.springframework.beans.factory.annotation.Autowired;

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

    public List<MedicoModel> findAll(){
        return medicoRepository.findAll();
    }

    public MedicoModel findById(Long idMedico){
        return medicoRepository.findById(idMedico).get();
    }

    public MedicoModel save(MedicoModel medico){
        return medicoRepository.save(medico);
    }

    public void delete(Long id){
        medicoRepository.deleteById(id);
    }


    private final WebClient webClient;


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
                    .uri("http://localhost:8083/api/v1/especialidades/{id}",
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
