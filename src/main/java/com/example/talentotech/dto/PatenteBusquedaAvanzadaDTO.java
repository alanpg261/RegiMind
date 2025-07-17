package com.example.talentotech.dto;

import java.util.Date;
import java.util.List;

public class PatenteBusquedaAvanzadaDTO {
    
    // Filtros básicos
    private String titulo;
    private String expediente;
    private String tipoPatente;
    private String estado;
    private String inventor;
    private String apoderado;
    private String cip;
    private Date fechaInicio;
    private Date fechaFin;
    
    // Filtros avanzados
    private List<String> tiposPatente;
    private List<String> estados;
    private List<String> inventores;
    private List<String> apoderados;
    
    // Paginación
    private Integer pagina = 0;
    private Integer tamanio = 20;
    
    // Ordenamiento
    private String ordenarPor = "fecha";
    private String direccion = "DESC"; // ASC o DESC
    
    // Búsqueda en múltiples campos
    private String terminoBusqueda;
    
    // Filtros de fecha
    private Boolean soloRecientes = false;
    private Integer diasRecientes = 30;

    // Constructor por defecto
    public PatenteBusquedaAvanzadaDTO() {}

    // Getters y setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public String getTipoPatente() { return tipoPatente; }
    public void setTipoPatente(String tipoPatente) { this.tipoPatente = tipoPatente; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getInventor() { return inventor; }
    public void setInventor(String inventor) { this.inventor = inventor; }

    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public List<String> getTiposPatente() { return tiposPatente; }
    public void setTiposPatente(List<String> tiposPatente) { this.tiposPatente = tiposPatente; }

    public List<String> getEstados() { return estados; }
    public void setEstados(List<String> estados) { this.estados = estados; }

    public List<String> getInventores() { return inventores; }
    public void setInventores(List<String> inventores) { this.inventores = inventores; }

    public List<String> getApoderados() { return apoderados; }
    public void setApoderados(List<String> apoderados) { this.apoderados = apoderados; }

    public Integer getPagina() { return pagina; }
    public void setPagina(Integer pagina) { this.pagina = pagina; }

    public Integer getTamanio() { return tamanio; }
    public void setTamanio(Integer tamanio) { this.tamanio = tamanio; }

    public String getOrdenarPor() { return ordenarPor; }
    public void setOrdenarPor(String ordenarPor) { this.ordenarPor = ordenarPor; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTerminoBusqueda() { return terminoBusqueda; }
    public void setTerminoBusqueda(String terminoBusqueda) { this.terminoBusqueda = terminoBusqueda; }

    public Boolean getSoloRecientes() { return soloRecientes; }
    public void setSoloRecientes(Boolean soloRecientes) { this.soloRecientes = soloRecientes; }

    public Integer getDiasRecientes() { return diasRecientes; }
    public void setDiasRecientes(Integer diasRecientes) { this.diasRecientes = diasRecientes; }
} 