package com.hospital.historialClinico.controller;

import com.hospital.historialClinico.model.HCModel;
import com.hospital.historialClinico.service.HCService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}