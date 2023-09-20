package com.faccat.sistemasdistribuidos.g2.jogodistribuido.model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private long id;
    private List<Jogador> jogadores;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Jogador> getJogadores() {
        if(jogadores==null){
            jogadores=new ArrayList<>();
        }
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
