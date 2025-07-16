package com.example.talentotech.controller;

import com.example.talentotech.dto.PatenteBusquedaDTO;
import com.example.talentotech.dto.PatenteRespuestaDTO;
import com.example.talentotech.service.PatenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patentes")
public class PatenteController {
    @Autowired
    private PatenteService patenteService;

    @PostMapping("/buscar")
    public ResponseEntity<List<PatenteRespuestaDTO>> buscarPatentes(@RequestBody PatenteBusquedaDTO filtros) {
        List<PatenteRespuestaDTO> resultados = patenteService.buscarPatentes(filtros);
        return ResponseEntity.ok(resultados);
    }
} 