package com.hospital.agenda.Controller;

import com.hospital.agenda.Dto.AgendaDetalleDTO;
import com.hospital.agenda.Model.AgendaModel;
import com.hospital.agenda.Service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;
@RestController
@RequestMapping("api/v1/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<AgendaModel>> listar(){
        List<AgendaModel> agenda =  agendaService.findAll();
        if(agenda.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(agenda,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AgendaModel> guardar(@Valid @RequestBody AgendaModel agendaModel){
        AgendaModel agenda = agendaService.save(agendaModel);
        return ResponseEntity.status(HttpStatus.OK).body(agenda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaModel> buscarPorId(@PathVariable Long id){
        try{
            AgendaModel agenda = agendaService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(agenda);

        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaModel> actualizar(@PathVariable Long id, @RequestBody AgendaModel agendaModel){
        try{
            AgendaModel agenda = agendaService.findById(id);

            agenda.setIdAgenda(id);
            agenda.setIdMedico(agendaModel.getIdMedico());
            agenda.setFecha(agendaModel.getFecha());
            agenda.setHoraInicio(agendaModel.getHoraInicio());
            agenda.setHoraFin(agendaModel.getHoraFin());
            agenda.setDuracionMinutos(agendaModel.getDuracionMinutos());
            agenda.setCuposDisponibles(agendaModel.getCuposDisponibles());
            agenda.setEstado(agendaModel.getEstado());
            agenda.setActivo(agendaModel.isActivo());

            agendaService.save(agenda);
            return ResponseEntity.status(HttpStatus.OK).body(agenda);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  eliminar(@PathVariable Long id){
        try{
            agendaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }


    // traer los datos de medico con el id
    @GetMapping("/detalle/{id}")
    public Mono<ResponseEntity<AgendaDetalleDTO>> obtenerDetalle(@PathVariable Long id){
        return agendaService.obtenerAgendaConMedico(id)
                .map(detalle -> ResponseEntity.status(HttpStatus.OK).body(detalle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
