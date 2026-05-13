package com.hospital.reservas.Controller;

import com.hospital.reservas.Model.ReservasModel;
import com.hospital.reservas.Service.ReservasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;

    @GetMapping
    public ResponseEntity<List<ReservasModel>> listar(){
        List<ReservasModel> reservas = reservasService.findAll();
        if(reservas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservasModel> guardar(@Valid @RequestBody ReservasModel reserva){
            ReservasModel reservas = reservasService.save(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservas);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservasModel> buscar(@PathVariable Long id){
        try {
            ReservasModel reserva = reservasService.findById(id);
            return ResponseEntity.ok(reserva);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservasModel> actualizar(@PathVariable Long id, @RequestBody ReservasModel reserva){
        try{
            ReservasModel reservas = reservasService.findById(id);

            reservas.setIdReservas(reserva.getIdReservas());
            reservas.setIdPaciente(reserva.getIdPaciente());
            reservas.setIdMedico(reserva.getIdMedico());
            reservas.setIdAgenda(reserva.getIdAgenda());
            reservas.setFechaAtencion(reserva.getFechaAtencion());
            reservas.setHoraAtencion(reserva.getHoraAtencion());
            reservas.setMotivoConsulta(reserva.getMotivoConsulta());
            reservas.setEstado(reserva.getEstado());
            reservas.setObservacion(reserva.getObservacion());

            reservasService.save(reservas);
            return ResponseEntity.ok(reserva);

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            reservasService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
