package com.example.talentotech.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patente_solicitante")
public class PatenteSolicitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patente_id")
    private Patente patente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Patente getPatente() { return patente; }
    public void setPatente(Patente patente) { this.patente = patente; }

    public Usuario getSolicitante() { return solicitante; }
    public void setSolicitante(Usuario solicitante) { this.solicitante = solicitante; }
} 