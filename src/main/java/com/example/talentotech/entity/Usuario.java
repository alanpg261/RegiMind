package com.example.talentotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "CC|TI|NIT", message = "El tipo de documento debe ser CC, TI o NIT")
    @Column(name = "tipodocumento")
    private String tipoDocumento;

    @Column(name = "nodocumento", unique = true)
    @NotBlank
    private String noDocumento;

    @NotBlank
    private String nombre;

    @NotBlank
    private String contrasena;

    @Pattern(regexp = "admin|user", message = "El tipo de usuario debe ser admin o user")
    @Column(name = "tipousuario")
    private String tipoUsuario;

    @Email
    @Column(unique = true)
    private String correo;

    private String celular;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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