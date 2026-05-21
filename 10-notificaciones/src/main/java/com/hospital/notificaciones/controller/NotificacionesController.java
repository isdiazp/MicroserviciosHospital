package com.hospital.notificaciones.controller;

import com.hospital.notificaciones.Dto.NotificacionesDetalleDTO;
import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.service.NotificacionesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionesService service;

    @GetMapping
    public List<NotificacionesModel> listar() {
        return service.listarTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificacionesModel crear(@Valid @RequestBody NotificacionesModel notificacion) {
        // @Valid revisará que el mensaje y el pacienteId sean correctos
        return service.guardar(notificacion);
    }

    @GetMapping("/{id}")
    public NotificacionesModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionesModel> actualizar(@PathVariable Long id, @Valid @RequestBody NotificacionesModel datosActualizados) {
        try {
            NotificacionesModel notificacionModificada = service.actualizar(id, datosActualizados);
            return ResponseEntity.ok(notificacionModificada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/detalle/{id}")
    public Mono<ResponseEntity<NotificacionesDetalleDTO>> detalle(@PathVariable Long id) {
        return service.obtenerAgendaConMedico(id)
                .map(detalle -> ResponseEntity.status(HttpStatus.OK).body(detalle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}