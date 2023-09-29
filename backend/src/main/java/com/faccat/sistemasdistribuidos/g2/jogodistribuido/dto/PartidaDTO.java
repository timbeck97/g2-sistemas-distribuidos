package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;

import java.util.Date;
import java.util.List;

public class PartidaDTO {
    private long id;

    private ESituacaoPartida situacao;

    private Date hora;
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

    public ESituacaoPartida getSituacao() {
        return situacao;
    }

    public void setSituacao(ESituacaoPartida situacao) {
        this.situacao = situacao;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
}
