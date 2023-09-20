package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;

import jakarta.persistence.*;

@Entity
public class Jogador {
    @Id
    @SequenceGenerator(name = "SEQ_JOGADOR", allocationSize = 1, sequenceName = "jogador_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JOGADOR")
    private long id;
    @Column(columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String nome;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
