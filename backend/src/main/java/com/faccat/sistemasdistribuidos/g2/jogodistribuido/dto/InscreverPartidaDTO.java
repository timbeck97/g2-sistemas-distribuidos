package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

public class InscreverPartidaDTO {
    private long idPartida;
    private String nomeParticipante;


    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }
}
