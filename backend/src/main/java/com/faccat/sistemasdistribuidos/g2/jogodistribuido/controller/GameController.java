package com.faccat.sistemasdistribuidos.g2.jogodistribuido.controller;


import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.InscreverPartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class GameController {
    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    List<Partida> partidas=new ArrayList<>();

    @MessageMapping("/game/partidas")
    @SendTo("/game/partidas")
    public List<Partida> connect(SimpMessageHeaderAccessor headerAccessor){
        System.out.println("CONSULTOU AS PARTIDAS CRIADAS");
        return partidas;

    }
    @MessageMapping("/game/initGame")
    @SendTo("/game/partidas")
    public void init(@Payload Jogador user, SimpMessageHeaderAccessor headerAccessor){
        System.out.println(user.getUserName());
        System.out.println("JOGO CRIADO NO BANCO DE DADOS");
        Partida partida=new Partida();
        partida.setId(partidas.size());
        partida.getJogadores().add(user);
        partidas.add(partida);
        simpleMessagingTemplate.convertAndSend("/game/partidas",partidas);
    }
    @MessageMapping("/game/partida/{idPartida}")
    @SendTo("/game/partida/{idPartida}")
    public Partida subscribePartida(@Payload InscreverPartidaDTO subscriber, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("Jogador: "+subscriber.getUserName()+" entrou na partida "+subscriber.getIdPartida());

        Partida partida = this.partidas.stream().filter(p -> p.getId()==subscriber.getIdPartida()).findFirst().get();
        Jogador novo=new Jogador();
        novo.setUserName(subscriber.getUserName());
        partida.getJogadores().add(novo);
        System.out.println("Partida: "+partida.getId());
        System.out.println("Jogadores: "+partida.getJogadores().size());
        simpleMessagingTemplate.convertAndSend("/game/partidas",partidas);
        return partida;

    }

}
