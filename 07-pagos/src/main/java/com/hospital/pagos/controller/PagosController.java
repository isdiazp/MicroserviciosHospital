package com.hospital.pagos.controller;

import com.hospital.pagos.Dto.PagosDetalleDTO;
import com.hospital.pagos.model.Pagos;
import com.hospital.pagos.service.PagosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve 204 al borrar
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagos> actualizar(@PathVariable Long id, @Valid @RequestBody Pagos datosActualizados) {
        try {
            Pagos pagoModificado = service.actualizar(id, datosActualizados);
            return ResponseEntity.ok(pagoModificado); // Devuelve el pago ya actualizado con estado 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no existía el ID, devuelve un 404 Not Found
        }
    }


    @GetMapping("/detalle/{id}")
    public Mono<ResponseEntity<PagosDetalleDTO>>  obtenerDetalle(@PathVariable Long id) {
        return service.obtenerPagosConPaciente(id)
                .map(detalle -> ResponseEntity.status(HttpStatus.OK).body(detalle))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}