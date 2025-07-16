package com.example.talentotech.dto;

import java.util.Date;

public class PatenteBusquedaDTO {
    private String cip;
    private String titulo;
    private String tipoPatente;
    private Date fechaInicio;
    private Date fechaFin;

    // Getters y setters
    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipoPatente() { return tipoPatente; }
    public void setTipoPatente(String tipoPatente) { this.tipoPatente = tipoPatente; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
} 