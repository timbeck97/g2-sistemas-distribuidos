package com.faccat.sistemasdistribuidos.g2.jogodistribuido.service;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.InscreverPartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.PartidaJogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.JogadorRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaJogadorRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private JogadorRepository jogadorRepository;
    @Autowired
    private PartidaJogadorRepository partidaJogadorRepository;

    public PartidaDTO iniciaPartida(Jogador jogador){
        jogador=getJogador(jogador);
        Partida partida=new Partida();
        partida=partidaRepository.save(partida);
        PartidaJogador partidaJogador=new PartidaJogador(partida, jogador);
        partidaJogadorRepository.save(partidaJogador);
        return createPartidaDTO(partida);

    }
    public Partida entrarPartida(InscreverPartidaDTO subscriber){
        Jogador jogador=new Jogador();
        jogador.setNome(subscriber.getNomeParticipante());
        jogador=getJogador(jogador);
        Partida partida=partidaRepository.findById(subscriber.getIdPartida()).get();
        PartidaJogador partidaJogador=new PartidaJogador(partida, jogador);
        partidaJogadorRepository.save(partidaJogador);
        return partida;
    }
    public List<PartidaDTO> findPartidas(){
        List<Partida> partidas=partidaRepository.findByEncerrada(false);
        List<PartidaDTO> dtos=new ArrayList<>();
        for (Partida partida : partidas) {
            dtos.add(createPartidaDTO(partida));
        }
        return dtos;
    }
    public PartidaDTO createPartidaDTO(Partida partida){
        PartidaDTO dto=new PartidaDTO();
        dto.setId(partida.getId());
        dto.setJogadores(partidaJogadorRepository.findByPartidaId(partida.getId()));
        return dto;
    }
    public Jogador getJogador(Jogador j){
        if(j.getNome().isEmpty()){
            throw new RuntimeException("Nome do jogador não pode ser vazio");
        }
        Jogador existente=jogadorRepository.findByNome(j.getNome());
        if(existente==null){
            j=jogadorRepository.save(j);
        }else{
            j=existente;
        }
        return j;
    }
}
