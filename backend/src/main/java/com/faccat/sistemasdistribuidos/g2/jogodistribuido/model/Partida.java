package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;


import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Partida {

    @Id
    @SequenceGenerator(name = "SEQ_PARTIDA", allocationSize = 1, sequenceName = "partida_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTIDA")
    private long id;

    @Enumerated(EnumType.STRING)
    private ESituacaoPartida situacao;

    @Column(columnDefinition = "integer default 1")
    private int rodada;

    private int jogadores;

    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;

    @Column(columnDefinition = "TEXT")
    private String cartasRodadaAtual;

    private int quantidadeJogadas;

    public Partida() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ESituacaoPartida getSituacao() {
        return situacao;
    }

    public void setSituacao(ESituacaoPartida situacao) {
        this.situacao = situacao;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }

    public int getJogadores() {
        return jogadores;
    }

    public void setJogadores(int jogadores) {
        this.jogadores = jogadores;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getCartasRodadaAtual() {
        return cartasRodadaAtual;
    }

    public void setCartasRodadaAtual(String cartasRodadaAtual) {
        this.cartasRodadaAtual = cartasRodadaAtual;
    }

    public int getQuantidadeJogadas() {
        return quantidadeJogadas;
    }

    public void setQuantidadeJogadas(int quantidadeJogadas) {
        this.quantidadeJogadas = quantidadeJogadas;
    }
}
