package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

public class CartaEscolhidaDTO {
    private long idPartida;
    private String jogador;
    private String value;

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
