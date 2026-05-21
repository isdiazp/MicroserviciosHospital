package com.hospital.historialclinico.controller;

import com.hospital.historialclinico.Dto.HCDetalleDTO;
import com.hospital.historialclinico.model.HCModel;
import com.hospital.historialclinico.service.HCService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
public class HCController {

    @Autowired
    private HCService service;

    @GetMapping
    public List<HCModel> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HCModel crear(@Valid @RequestBody HCModel historial) {
        return service.guardar(historial);
    }

    @GetMapping("/{id}")
    public HCModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HCModel> actualizar(@PathVariable Long id, @Valid @RequestBody HCModel datosActualizados) {
        try {
            HCModel historialModificado = service.actualizar(id, datosActualizados);
            return ResponseEntity.ok(historialModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/detalle/{id}")
    public Mono<ResponseEntity<HCDetalleDTO>> detalleClinico(@PathVariable Long id) {
        return service.obtenerHistorialMedicoGeneral(id)
                .map(detalle -> ResponseEntity.status(HttpStatus.OK).body(detalle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}