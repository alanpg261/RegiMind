package com.example.talentotech.service;

import com.example.talentotech.dto.UsuarioRegistroDTO;
import com.example.talentotech.dto.UsuarioLoginDTO;
import com.example.talentotech.dto.UsuarioRespuestaDTO;
import com.example.talentotech.entity.Usuario;
import com.example.talentotech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

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