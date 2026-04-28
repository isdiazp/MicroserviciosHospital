package com.hospital.consultas.controller;

import com.hospital.consultas.model.ConsultasModel;
import com.hospital.consultas.service.ConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultas") // [cite: 59]
public class ConsultasController {

    @Autowired
    private ConsultasService service;

    @GetMapping
    public List<ConsultasModel> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ConsultasModel crear(@RequestBody ConsultasModel box) {
        return service.guardar(box);
    }

    @GetMapping("/{id}")
    public ConsultasModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }
}