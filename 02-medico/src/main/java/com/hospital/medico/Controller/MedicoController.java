package com.hospital.medico.Controller;

import com.hospital.medico.Model.MedicoModel;
import com.hospital.medico.Service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
@RestController
@RequestMapping("api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoModel>> listar(){
        List<MedicoModel> medicos = medicoService.findAll();
        if(medicos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicoModel> guardar(@Valid @RequestBody MedicoModel medico){
        MedicoModel medicos = medicoService.save(medico);
        return ResponseEntity.status( HttpStatus.CREATED).body(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoModel> buscar(@PathVariable Long id){
        try{
            MedicoModel medico = medicoService.findById(id);
            return ResponseEntity.ok(medico);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoModel> actualizar(@PathVariable Long id, @RequestBody MedicoModel medico){
        try{
            MedicoModel medicos = medicoService.findById(id);

            medicos.setIdMedico(medico.getIdMedico());
            medicos.setRutMedico(medicos.getRutMedico());
            medicos.setNombreMedico(medicos.getNombreMedico());
            medicos.setApellidoMedico(medicos.getApellidoMedico());
            medicos.setTelefonoMedico(medicos.getTelefonoMedico());
            medicos.setCorreoMedico(medicos.getCorreoMedico());
            medicos.setAnniosExperiencia(medicos.getAnniosExperiencia());
            medicos.setIdEspecialidad(medicos.getIdEspecialidad());

            medicoService.save(medicos);
            return ResponseEntity.ok(medicos);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            medicoService.delete(id);
            return ResponseEntity.ok().build();

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }




}
