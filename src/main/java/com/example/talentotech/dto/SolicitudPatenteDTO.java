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
    
    @NotNull(message = "El ID del solicitante es obligatorio")
    private Integer solicitanteId;
    
    @NotNull(message = "El ID del inventor es obligatorio")
    private Integer inventorId;
    
    private Integer apoderadoId;
    
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

    public Integer getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Integer solicitanteId) { this.solicitanteId = solicitanteId; }

    public Integer getInventorId() { return inventorId; }
    public void setInventorId(Integer inventorId) { this.inventorId = inventorId; }

    public Integer getApoderadoId() { return apoderadoId; }
    public void setApoderadoId(Integer apoderadoId) { this.apoderadoId = apoderadoId; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }
} 