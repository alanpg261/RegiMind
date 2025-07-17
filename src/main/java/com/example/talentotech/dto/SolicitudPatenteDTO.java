package com.example.talentotech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class SolicitudPatenteDTO {
    
    @NotBlank(message = "El expediente es obligatorio")
    private String expediente;
    
    @NotBlank(message = "El tipo de patente es obligatorio")
    private String tipoPatente;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;
    
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    
    @NotNull(message = "El ID del solicitante es obligatorio")
    private Integer solicitanteId;
    
    @NotBlank(message = "El inventor es obligatorio")
    private String inventor;
    
    private String apoderado;
    
    private String cip;

    // Constructor por defecto
    public SolicitudPatenteDTO() {
        this.fecha = new Date();
    }

    // Getters y setters
    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public String getTipoPatente() { return tipoPatente; }
    public void setTipoPatente(String tipoPatente) { this.tipoPatente = tipoPatente; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Integer solicitanteId) { this.solicitanteId = solicitanteId; }

    public String getInventor() { return inventor; }
    public void setInventor(String inventor) { this.inventor = inventor; }

    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }
} 