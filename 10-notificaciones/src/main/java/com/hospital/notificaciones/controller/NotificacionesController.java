package com.hospital.notificaciones.controller;

import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public NotificacionesModel crear(@RequestBody NotificacionesModel notificacion) {
        return service.guardar(notificacion);
    }

    @GetMapping("/{id}")
    public NotificacionesModel obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }

}
