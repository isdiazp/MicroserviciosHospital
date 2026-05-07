package com.hospital.recetas.controller;

import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.service.RecetasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}