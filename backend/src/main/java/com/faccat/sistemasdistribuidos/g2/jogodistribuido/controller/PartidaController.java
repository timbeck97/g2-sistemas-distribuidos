package com.faccat.sistemasdistribuidos.g2.jogodistribuido.controller;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.CartaEscolhidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.InscreverPartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.RodadaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    private GameService gameService;
    @Autowired
    private PartidaRepository partidaRepository;

    @GetMapping
    public List<PartidaDTO> getPartidas(){
        return gameService.findPartidasBySituacao(ESituacaoPartida.AGUARDANDO);
    }

    @PostMapping()
    public ResponseEntity<PartidaDTO> createPartida(){
        return ResponseEntity.ok().body(gameService.iniciaPartida());
    }

    @PostMapping("/entrar/{id}")
    public ResponseEntity<PartidaDTO> entrarPartida(@PathVariable Long id, @RequestBody InscreverPartidaDTO subscriber ){
        Partida partida=gameService.entrarPartida(subscriber);
        return ResponseEntity.ok().body(gameService.createPartidaDTO(partida));
    }
    @GetMapping("/{id}")
    public RodadaDTO getRodada(@PathVariable Long id){
        Partida p = partidaRepository.findById(id).orElseThrow(() -> new RuntimeException("Partida não encontrada"));
        return gameService.getRodadaDTO(p);
    }
    @PostMapping("/jogar")
    public RodadaDTO sendCard(@RequestBody CartaEscolhidaDTO card){
        gameService.salvarCartaEscolhida(card);
        return new RodadaDTO();
    }
}
