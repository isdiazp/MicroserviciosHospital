package com.hospital.especialidades.Controller;

import com.hospital.especialidades.Model.EspecialidadesModel;
import com.hospital.especialidades.Service.EspecialidadesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/v1/especialidades")
public class EspecialidadesController {

    @Autowired
    private EspecialidadesService especialidadesService;



    @GetMapping
    public ResponseEntity<List<EspecialidadesModel>> listar(){
        List<EspecialidadesModel> especialidad =  especialidadesService.findAll();
        if(especialidad.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EspecialidadesModel> guardar(@Valid @RequestBody EspecialidadesModel especialidadesModel){
        EspecialidadesModel  especialidad = especialidadesService.save(especialidadesModel);
        return ResponseEntity.status(HttpStatus.OK).body(especialidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadesModel> buscar(@PathVariable Long id){
        try{
            EspecialidadesModel especialidad = especialidadesService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(especialidad);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadesModel> actualizar(@PathVariable Long id, @RequestBody EspecialidadesModel especialidadesModel){
        try{
            EspecialidadesModel especialidad = especialidadesService.findById(id);

            especialidad.setIdEspecialidad(especialidadesModel.getIdEspecialidad());
            especialidad.setNombreEspecialidad(especialidadesModel.getNombreEspecialidad());
            especialidad.setDescripcionEspecialidad(especialidadesModel.getDescripcionEspecialidad());
            especialidad.setAreaMedica(especialidadesModel.getAreaMedica());

            especialidadesService.save(especialidad);
            return  ResponseEntity.status(HttpStatus.OK).body(especialidad);

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            especialidadesService.delete(id);
            return  ResponseEntity.status(HttpStatus.OK).build();

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }




}
