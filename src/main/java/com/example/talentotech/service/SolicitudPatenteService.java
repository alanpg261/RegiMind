package com.example.talentotech.service;

import com.example.talentotech.dto.SolicitudPatenteDTO;
import com.example.talentotech.dto.SolicitudPatenteRespuestaDTO;
import com.example.talentotech.entity.Patente;
import com.example.talentotech.entity.PatenteSolicitante;
import com.example.talentotech.entity.SolicitudPatente;
import com.example.talentotech.entity.Usuario;
import com.example.talentotech.repository.PatenteRepository;
import com.example.talentotech.repository.PatenteSolicitanteRepository;
import com.example.talentotech.repository.SolicitudPatenteRepository;
import com.example.talentotech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudPatenteService {
    @Autowired
    private SolicitudPatenteRepository solicitudPatenteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PatenteRepository patenteRepository;

    @Autowired
    private PatenteSolicitanteRepository patenteSolicitanteRepository;

    // Crear nueva solicitud
    @Transactional
    public SolicitudPatenteRespuestaDTO crearSolicitud(SolicitudPatenteDTO solicitudDTO) {
        // Validar que el expediente no exista ya
        if (solicitudPatenteRepository.findByExpediente(solicitudDTO.getExpediente()) != null) {
            throw new RuntimeException("Ya existe una solicitud con este expediente");
        }

        // Validar que el expediente no exista en patentes aprobadas
        if (patenteRepository.findByExpediente(solicitudDTO.getExpediente()).isPresent()) {
            throw new RuntimeException("Ya existe una patente aprobada con este expediente");
        }

        // Obtener solicitante
        Usuario solicitante = usuarioRepository.findById(solicitudDTO.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));

        // Crear solicitud
        SolicitudPatente solicitud = new SolicitudPatente();
        solicitud.setExpediente(solicitudDTO.getExpediente());
        solicitud.setTipoPatente(solicitudDTO.getTipoPatente());
        solicitud.setTitulo(solicitudDTO.getTitulo());
        solicitud.setFecha(solicitudDTO.getFecha());
        solicitud.setEstado("PENDIENTE"); // Siempre pendiente
        solicitud.setSolicitante(solicitante);
        solicitud.setInventor(solicitante); // Por ahora usamos el solicitante como inventor
        solicitud.setApoderado(null); // Por ahora no manejamos apoderado
        solicitud.setCip(solicitudDTO.getCip());

        SolicitudPatente solicitudGuardada = solicitudPatenteRepository.save(solicitud);
        return convertirADTO(solicitudGuardada);
    }

    // Obtener todas las solicitudes (para admin)
    public List<SolicitudPatenteRespuestaDTO> obtenerTodasLasSolicitudes() {
        return solicitudPatenteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener solicitudes por estado
    public List<SolicitudPatenteRespuestaDTO> obtenerSolicitudesPorEstado(String estado) {
        return solicitudPatenteRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener solicitudes pendientes
    public List<SolicitudPatenteRespuestaDTO> obtenerSolicitudesPendientes() {
        return solicitudPatenteRepository.findByEstadoOrderByFechaDesc("PENDIENTE").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener solicitudes por solicitante
    public List<SolicitudPatenteRespuestaDTO> obtenerSolicitudesPorSolicitante(Integer solicitanteId) {
        Usuario solicitante = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));
        
        return solicitudPatenteRepository.findBySolicitante(solicitante).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Aprobar solicitud
    @Transactional
    public SolicitudPatenteRespuestaDTO aprobarSolicitud(Integer solicitudId) {
        SolicitudPatente solicitud = solicitudPatenteRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Verificar que la solicitud esté pendiente (ignorando mayúsculas/minúsculas)
        String estado = solicitud.getEstado();
        if (!"PENDIENTE".equalsIgnoreCase(estado)) {
            throw new RuntimeException("Solo se pueden aprobar solicitudes pendientes. Estado actual: " + estado);
        }

        // Crear patente
        Patente patente = new Patente();
        patente.setExpediente(solicitud.getExpediente());
        patente.setTipoPatente(solicitud.getTipoPatente());
        patente.setTitulo(solicitud.getTitulo());
        patente.setFecha(solicitud.getFecha());
        patente.setEstado("Publicada sin pago");
        patente.setInventor(solicitud.getInventor().getNombre());
        patente.setApoderado(null); // Por ahora no manejamos apoderado
        patente.setCip(solicitud.getCip());

        Patente patenteGuardada = patenteRepository.save(patente);

        // Crear relación patente-solicitante
        PatenteSolicitante patenteSolicitante = new PatenteSolicitante();
        patenteSolicitante.setPatente(patenteGuardada);
        patenteSolicitante.setSolicitante(solicitud.getSolicitante());
        patenteSolicitanteRepository.save(patenteSolicitante);

        // Actualizar estado de la solicitud
        solicitud.setEstado("APROBADA");
        SolicitudPatente solicitudActualizada = solicitudPatenteRepository.save(solicitud);

        return convertirADTO(solicitudActualizada);
    }

    // Rechazar solicitud
    @Transactional
    public SolicitudPatenteRespuestaDTO rechazarSolicitud(Integer solicitudId, String motivo) {
        SolicitudPatente solicitud = solicitudPatenteRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!"PENDIENTE".equalsIgnoreCase(solicitud.getEstado())) {
            throw new RuntimeException("Solo se pueden rechazar solicitudes pendientes");
        }

        solicitud.setEstado("RECHAZADA");
        SolicitudPatente solicitudActualizada = solicitudPatenteRepository.save(solicitud);

        return convertirADTO(solicitudActualizada);
    }

    // Obtener solicitud por ID
    public SolicitudPatenteRespuestaDTO obtenerSolicitudPorId(Integer solicitudId) {
        SolicitudPatente solicitud = solicitudPatenteRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        return convertirADTO(solicitud);
    }

    // Buscar solicitudes por título
    public List<SolicitudPatenteRespuestaDTO> buscarSolicitudesPorTitulo(String titulo) {
        return solicitudPatenteRepository.findByTituloContaining(titulo).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener estadísticas
    public long contarSolicitudesPorEstado(String estado) {
        return solicitudPatenteRepository.countByEstado(estado);
    }

    // Convertir entidad a DTO
    private SolicitudPatenteRespuestaDTO convertirADTO(SolicitudPatente solicitud) {
        return new SolicitudPatenteRespuestaDTO(
                solicitud.getId(),
                solicitud.getExpediente(),
                solicitud.getTipoPatente(),
                solicitud.getTitulo(),
                solicitud.getFecha(),
                solicitud.getEstado(),
                solicitud.getSolicitante().getNombre(),
                solicitud.getInventor().getNombre(), // Por ahora usamos el nombre del usuario inventor
                null, // Por ahora no manejamos apoderado
                solicitud.getCip()
        );
    }
} 