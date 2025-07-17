package com.example.talentotech.service;

import com.example.talentotech.dto.PatenteBusquedaDTO;
import com.example.talentotech.dto.PatenteBusquedaAvanzadaDTO;
import com.example.talentotech.dto.PatenteRespuestaDTO;
import com.example.talentotech.dto.PaginaRespuestaDTO;
import com.example.talentotech.entity.Patente;
import com.example.talentotech.entity.PatenteSolicitante;
import com.example.talentotech.entity.Usuario;
import com.example.talentotech.repository.PatenteRepository;
import com.example.talentotech.repository.PatenteSolicitanteRepository;
import com.example.talentotech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.Calendar;

@Service
public class PatenteService {
    @Autowired
    private PatenteRepository patenteRepository;
    @Autowired
    private PatenteSolicitanteRepository patenteSolicitanteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Búsqueda avanzada con filtros
    public List<PatenteRespuestaDTO> buscarPatentes(PatenteBusquedaDTO filtros) {
        List<Patente> patentes = patenteRepository.findAll();
        
        return patentes.stream()
            .filter(p -> aplicarFiltros(p, filtros))
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda avanzada con paginación
    public PaginaRespuestaDTO<PatenteRespuestaDTO> buscarPatentesAvanzado(PatenteBusquedaAvanzadaDTO filtros) {
        List<Patente> todasLasPatentes = patenteRepository.findAll();
        
        // Aplicar filtros
        List<Patente> patentesFiltradas = todasLasPatentes.stream()
            .filter(p -> aplicarFiltrosAvanzados(p, filtros))
            .collect(Collectors.toList());
        
        // Aplicar ordenamiento
        patentesFiltradas = ordenarPatentes(patentesFiltradas, filtros.getOrdenarPor(), filtros.getDireccion());
        
        // Aplicar paginación
        int inicio = filtros.getPagina() * filtros.getTamanio();
        int fin = Math.min(inicio + filtros.getTamanio(), patentesFiltradas.size());
        
        List<Patente> patentesPagina = patentesFiltradas.subList(inicio, fin);
        
        // Convertir a DTOs
        List<PatenteRespuestaDTO> dtoPagina = patentesPagina.stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
        
        return new PaginaRespuestaDTO<>(
            dtoPagina,
            filtros.getPagina(),
            filtros.getTamanio(),
            (long) patentesFiltradas.size()
        );
    }

    // Búsqueda por título
    public List<PatenteRespuestaDTO> buscarPorTitulo(String titulo) {
        return patenteRepository.findByTituloContaining(titulo).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda por expediente
    public Optional<PatenteRespuestaDTO> buscarPorExpediente(String expediente) {
        return patenteRepository.findByExpediente(expediente)
            .map(this::convertirADTO);
    }

    // Búsqueda por tipo de patente
    public List<PatenteRespuestaDTO> buscarPorTipoPatente(String tipoPatente) {
        return patenteRepository.findByTipoPatente(tipoPatente).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda por estado
    public List<PatenteRespuestaDTO> buscarPorEstado(String estado) {
        return patenteRepository.findByEstado(estado).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda por inventor
    public List<PatenteRespuestaDTO> buscarPorInventor(String inventor) {
        return patenteRepository.findByInventor(inventor).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda por apoderado
    public List<PatenteRespuestaDTO> buscarPorApoderado(String apoderado) {
        return patenteRepository.findByApoderado(apoderado).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Búsqueda por CIP
    public List<PatenteRespuestaDTO> buscarPorCip(String cip) {
        return patenteRepository.findByCipContaining(cip).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener patentes recientes
    public List<PatenteRespuestaDTO> obtenerPatentesRecientes(Date fechaInicio) {
        return patenteRepository.findPatentesRecientes(fechaInicio).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener todas las patentes
    public List<PatenteRespuestaDTO> obtenerTodasLasPatentes() {
        return patenteRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener patente por ID
    public Optional<PatenteRespuestaDTO> obtenerPatentePorId(Integer id) {
        return patenteRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Obtener estadísticas
    public long contarPatentesPorEstado(String estado) {
        return patenteRepository.countByEstado(estado);
    }

    // Obtener total de patentes
    public long contarTotalPatentes() {
        return patenteRepository.count();
    }

    // Obtener patentes recientes (últimos 30 días)
    public long contarPatentesRecientes() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date fechaLimite = cal.getTime();

        return patenteRepository.findAll().stream()
            .filter(p -> p.getFecha() != null && p.getFecha().after(fechaLimite))
            .count();
    }

    // Obtener patentes paginadas para admin
    public PaginaRespuestaDTO<PatenteRespuestaDTO> obtenerPatentesPaginadas(int page, int size) {
        List<Patente> todasLasPatentes = patenteRepository.findAll();

        // Aplicar paginación
        int inicio = page * size;
        int fin = Math.min(inicio + size, todasLasPatentes.size());

        List<Patente> patentesPagina = todasLasPatentes.subList(inicio, fin);

        // Convertir a DTOs
        List<PatenteRespuestaDTO> dtoPagina = patentesPagina.stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());

        return new PaginaRespuestaDTO<>(
            dtoPagina,
            page,
            size,
            (long) todasLasPatentes.size()
        );
    }

    // Obtener patentes por solicitante
    public List<PatenteRespuestaDTO> obtenerPatentesPorSolicitante(Integer solicitanteId) {
        Usuario solicitante = usuarioRepository.findById(solicitanteId)
            .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));
        
        return patenteSolicitanteRepository.findBySolicitante(solicitante).stream()
            .map(ps -> convertirADTO(ps.getPatente()))
            .collect(Collectors.toList());
    }

    // Aplicar filtros a una patente
    private boolean aplicarFiltros(Patente patente, PatenteBusquedaDTO filtros) {
        // Filtro por CIP
        if (StringUtils.hasText(filtros.getCip()) && 
            (patente.getCip() == null || !patente.getCip().toLowerCase().contains(filtros.getCip().toLowerCase()))) {
            return false;
        }

        // Filtro por título
        if (StringUtils.hasText(filtros.getTitulo()) && 
            (patente.getTitulo() == null || !patente.getTitulo().toLowerCase().contains(filtros.getTitulo().toLowerCase()))) {
            return false;
        }

        // Filtro por tipo de patente
        if (StringUtils.hasText(filtros.getTipoPatente()) && 
            (patente.getTipoPatente() == null || !patente.getTipoPatente().toLowerCase().contains(filtros.getTipoPatente().toLowerCase()))) {
            return false;
        }

        // Filtro por fecha inicio
        if (filtros.getFechaInicio() != null && 
            (patente.getFecha() == null || patente.getFecha().before(filtros.getFechaInicio()))) {
            return false;
        }

        // Filtro por fecha fin
        if (filtros.getFechaFin() != null && 
            (patente.getFecha() == null || patente.getFecha().after(filtros.getFechaFin()))) {
            return false;
        }

        return true;
    }

    // Aplicar filtros avanzados
    private boolean aplicarFiltrosAvanzados(Patente patente, PatenteBusquedaAvanzadaDTO filtros) {
        // Filtro por término de búsqueda general
        if (StringUtils.hasText(filtros.getTerminoBusqueda())) {
            String termino = filtros.getTerminoBusqueda().toLowerCase();
            boolean coincide = (patente.getTitulo() != null && patente.getTitulo().toLowerCase().contains(termino)) ||
                             (patente.getExpediente() != null && patente.getExpediente().toLowerCase().contains(termino)) ||
                             (patente.getInventor() != null && patente.getInventor().toLowerCase().contains(termino)) ||
                             (patente.getCip() != null && patente.getCip().toLowerCase().contains(termino));
            if (!coincide) return false;
        }

        // Filtro por título
        if (StringUtils.hasText(filtros.getTitulo()) && 
            (patente.getTitulo() == null || !patente.getTitulo().toLowerCase().contains(filtros.getTitulo().toLowerCase()))) {
            return false;
        }

        // Filtro por expediente
        if (StringUtils.hasText(filtros.getExpediente()) && 
            (patente.getExpediente() == null || !patente.getExpediente().toLowerCase().contains(filtros.getExpediente().toLowerCase()))) {
            return false;
        }

        // Filtro por tipo de patente
        if (StringUtils.hasText(filtros.getTipoPatente()) && 
            (patente.getTipoPatente() == null || !patente.getTipoPatente().toLowerCase().contains(filtros.getTipoPatente().toLowerCase()))) {
            return false;
        }

        // Filtro por tipos de patente múltiples
        if (filtros.getTiposPatente() != null && !filtros.getTiposPatente().isEmpty() &&
            (patente.getTipoPatente() == null || !filtros.getTiposPatente().contains(patente.getTipoPatente()))) {
            return false;
        }

        // Filtro por estado
        if (StringUtils.hasText(filtros.getEstado()) && 
            (patente.getEstado() == null || !patente.getEstado().toLowerCase().contains(filtros.getEstado().toLowerCase()))) {
            return false;
        }

        // Filtro por estados múltiples
        if (filtros.getEstados() != null && !filtros.getEstados().isEmpty() &&
            (patente.getEstado() == null || !filtros.getEstados().contains(patente.getEstado()))) {
            return false;
        }

        // Filtro por inventor
        if (StringUtils.hasText(filtros.getInventor()) && 
            (patente.getInventor() == null || !patente.getInventor().toLowerCase().contains(filtros.getInventor().toLowerCase()))) {
            return false;
        }

        // Filtro por inventores múltiples
        if (filtros.getInventores() != null && !filtros.getInventores().isEmpty() &&
            (patente.getInventor() == null || !filtros.getInventores().contains(patente.getInventor()))) {
            return false;
        }

        // Filtro por apoderado
        if (StringUtils.hasText(filtros.getApoderado()) && 
            (patente.getApoderado() == null || !patente.getApoderado().toLowerCase().contains(filtros.getApoderado().toLowerCase()))) {
            return false;
        }

        // Filtro por apoderados múltiples
        if (filtros.getApoderados() != null && !filtros.getApoderados().isEmpty() &&
            (patente.getApoderado() == null || !filtros.getApoderados().contains(patente.getApoderado()))) {
            return false;
        }

        // Filtro por CIP
        if (StringUtils.hasText(filtros.getCip()) && 
            (patente.getCip() == null || !patente.getCip().toLowerCase().contains(filtros.getCip().toLowerCase()))) {
            return false;
        }

        // Filtro por fecha inicio
        if (filtros.getFechaInicio() != null && 
            (patente.getFecha() == null || patente.getFecha().before(filtros.getFechaInicio()))) {
            return false;
        }

        // Filtro por fecha fin
        if (filtros.getFechaFin() != null && 
            (patente.getFecha() == null || patente.getFecha().after(filtros.getFechaFin()))) {
            return false;
        }

        // Filtro por patentes recientes
        if (filtros.getSoloRecientes() != null && filtros.getSoloRecientes()) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -filtros.getDiasRecientes());
            Date fechaLimite = cal.getTime();
            
            if (patente.getFecha() == null || patente.getFecha().before(fechaLimite)) {
                return false;
            }
        }

        return true;
    }

    // Ordenar patentes
    private List<Patente> ordenarPatentes(List<Patente> patentes, String ordenarPor, String direccion) {
        boolean ascendente = "ASC".equalsIgnoreCase(direccion);
        
        switch (ordenarPor.toLowerCase()) {
            case "titulo":
                patentes.sort((p1, p2) -> {
                    String t1 = p1.getTitulo() != null ? p1.getTitulo() : "";
                    String t2 = p2.getTitulo() != null ? p2.getTitulo() : "";
                    return ascendente ? t1.compareTo(t2) : t2.compareTo(t1);
                });
                break;
            case "expediente":
                patentes.sort((p1, p2) -> {
                    String e1 = p1.getExpediente() != null ? p1.getExpediente() : "";
                    String e2 = p2.getExpediente() != null ? p2.getExpediente() : "";
                    return ascendente ? e1.compareTo(e2) : e2.compareTo(e1);
                });
                break;
            case "inventor":
                patentes.sort((p1, p2) -> {
                    String i1 = p1.getInventor() != null ? p1.getInventor() : "";
                    String i2 = p2.getInventor() != null ? p2.getInventor() : "";
                    return ascendente ? i1.compareTo(i2) : i2.compareTo(i1);
                });
                break;
            case "estado":
                patentes.sort((p1, p2) -> {
                    String e1 = p1.getEstado() != null ? p1.getEstado() : "";
                    String e2 = p2.getEstado() != null ? p2.getEstado() : "";
                    return ascendente ? e1.compareTo(e2) : e2.compareTo(e1);
                });
                break;
            case "fecha":
            default:
                patentes.sort((p1, p2) -> {
                    Date f1 = p1.getFecha() != null ? p1.getFecha() : new Date(0);
                    Date f2 = p2.getFecha() != null ? p2.getFecha() : new Date(0);
                    return ascendente ? f1.compareTo(f2) : f2.compareTo(f1);
                });
                break;
        }
        
        return patentes;
    }

    // Convertir entidad a DTO
    private PatenteRespuestaDTO convertirADTO(Patente patente) {
        PatenteRespuestaDTO dto = new PatenteRespuestaDTO();
        dto.setId(patente.getId());
        dto.setTitulo(patente.getTitulo());
        dto.setExpediente(patente.getExpediente());
        dto.setTipoPatente(patente.getTipoPatente());
        dto.setFecha(patente.getFecha());
        dto.setEstado(patente.getEstado());
        dto.setInventor(patente.getInventor());
        dto.setApoderado(patente.getApoderado());
        dto.setCip(patente.getCip());
        
        // Obtener solicitantes usando las nuevas relaciones JPA
        List<PatenteSolicitante> relaciones = patenteSolicitanteRepository.findByPatente(patente);
        List<String> solicitantes = relaciones.stream()
            .map(ps -> ps.getSolicitante().getNombre())
            .collect(Collectors.toList());
        dto.setSolicitantes(solicitantes);
        
        return dto;
    }
} 