package com.example.talentotech.service;

import com.example.talentotech.dto.PatenteBusquedaDTO;
import com.example.talentotech.dto.PatenteRespuestaDTO;
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

@Service
public class PatenteService {
    @Autowired
    private PatenteRepository patenteRepository;
    @Autowired
    private PatenteSolicitanteRepository patenteSolicitanteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PatenteRespuestaDTO> buscarPatentes(PatenteBusquedaDTO filtros) {
        // Obtener todas las patentes y filtrar en memoria (para simplicidad)
        List<Patente> patentes = patenteRepository.findAll();
        return patentes.stream()
            .filter(p -> filtros.getCip() == null || p.getCip() != null && p.getCip().toLowerCase().contains(filtros.getCip().toLowerCase()))
            .filter(p -> filtros.getTitulo() == null || p.getTitulo() != null && p.getTitulo().toLowerCase().contains(filtros.getTitulo().toLowerCase()))
            .filter(p -> filtros.getTipoPatente() == null || p.getTipoPatente() != null && p.getTipoPatente().toLowerCase().contains(filtros.getTipoPatente().toLowerCase()))
            .filter(p -> filtros.getFechaInicio() == null || (p.getFecha() != null && !p.getFecha().before(filtros.getFechaInicio())))
            .filter(p -> filtros.getFechaFin() == null || (p.getFecha() != null && !p.getFecha().after(filtros.getFechaFin())))
            .map(p -> {
                PatenteRespuestaDTO dto = new PatenteRespuestaDTO();
                dto.setTitulo(p.getTitulo());
                dto.setExpediente(p.getExpediente());
                dto.setTipoPatente(p.getTipoPatente());
                dto.setFecha(p.getFecha());
                dto.setEstado(p.getEstado());
                dto.setInventor(p.getInventor());
                dto.setApoderado(p.getApoderado());
                dto.setCip(p.getCip());
                // Obtener solicitantes
                List<PatenteSolicitante> relaciones = patenteSolicitanteRepository.findByPatenteId(p.getId());
                List<String> solicitantes = relaciones.stream()
                    .map(rel -> usuarioRepository.findById(rel.getSolicitanteId()))
                    .filter(Optional::isPresent)
                    .map(opt -> opt.get().getNombre())
                    .collect(Collectors.toList());
                dto.setSolicitantes(solicitantes);
                return dto;
            })
            .collect(Collectors.toList());
    }
} 