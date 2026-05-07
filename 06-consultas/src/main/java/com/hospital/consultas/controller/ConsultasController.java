package com.hospital.consultas.controller;

import com.hospital.consultas.model.ConsultasModel;
import com.hospital.consultas.service.ConsultasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasService service;

    @GetMapping
    public List<ConsultasModel> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultasModel crear(@Valid @RequestBody ConsultasModel box) {
        // @Valid asegura que el piso sea >= 1 y el nombre no esté vacío
        return service.guardar(box);
    }

    @GetMapping("/{id}")
    public ConsultasModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }
}