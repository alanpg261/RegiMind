package com.example.talentotech.dto;

import java.util.Date;

public class SolicitudPatenteRespuestaDTO {
    
    private Integer id;
    private String expediente;
    private String tipoPatente;
    private String titulo;
    private Date fecha;
    private String estado;
    private String solicitanteNombre;
    private String inventorNombre;
    private String apoderadoNombre;
    private String cip;

    // Constructor por defecto
    public SolicitudPatenteRespuestaDTO() {}

    // Constructor con parámetros
    public SolicitudPatenteRespuestaDTO(Integer id, String expediente, String tipoPatente, 
                                      String titulo, Date fecha, String estado,
                                      String solicitanteNombre, String inventorNombre, 
                                      String apoderadoNombre, String cip) {
        this.id = id;
        this.expediente = expediente;
        this.tipoPatente = tipoPatente;
        this.titulo = titulo;
        this.fecha = fecha;
        this.estado = estado;
        this.solicitanteNombre = solicitanteNombre;
        this.inventorNombre = inventorNombre;
        this.apoderadoNombre = apoderadoNombre;
        this.cip = cip;
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public String getSolicitanteNombre() { return solicitanteNombre; }
    public void setSolicitanteNombre(String solicitanteNombre) { this.solicitanteNombre = solicitanteNombre; }

    public String getInventorNombre() { return inventorNombre; }
    public void setInventorNombre(String inventorNombre) { this.inventorNombre = inventorNombre; }

    public String getApoderadoNombre() { return apoderadoNombre; }
    public void setApoderadoNombre(String apoderadoNombre) { this.apoderadoNombre = apoderadoNombre; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }
} 