package com.faccat.sistemasdistribuidos.g2.jogodistribuido.service;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.CardDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.InscreverPartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.RodadaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.PartidaJogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.JogadorRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaJogadorRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private JogadorRepository jogadorRepository;
    @Autowired
    private PartidaJogadorRepository partidaJogadorRepository;
    @Value("${game.segundos.rodada}")
    Integer segundosRodada;

    public PartidaDTO iniciaPartida(Jogador jogador){
        jogador=getJogador(jogador);
        Partida partida=new Partida();
        partida.setJogadores(1);
        partida.setSituacao(ESituacaoPartida.ANDAMENTO);
        partida.setHora(adicionaSegundos(new Date(),10));
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
        partida.setJogadores(partida.getJogadores()+1);
        if(partida.getJogadores()==2){
            partida.setSituacao(ESituacaoPartida.ANDAMENTO);
            partida.setHora(adicionaSegundos(new Date(),30));
        }
        PartidaJogador partidaJogador=new PartidaJogador(partida, jogador);
        partidaJogadorRepository.save(partidaJogador);
        return partida;
    }
    public List<PartidaDTO> findPartidasNaoEncerradas(){
        List<Partida> partidas=partidaRepository.findPartidasAndamento();
        List<PartidaDTO> dtos=new ArrayList<>();
        for (Partida partida : partidas) {
            dtos.add(createPartidaDTO(partida));
        }
        return dtos;
    }
    public PartidaDTO createPartidaDTO(Partida partida){
        PartidaDTO dto=new PartidaDTO();
        dto.setId(partida.getId());
        dto.setSituacao(partida.getSituacao());
        dto.setHora(partida.getHora());
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
    public RodadaDTO getRodadaDTO(Partida partida){
        RodadaDTO dto=new RodadaDTO();
        dto.setIdPartida(partida.getId());
        dto.setJogadores(partidaJogadorRepository.findByPartidaId(partida.getId()));
        dto.setRodada(partida.getRodada());
        dto.setCartas(getCards(Arrays.asList(5,15,-20,30,25)));
        dto.setSituacaoPartida(partida.getSituacao());
        dto.setHora(adicionaSegundos(partida.getHora(),segundosRodada*partida.getRodada()));
        return dto;
    }
    private List<CardDTO> getCards(List<Integer> numeros){
       List<CardDTO> cards=new ArrayList<>();
        Collections.shuffle(numeros);
          for (Integer numero : numeros) {
            CardDTO card=new CardDTO();
            card.setValue(String.valueOf(numero));
            cards.add(card);
          }
          return cards;
    }
    public Date adicionaMinutos(Date data, int minutos){
        return new Date(data.getTime() + minutos * 60000);
    }
    public Date adicionaSegundos(Date data, int segundos){
        return new Date(data.getTime() + segundos * 1000);
    }
}
