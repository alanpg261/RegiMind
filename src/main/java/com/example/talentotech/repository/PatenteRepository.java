package com.example.talentotech.repository;

import com.example.talentotech.entity.Patente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PatenteRepository extends JpaRepository<Patente, Integer> {
    
    // Buscar por expediente
    Optional<Patente> findByExpediente(String expediente);
    
    // Buscar por tipo de patente
    List<Patente> findByTipoPatente(String tipoPatente);
    
    // Buscar por estado
    List<Patente> findByEstado(String estado);
    
    // Buscar por inventor
    List<Patente> findByInventor(String inventor);
    
    // Buscar por apoderado
    List<Patente> findByApoderado(String apoderado);
    
    // Buscar por título (búsqueda parcial)
    @Query("SELECT p FROM Patente p WHERE p.titulo LIKE %:titulo%")
    List<Patente> findByTituloContaining(@Param("titulo") String titulo);
    
    // Buscar por CIP
    @Query("SELECT p FROM Patente p WHERE p.cip LIKE %:cip%")
    List<Patente> findByCipContaining(@Param("cip") String cip);
    
    // Contar por estado
    long countByEstado(String estado);
    
    // Buscar patentes recientes
    @Query("SELECT p FROM Patente p WHERE p.fecha >= :fechaInicio ORDER BY p.fecha DESC")
    List<Patente> findPatentesRecientes(@Param("fechaInicio") java.util.Date fechaInicio);
} 