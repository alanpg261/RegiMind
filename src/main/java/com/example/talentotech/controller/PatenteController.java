package com.example.talentotech.controller;

import com.example.talentotech.dto.PatenteBusquedaDTO;
import com.example.talentotech.dto.PatenteBusquedaAvanzadaDTO;
import com.example.talentotech.dto.PatenteRespuestaDTO;
import com.example.talentotech.dto.PaginaRespuestaDTO;
import com.example.talentotech.service.PatenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@RestController
@RequestMapping("/api/patentes")
@CrossOrigin(origins = "*")
public class PatenteController {
    @Autowired
    private PatenteService patenteService;

    // Búsqueda avanzada con filtros
    @PostMapping("/buscar")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPatentes(@Valid @RequestBody PatenteBusquedaDTO filtros) {
        List<PatenteRespuestaDTO> resultados = patenteService.buscarPatentes(filtros);
        return ResponseEntity.ok(resultados);
    }

    // Búsqueda avanzada con paginación
    @PostMapping("/buscar-avanzado")
    public ResponseEntity<PaginaRespuestaDTO<PatenteRespuestaDTO>> buscarPatentesAvanzado(@Valid @RequestBody PatenteBusquedaAvanzadaDTO filtros) {
        PaginaRespuestaDTO<PatenteRespuestaDTO> resultados = patenteService.buscarPatentesAvanzado(filtros);
        return ResponseEntity.ok(resultados);
    }

    // Búsqueda avanzada con parámetros GET (para el frontend)
    @GetMapping("/buscar-avanzada")
    public ResponseEntity<PaginaRespuestaDTO<PatenteRespuestaDTO>> buscarPatentesAvanzada(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String expediente,
            @RequestParam(required = false) String inventor,
            @RequestParam(required = false) String tipoPatente,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cip,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        PatenteBusquedaAvanzadaDTO filtros = new PatenteBusquedaAvanzadaDTO();
        filtros.setTitulo(titulo);
        filtros.setExpediente(expediente);
        filtros.setInventor(inventor);
        filtros.setTipoPatente(tipoPatente);
        filtros.setEstado(estado);
        filtros.setCip(cip);
        filtros.setPagina(page);
        filtros.setTamanio(size);
        
        PaginaRespuestaDTO<PatenteRespuestaDTO> resultados = patenteService.buscarPatentesAvanzado(filtros);
        return ResponseEntity.ok(resultados);
    }

    // Obtener todas las patentes
    @GetMapping
    public ResponseEntity<List<PatenteRespuestaDTO>> obtenerTodasLasPatentes() {
        List<PatenteRespuestaDTO> patentes = patenteService.obtenerTodasLasPatentes();
        return ResponseEntity.ok(patentes);
    }

    // Obtener patente por ID
    @GetMapping("/{id}")
    public ResponseEntity<PatenteRespuestaDTO> obtenerPatentePorId(@PathVariable Integer id) {
        Optional<PatenteRespuestaDTO> patente = patenteService.obtenerPatentePorId(id);
        return patente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por expediente
    @GetMapping("/expediente/{expediente}")
    public ResponseEntity<PatenteRespuestaDTO> buscarPorExpediente(@PathVariable String expediente) {
        Optional<PatenteRespuestaDTO> patente = patenteService.buscarPorExpediente(expediente);
        return patente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por título
    @GetMapping("/titulo")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(patentes);
    }

    // Buscar por tipo de patente
    @GetMapping("/tipo/{tipoPatente}")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorTipoPatente(@PathVariable String tipoPatente) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorTipoPatente(tipoPatente);
        return ResponseEntity.ok(patentes);
    }

    // Buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorEstado(@PathVariable String estado) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorEstado(estado);
        return ResponseEntity.ok(patentes);
    }

    // Buscar por inventor
    @GetMapping("/inventor")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorInventor(@RequestParam String inventor) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorInventor(inventor);
        return ResponseEntity.ok(patentes);
    }

    // Buscar por apoderado
    @GetMapping("/apoderado")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorApoderado(@RequestParam String apoderado) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorApoderado(apoderado);
        return ResponseEntity.ok(patentes);
    }

    // Buscar por CIP
    @GetMapping("/cip")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPorCip(@RequestParam String cip) {
        List<PatenteRespuestaDTO> patentes = patenteService.buscarPorCip(cip);
        return ResponseEntity.ok(patentes);
    }

    // Obtener patentes recientes
    @GetMapping("/recientes")
    public ResponseEntity<List<PatenteRespuestaDTO>> obtenerPatentesRecientes(@RequestParam Date fechaInicio) {
        List<PatenteRespuestaDTO> patentes = patenteService.obtenerPatentesRecientes(fechaInicio);
        return ResponseEntity.ok(patentes);
    }

    // Obtener patentes por solicitante
    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<PatenteRespuestaDTO>> obtenerPatentesPorSolicitante(@PathVariable Integer solicitanteId) {
        try {
            List<PatenteRespuestaDTO> patentes = patenteService.obtenerPatentesPorSolicitante(solicitanteId);
            return ResponseEntity.ok(patentes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener estadísticas
    @GetMapping("/estadisticas/estado/{estado}")
    public ResponseEntity<Long> contarPatentesPorEstado(@PathVariable String estado) {
        long cantidad = patenteService.contarPatentesPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    // Obtener total de patentes
    @GetMapping("/estadisticas/total")
    public ResponseEntity<Long> contarTotalPatentes() {
        long total = patenteService.contarTotalPatentes();
        return ResponseEntity.ok(total);
    }
} 