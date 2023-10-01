package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;

import jakarta.persistence.*;

@Entity
public class PartidaJogador {

    @Id
    @SequenceGenerator(name = "SEQ_PARTIDA_JOGADOR", allocationSize = 1, sequenceName = "partida_jogador_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTIDA_JOGADOR")
    private long id;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Jogador jogador;

    private int pontos;

    public PartidaJogador() {
    }

    public PartidaJogador(Partida partida, Jogador jogador) {
        this.partida = partida;
        this.jogador = jogador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
