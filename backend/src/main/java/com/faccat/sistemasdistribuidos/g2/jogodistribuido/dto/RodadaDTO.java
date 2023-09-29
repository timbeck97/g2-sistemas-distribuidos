package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;

import java.util.Date;
import java.util.List;

public class RodadaDTO {
    private long idPartida;
    private List<Jogador> jogadores;
    private int rodada;

    private Date hora;

    private List<CardDTO> cartas;

    private ESituacaoPartida situacaoPartida;

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }

    public List<CardDTO> getCartas() {
        return cartas;
    }

    public void setCartas(List<CardDTO> cartas) {
        this.cartas = cartas;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public ESituacaoPartida getSituacaoPartida() {
        return situacaoPartida;
    }

    public void setSituacaoPartida(ESituacaoPartida situacaoPartida) {
        this.situacaoPartida = situacaoPartida;
    }

    @Override
    public String toString() {
        return "RodadaDTO{" +
                "idPartida=" + idPartida +
                ", jogadores=" + jogadores +
                ", rodada=" + rodada +
                ", cartas=" + cartas +
                '}';
    }
}
