package com.example.talentotech.repository;

import com.example.talentotech.entity.SolicitudPatente;
import com.example.talentotech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SolicitudPatenteRepository extends JpaRepository<SolicitudPatente, Integer> {
    
    // Buscar solicitudes por estado
    List<SolicitudPatente> findByEstado(String estado);
    
    // Buscar solicitudes por solicitante
    List<SolicitudPatente> findBySolicitante(Usuario solicitante);
    
    // Buscar solicitudes por inventor
    List<SolicitudPatente> findByInventor(Usuario inventor);
    
    // Buscar solicitudes por apoderado
    List<SolicitudPatente> findByApoderado(Usuario apoderado);
    
    // Buscar solicitudes pendientes
    List<SolicitudPatente> findByEstadoOrderByFechaDesc(String estado);
    
    // Buscar por expediente
    SolicitudPatente findByExpediente(String expediente);
    
    // Buscar por título (búsqueda parcial)
    @Query("SELECT s FROM SolicitudPatente s WHERE s.titulo LIKE %:titulo%")
    List<SolicitudPatente> findByTituloContaining(@Param("titulo") String titulo);
    
    // Contar solicitudes por estado
    long countByEstado(String estado);
    
    // Buscar solicitudes recientes (últimos 30 días)
    @Query("SELECT s FROM SolicitudPatente s WHERE s.fecha >= :fechaInicio ORDER BY s.fecha DESC")
    List<SolicitudPatente> findSolicitudesRecientes(@Param("fechaInicio") java.util.Date fechaInicio);
} 