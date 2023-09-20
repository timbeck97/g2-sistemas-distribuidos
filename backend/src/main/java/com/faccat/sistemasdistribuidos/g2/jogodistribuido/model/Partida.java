package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Partida {

    @Id
    @SequenceGenerator(name = "SEQ_PARTIDA", allocationSize = 1, sequenceName = "partida_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTIDA")
    private long id;

    @Column(columnDefinition = "boolean default false")
    private boolean encerrada;

    public Partida() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }
}
