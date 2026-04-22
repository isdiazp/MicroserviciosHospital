package com.hospital_vm.hospital_pagos.controller;

import com.hospital_vm.hospital_pagos.model.Pagos;
import com.hospital_vm.hospital_pagos.service.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public class PagosController {

    @RestController
    @RequestMapping("/pago")
    public class PagoController {

        @Autowired
        private PagosService service;

        @GetMapping("/listar")
        public List<Pagos> listar() {
            return service.listar();
        }

        @PostMapping("/guardar")
        public void guardar(@RequestBody Pagos pago) {
            service.guardar(pago);
        }

        @PutMapping("/actualizar")
        public void actualizar(@RequestBody Pagos pago) {
            service.actualizar(pago);
        }

        @DeleteMapping("/eliminar/{id}")
        public void eliminar(@PathVariable Long id) {
            service.eliminar(id);
        }
    }
}
