package com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto;

public class CardDTO {
    private long id;
    private String value;

    private long idJogador;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(long idJogador) {
        this.idJogador = idJogador;
    }
}
