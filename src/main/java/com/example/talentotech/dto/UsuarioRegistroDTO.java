package com.example.talentotech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

public class UsuarioRegistroDTO {
    @Pattern(regexp = "CC|TI|NIT", message = "El tipo de documento debe ser CC, TI o NIT")
    private String tipoDocumento;

    @NotBlank
    private String noDocumento;

    @NotBlank
    private String nombre;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @Pattern(regexp = "admin|user", message = "El tipo de usuario debe ser admin o user")
    private String tipoUsuario;

    @Email
    private String correo;

    private String celular;

    private Date fechaNacimiento;

    // Getters y setters
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNoDocumento() { return noDocumento; }
    public void setNoDocumento(String noDocumento) { this.noDocumento = noDocumento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
} 