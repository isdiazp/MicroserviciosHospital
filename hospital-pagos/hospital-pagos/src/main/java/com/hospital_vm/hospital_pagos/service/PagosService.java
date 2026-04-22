package com.hospital_vm.hospital_pagos.service;

import com.hospital_vm.hospital_pagos.model.Pagos;
import com.hospital_vm.hospital_pagos.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PagosService {
    @Autowired
    private  PagosRepository repo;

    public List<Pagos> listar(){
        return repo.findAll();
    }

    public void guardar(Pagos pago){
        if (pago.getFechaPago()== null){
            pago.setFechaPago(java.time.LocalDateTime.now());
        }
        repo.save(pago);
    }

    public void actualizar(Pagos pago){
        if (repo.existsById(pago.getId())){
            repo.save(pago);
        }
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
