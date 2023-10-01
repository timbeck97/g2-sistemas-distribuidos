package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;



public class JogadorDTO {
    private long id;
    private String nome;
    private int pontos;

    public JogadorDTO(PartidaJogador pj) {
        this.id = pj.getJogador().getId();
        this.nome = pj.getJogador().getNome();
        this.pontos = pj.getPontos();
    }

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

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
