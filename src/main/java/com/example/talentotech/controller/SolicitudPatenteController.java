package com.example.talentotech.controller;

import com.example.talentotech.dto.SolicitudPatenteDTO;
import com.example.talentotech.dto.SolicitudPatenteRespuestaDTO;
import com.example.talentotech.service.SolicitudPatenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudPatenteController {

    @Autowired
    private SolicitudPatenteService solicitudPatenteService;

    // Crear nueva solicitud (usuarios comunes)
    @PostMapping
    public ResponseEntity<SolicitudPatenteRespuestaDTO> crearSolicitud(@Valid @RequestBody SolicitudPatenteDTO solicitudDTO) {
        try {
            SolicitudPatenteRespuestaDTO solicitudCreada = solicitudPatenteService.crearSolicitud(solicitudDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(solicitudCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener todas las solicitudes (admin)
    @GetMapping
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> obtenerTodasLasSolicitudes() {
        List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.obtenerTodasLasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener solicitudes pendientes (admin)
    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> obtenerSolicitudesPendientes() {
        List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.obtenerSolicitudesPendientes();
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener solicitudes por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> obtenerSolicitudesPorEstado(@PathVariable String estado) {
        List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.obtenerSolicitudesPorEstado(estado);
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener solicitudes por solicitante
    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> obtenerSolicitudesPorSolicitante(@PathVariable Integer solicitanteId) {
        try {
            List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.obtenerSolicitudesPorSolicitante(solicitanteId);
            return ResponseEntity.ok(solicitudes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudPatenteRespuestaDTO> obtenerSolicitudPorId(@PathVariable Integer id) {
        try {
            SolicitudPatenteRespuestaDTO solicitud = solicitudPatenteService.obtenerSolicitudPorId(id);
            return ResponseEntity.ok(solicitud);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Aprobar solicitud (admin)
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudPatenteRespuestaDTO> aprobarSolicitud(@PathVariable Integer id) {
        try {
            SolicitudPatenteRespuestaDTO solicitudAprobada = solicitudPatenteService.aprobarSolicitud(id);
            return ResponseEntity.ok(solicitudAprobada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Rechazar solicitud (admin)
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudPatenteRespuestaDTO> rechazarSolicitud(
            @PathVariable Integer id,
            @RequestParam(required = false) String motivo) {
        try {
            SolicitudPatenteRespuestaDTO solicitudRechazada = solicitudPatenteService.rechazarSolicitud(id, motivo);
            return ResponseEntity.ok(solicitudRechazada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Buscar solicitudes por título
    @GetMapping("/buscar")
    public ResponseEntity<List<SolicitudPatenteRespuestaDTO>> buscarSolicitudesPorTitulo(@RequestParam String titulo) {
        List<SolicitudPatenteRespuestaDTO> solicitudes = solicitudPatenteService.buscarSolicitudesPorTitulo(titulo);
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener estadísticas
    @GetMapping("/estadisticas/{estado}")
    public ResponseEntity<Long> contarSolicitudesPorEstado(@PathVariable String estado) {
        long cantidad = solicitudPatenteService.contarSolicitudesPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }
} 