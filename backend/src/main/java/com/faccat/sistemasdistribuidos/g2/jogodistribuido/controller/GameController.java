package com.faccat.sistemasdistribuidos.g2.jogodistribuido.controller;


import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.InscreverPartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.service.GameService;
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

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private GameService gameService;



    @MessageMapping("/game/partidas")
    @SendTo("/game/partidas")
    public List<PartidaDTO> connect(SimpMessageHeaderAccessor headerAccessor){
        System.out.println("CONSULTOU AS PARTIDAS CRIADAS");
        return gameService.findPartidas();

    }

    @MessageMapping("/game/partida/{idPartida}")
    @SendTo("/game/partida/{idPartida}")
    public PartidaDTO subscribePartida(@Payload InscreverPartidaDTO subscriber, SimpMessageHeaderAccessor headerAccessor){
        //System.out.println("Jogador: "+subscriber.getNomeParticipante()+" entrou na partida "+subscriber.getIdPartida());
        Partida partida=gameService.entrarPartida(subscriber);
        simpleMessagingTemplate.convertAndSend("/game/partidas",gameService.findPartidas());
        return gameService.createPartidaDTO(partida);

    }

}
