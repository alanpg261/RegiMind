package com.example.talentotech.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patentes")
public class Patente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String expediente;

    @Column(name = "tipopatente")
    private String tipoPatente;
    private String titulo;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String estado;
    private String inventor;
    private String apoderado;
    private String cip;

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

    public String getInventor() { return inventor; }
    public void setInventor(String inventor) { this.inventor = inventor; }

    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }
} 