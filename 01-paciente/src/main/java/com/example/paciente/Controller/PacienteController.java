package com.example.paciente.Controller;

import com.example.paciente.Model.Paciente;
import com.example.paciente.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar(){
        List<Paciente> pacientes = pacienteService.findAll();
        if(pacientes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente){
        Paciente pacientes = pacienteService.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacientes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id){
        try{
            Paciente paciente =  pacienteService.findById(id);
            return ResponseEntity.ok(paciente);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        try{
            Paciente pacientes = pacienteService.findById(id);
            pacientes.setIdPaciente(id);
            pacientes.setRutPaciente(paciente.getRutPaciente());
            pacientes.setNombrePaciente(paciente.getNombrePaciente());
            pacientes.setApellidoPaciente(paciente.getApellidoPaciente());
            pacientes.setFechaNacimiento(paciente.getFechaNacimiento());
            pacientes.setSexoPaciente(paciente.getSexoPaciente());
            pacientes.setCorreoPaciente(paciente.getCorreoPaciente());
            pacientes.setTelefonoPaciente(paciente.getTelefonoPaciente());
            pacientes.setDireccionPaciente(paciente.getDireccionPaciente());

            pacienteService.save(pacientes);
            return  ResponseEntity.ok(pacientes);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            pacienteService.delete(id);
            return  ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }


}
