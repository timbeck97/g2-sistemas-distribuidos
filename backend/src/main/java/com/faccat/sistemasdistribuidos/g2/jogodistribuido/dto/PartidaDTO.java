package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.JogadorDTO;

import java.util.Date;
import java.util.List;

public class PartidaDTO {
    private long id;

    private ESituacaoPartida situacao;

    private Date hora;
    private List<JogadorDTO> jogadores;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<JogadorDTO> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorDTO> jogadores) {
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
