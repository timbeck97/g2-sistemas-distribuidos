package com.faccat.sistemasdistribuidos.g2.jogodistribuido.controller;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    private GameService gameService;

    @PostMapping("/{participante}")
    //@CrossOrigin(value = "*")
    public ResponseEntity<PartidaDTO> createPartida(@PathVariable String participante){
        Jogador j=new Jogador();
        j.setNome(participante);
        return ResponseEntity.ok().body(gameService.iniciaPartida(j));
    }
}
