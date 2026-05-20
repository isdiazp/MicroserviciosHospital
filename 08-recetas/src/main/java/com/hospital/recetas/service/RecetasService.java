package com.hospital.recetas.service;
import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.repository.RecetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class RecetasService {

    @Autowired
    private RecetasRepository repo;

    public List<RecetasModel> listarTodas() {
        return repo.findAll();
    }

    public RecetasModel guardar(RecetasModel receta) {
        return repo.save(receta);
    }

    public RecetasModel buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public RecetasModel actualizar(Long id, RecetasModel datosActualizados) {
        // 1. Buscamos si la receta existe por su ID
        return repo.findById(id).map(recetaExistente -> {
            // 2. Mapeamos uno a uno todos los campos de tu modelo
            recetaExistente.setIdPaciente(datosActualizados.getIdPaciente());
            recetaExistente.setIdMedico(datosActualizados.getIdMedico());
            recetaExistente.setIdReserva(datosActualizados.getIdReserva());
            recetaExistente.setFechaEmision(datosActualizados.getFechaEmision());
            recetaExistente.setDiagnostico(datosActualizados.getDiagnostico());
            recetaExistente.setMedicamentos(datosActualizados.getMedicamentos());
            recetaExistente.setIndicaciones(datosActualizados.getIndicaciones());
            recetaExistente.setVigenciaDias(datosActualizados.getVigenciaDias());

            // 3. Guardamos los cambios
            return repo.save(recetaExistente);
        }).orElseThrow(() -> new RuntimeException("Receta no encontrada con el ID: " + id));
    }
}