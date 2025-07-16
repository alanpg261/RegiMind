package com.example.talentotech.repository;

import com.example.talentotech.entity.PatenteSolicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatenteSolicitanteRepository extends JpaRepository<PatenteSolicitante, Integer> {
    List<PatenteSolicitante> findByPatenteId(Integer patenteId);
} 