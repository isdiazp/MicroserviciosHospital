package com.hospital_vm.hospital_pagos.repository;

import com.hospital_vm.hospital_pagos.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long>{
}
