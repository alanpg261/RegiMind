package com.example.talentotech.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patente_solicitante")
public class PatenteSolicitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "patente_id")
    private Integer patenteId;

    @Column(name = "solicitante_id")
    private Integer solicitanteId;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPatenteId() { return patenteId; }
    public void setPatenteId(Integer patenteId) { this.patenteId = patenteId; }

    public Integer getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Integer solicitanteId) { this.solicitanteId = solicitanteId; }
} 