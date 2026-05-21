package com.hospital.recetas.controller;

import com.hospital.recetas.Dto.RecetasDetalleDTO;
import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.service.RecetasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetasController {

    @Autowired
    private RecetasService service;

    @GetMapping
    public List<RecetasModel> listar() {
        return service.listarTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // devolver 201 al crear
    public RecetasModel crear(@Valid @RequestBody RecetasModel receta) {
        // El @Valid de arriba obliga a Spring a revisar las anotaciones del Model
        return service.guardar(receta);
    }

    @GetMapping("/{id}")
    public RecetasModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // devolver 204 al borrar
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetasModel> actualizar(@PathVariable Long id, @Valid @RequestBody RecetasModel datosActualizados) {
        try {
            RecetasModel recetaModificada = service.actualizar(id, datosActualizados);
            return ResponseEntity.ok(recetaModificada); // 200 OK con los datos nuevos
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found si el ID no existía
        }
    }


    @GetMapping("/detalle/{id}")
    public Mono<ResponseEntity<RecetasDetalleDTO>> detalle(@PathVariable Long id) {
        return service.obtenerDetalleRecetas(id)
                .map(detalle -> ResponseEntity.status(HttpStatus.OK).body(detalle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}