package com.hospital.pagos.controller;

import com.hospital.pagos.model.Pagos;
import com.hospital.pagos.service.PagosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagosController {

    @Autowired
    private PagosService service;

    @GetMapping("/listar")
    public List<Pagos> listar() {
        return service.listar();
    }

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED) // Devuelve 201 al crear
    public void guardar(@Valid @RequestBody Pagos pago) {
        // El @Valid revisa que el monto sea positivo, el RUT no esté vacío, etc.
        service.guardar(pago);
    }

    @PutMapping("/actualizar")
    public void actualizar(@Valid @RequestBody Pagos pago) {
        service.actualizar(pago);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve 204 al borrar
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}