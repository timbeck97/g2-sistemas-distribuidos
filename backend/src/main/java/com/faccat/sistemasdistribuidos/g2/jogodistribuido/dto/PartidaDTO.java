package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;

import java.util.List;

public class PartidaDTO {
    private long id;
    private List<Jogador> jogadores;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
