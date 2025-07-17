package com.example.talentotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "solicitudpatentes")
public class SolicitudPatente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String expediente;

    @NotBlank
    @Column(name = "tipopatente")
    private String tipoPatente;

    @NotBlank
    private String titulo;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotBlank
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitante")
    @NotNull
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventor")
    @NotNull
    private Usuario inventor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apoderado")
    private Usuario apoderado;

    private String cip;

    // Constructor por defecto
    public SolicitudPatente() {
        this.fecha = new Date();
        this.estado = "PENDIENTE";
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

    public Usuario getSolicitante() { return solicitante; }
    public void setSolicitante(Usuario solicitante) { this.solicitante = solicitante; }

    public Usuario getInventor() { return inventor; }
    public void setInventor(Usuario inventor) { this.inventor = inventor; }

    public Usuario getApoderado() { return apoderado; }
    public void setApoderado(Usuario apoderado) { this.apoderado = apoderado; }

    public String getCip() { return cip; }
    public void setCip(String cip) { this.cip = cip; }
} 