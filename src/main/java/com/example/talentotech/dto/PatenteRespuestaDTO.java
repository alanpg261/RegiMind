package com.example.talentotech.dto;

import java.util.Date;
import java.util.List;

public class PatenteRespuestaDTO {
    private String titulo;
    private String expediente;
    private String tipoPatente;
    private Date fecha;
    private String estado;
    private String inventor;
    private String apoderado;
    private String cip;
    private List<String> solicitantes;

    // Getters y setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public String getTipoPatente() { return tipoPatente; }
    public void setTipoPatente(String tipoPatente) { this.tipoPatente = tipoPatente; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getInventor() { return inventor; }
    public void setInventor(String inventor) { this.inventor = inventor; }

    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }

    public List<String> getSolicitantes() { return solicitantes; }
    public void setSolicitantes(List<String> solicitantes) { this.solicitantes = solicitantes; }
} 