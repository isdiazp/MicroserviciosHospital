package com.hospital.consultas.repository;

import com.hospital.consultas.model.ConsultasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultasRepository extends JpaRepository<ConsultasModel, Long> {

}
