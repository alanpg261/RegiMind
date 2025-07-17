package com.example.talentotech.service;

import com.example.talentotech.dto.UsuarioRegistroDTO;
import com.example.talentotech.dto.UsuarioLoginDTO;
import com.example.talentotech.dto.UsuarioRespuestaDTO;
import com.example.talentotech.dto.PaginaRespuestaDTO;
import com.example.talentotech.entity.Usuario;
import com.example.talentotech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto) {
        // Validar que el correo y el documento no estén registrados
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuarioRepository.findByNoDocumento(dto.getNoDocumento()).isPresent()) {
            throw new RuntimeException("El número de documento ya está registrado");
        }
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setTipoDocumento(dto.getTipoDocumento());
        usuario.setNoDocumento(dto.getNoDocumento());
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCelular(dto.getCelular());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario = usuarioRepository.save(usuario);
        // Preparar respuesta
        return toRespuestaDTO(usuario);
    }

    public UsuarioRespuestaDTO loginUsuario(UsuarioLoginDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(dto.getCorreo());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(dto.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        return toRespuestaDTO(usuario);
    }

    // Métodos para el dashboard de admin
    public long contarTotalUsuarios() {
        return usuarioRepository.count();
    }

    public long contarUsuariosRegistradosHoy() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date inicioDia = cal.getTime();
        
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date finDia = cal.getTime();
        
        return usuarioRepository.countByFechaNacimientoBetween(inicioDia, finDia);
    }

    public PaginaRespuestaDTO<UsuarioRespuestaDTO> obtenerUsuariosPaginados(int page, int size) {
        List<Usuario> todosLosUsuarios = usuarioRepository.findAll();
        
        // Aplicar paginación
        int inicio = page * size;
        int fin = Math.min(inicio + size, todosLosUsuarios.size());
        
        List<Usuario> usuariosPagina = todosLosUsuarios.subList(inicio, fin);
        
        // Convertir a DTOs
        List<UsuarioRespuestaDTO> dtoPagina = usuariosPagina.stream()
            .map(this::toRespuestaDTO)
            .collect(Collectors.toList());
        
        return new PaginaRespuestaDTO<>(
            dtoPagina,
            page,
            size,
            (long) todosLosUsuarios.size()
        );
    }

    public List<Map<String, Object>> obtenerActividadReciente() {
        // Simular actividad reciente (en un sistema real esto vendría de logs)
        List<Map<String, Object>> actividad = new ArrayList<>();
        
        Map<String, Object> actividad1 = new HashMap<>();
        actividad1.put("tipo", "registro");
        actividad1.put("descripcion", "Nuevo usuario registrado");
        actividad1.put("tiempo", "Hace 5 minutos");
        actividad1.put("color", "green");
        actividad.add(actividad1);
        
        Map<String, Object> actividad2 = new HashMap<>();
        actividad2.put("tipo", "busqueda");
        actividad2.put("descripcion", "Búsqueda de patentes realizada");
        actividad2.put("tiempo", "Hace 12 minutos");
        actividad2.put("color", "blue");
        actividad.add(actividad2);
        
        Map<String, Object> actividad3 = new HashMap<>();
        actividad3.put("tipo", "sistema");
        actividad3.put("descripcion", "Sistema actualizado");
        actividad3.put("tiempo", "Hace 1 hora");
        actividad3.put("color", "yellow");
        actividad.add(actividad3);
        
        return actividad;
    }

    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public void cambiarRolUsuario(Integer id, String nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setTipoUsuario(nuevoRol);
        usuarioRepository.save(usuario);
    }

    private UsuarioRespuestaDTO toRespuestaDTO(Usuario usuario) {
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();
        dto.setId(usuario.getId());
        dto.setTipoDocumento(usuario.getTipoDocumento());
        dto.setNoDocumento(usuario.getNoDocumento());
        dto.setNombre(usuario.getNombre());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setCorreo(usuario.getCorreo());
        dto.setCelular(usuario.getCelular());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        return dto;
    }
} 