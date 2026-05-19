package com.hospital.pagos.service;

import com.hospital.pagos.model.Pagos;
import com.hospital.pagos.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;

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

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public Pagos actualizar(Long id, Pagos datosActualizados) {
        // 1. Buscamos si el pago existe en la base de datos
        return repo.findById(id).map(pagoExistente -> {
            // 2. Si existe, actualizamos cada uno de sus campos con los nuevos datos
            pagoExistente.setRutPaciente(datosActualizados.getRutPaciente());
            pagoExistente.setMonto(datosActualizados.getMonto());
            pagoExistente.setFechaPago(datosActualizados.getFechaPago());
            pagoExistente.setMedioPago(datosActualizados.getMedioPago());
            pagoExistente.setEstadoPago(datosActualizados.getEstadoPago());
            pagoExistente.setActivo(datosActualizados.getActivo());

            // 3. Guardamos los cambios en la base de datos
            return repo.save(pagoExistente);
        }).orElseThrow(() -> new RuntimeException("Pago no encontrado con el ID: " + id));
    }

}
