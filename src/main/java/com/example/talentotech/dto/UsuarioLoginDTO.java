package com.example.talentotech.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDTO {
    @NotBlank
    private String correo;

    @NotBlank
    private String contrasena;

    // Getters y setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
} 