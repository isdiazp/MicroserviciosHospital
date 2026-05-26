package com.hospital.recetas.repository;
import com.hospital.recetas.model.RecetasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetasRepository extends JpaRepository<RecetasModel, Long> {

    List<RecetasModel> findByIdMedico(Long idMedico);

}
