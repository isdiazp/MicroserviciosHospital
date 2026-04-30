package com.hospital.historialClinico.repository;

import com.hospital.historialClinico.model.HCModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HCRepository extends JpaRepository<HCModel, Long> {
}
