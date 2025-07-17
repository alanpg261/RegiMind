package com.example.talentotech.repository;

import com.example.talentotech.entity.PatenteSolicitante;
import com.example.talentotech.entity.Patente;
import com.example.talentotech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatenteSolicitanteRepository extends JpaRepository<PatenteSolicitante, Integer> {
    List<PatenteSolicitante> findByPatente(Patente patente);
    List<PatenteSolicitante> findBySolicitante(Usuario solicitante);
    List<PatenteSolicitante> findByPatenteId(Integer patenteId);
    List<PatenteSolicitante> findBySolicitanteId(Integer solicitanteId);
} 