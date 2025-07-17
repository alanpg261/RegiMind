package com.example.talentotech.controller;

import com.example.talentotech.dto.UsuarioRespuestaDTO;
import com.example.talentotech.dto.PatenteRespuestaDTO;
import com.example.talentotech.dto.PaginaRespuestaDTO;
import com.example.talentotech.dto.SolicitudPatenteRespuestaDTO;
import com.example.talentotech.service.UsuarioService;
import com.example.talentotech.service.PatenteService;
import com.example.talentotech.service.SolicitudPatenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PatenteService patenteService;
    
    @Autowired
    private SolicitudPatenteService solicitudPatenteService;

    // Obtener estadísticas del dashboard
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Estadísticas de usuarios
        long totalUsuarios = usuarioService.contarTotalUsuarios();
        long usuariosHoy = usuarioService.contarUsuariosRegistradosHoy();
        
        // Estadísticas de patentes
        long totalPatentes = patenteService.contarTotalPatentes();
        long patentesRecientes = patenteService.contarPatentesRecientes();
        
        // Estadísticas de actividad (simuladas por ahora)
        long busquedasHoy = 156; // Esto se puede implementar con un sistema de logs
        
        // Solicitudes pendientes reales
        long solicitudesPendientes = solicitudPatenteService.contarSolicitudesPorEstado("PENDIENTE");
        
        stats.put("totalUsuarios", totalUsuarios);
        stats.put("usuariosHoy", usuariosHoy);
        stats.put("totalPatentes", totalPatentes);
        stats.put("patentesRecientes", patentesRecientes);
        stats.put("busquedasHoy", busquedasHoy);
        stats.put("solicitudesPendientes", solicitudesPendientes);
        
        return ResponseEntity.ok(stats);
    }

    // Obtener todos los usuarios con paginación
    @GetMapping("/usuarios")
    public ResponseEntity<PaginaRespuestaDTO<UsuarioRespuestaDTO>> getUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginaRespuestaDTO<UsuarioRespuestaDTO> usuarios = usuarioService.obtenerUsuariosPaginados(page, size);
        return ResponseEntity.ok(usuarios);
    }

    // Obtener todas las patentes con paginación
    @GetMapping("/patentes")
    public ResponseEntity<PaginaRespuestaDTO<PatenteRespuestaDTO>> getPatentes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginaRespuestaDTO<PatenteRespuestaDTO> patentes = patenteService.obtenerPatentesPaginadas(page, size);
        return ResponseEntity.ok(patentes);
    }

    // Obtener actividad reciente
    @GetMapping("/actividad-reciente")
    public ResponseEntity<List<Map<String, Object>>> getActividadReciente() {
        List<Map<String, Object>> actividad = usuarioService.obtenerActividadReciente();
        return ResponseEntity.ok(actividad);
    }

    // Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Cambiar rol de usuario
    @PutMapping("/usuarios/{id}/rol")
    public ResponseEntity<String> cambiarRolUsuario(
            @PathVariable Integer id,
            @RequestParam String nuevoRol) {
        try {
            usuarioService.cambiarRolUsuario(id, nuevoRol);
            return ResponseEntity.ok("Rol de usuario actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cambiar rol: " + e.getMessage());
        }
    }

    // Obtener solicitudes pendientes
    @GetMapping("/solicitudes-pendientes")
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> getSolicitudesPendientes() {
        List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.obtenerSolicitudesPendientes();
        return ResponseEntity.ok(solicitudes);
    }

    // Aprobar solicitud
    @PutMapping("/solicitudes/{id}/aprobar")
    public ResponseEntity<String> aprobarSolicitud(@PathVariable Integer id) {
        try {
            solicitudPatenteService.aprobarSolicitud(id);
            return ResponseEntity.ok("Solicitud aprobada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al aprobar solicitud: " + e.getMessage());
        }
    }

    // Rechazar solicitud
    @PutMapping("/solicitudes/{id}/rechazar")
    public ResponseEntity<String> rechazarSolicitud(@PathVariable Integer id) {
        try {
            solicitudPatenteService.rechazarSolicitud(id, "Rechazado por el administrador");
            return ResponseEntity.ok("Solicitud rechazada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al rechazar solicitud: " + e.getMessage());
        }
    }
} 