package com.example.talentotech.repository;

import com.example.talentotech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Date;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNoDocumento(String noDocumento);
    long countByFechaNacimientoBetween(Date fechaInicio, Date fechaFin);
} 