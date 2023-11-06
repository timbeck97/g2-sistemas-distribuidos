package com.faccat.sistemasdistribuidos.g2.jogodistribuido.service;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.*;
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
import java.util.stream.Collectors;

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
    @Value("${game.segundos.inicio.partida}")
    Integer segundosInicioPartida;
    @Value("${game.quantidade.maxima.jogadores}")
    Integer quantidadeMinimaJogadores;

    @Value("${game.quantidade.rodadas}")
    Integer quantidadeRodadas;

    public PartidaDTO iniciaPartida(){
        System.out.println("CRIANDO PARTIDA");
        Partida partida=new Partida();
        partida.setJogadores(0);
        partida.setSituacao(ESituacaoPartida.AGUARDANDO);
        //partida.setHora(adicionaSegundos(new Date(),segundosInicioPuartida));
        partida.setCartasRodadaAtual(createNumbersCards(Arrays.asList(5,15,-20,30,25)));
        partida=partidaRepository.save(partida);
        return createPartidaDTO(partida);

    }
    public Partida entrarPartida(InscreverPartidaDTO subscriber){
        Jogador jogador=new Jogador();
        jogador.setNome(subscriber.getNomeParticipante());
        jogador=getJogador(jogador);
        Partida partida=partidaRepository.findById(subscriber.getIdPartida()).get();
        partida.setJogadores(partida.getJogadores()+1);
        if(partida.getJogadores()==quantidadeMinimaJogadores){
            partida.setSituacao(ESituacaoPartida.ANDAMENTO);
            partida.setHora(adicionaSegundos(new Date(),3));
        }
        partida=partidaRepository.save(partida);
        PartidaJogador partidaJogador=new PartidaJogador(partida, jogador);
        partidaJogadorRepository.save(partidaJogador);
        return partida;
    }
    public Partida sairPartida(Long idPartida, Long idUser){
        Jogador jogador=jogadorRepository.findById(idUser).get();
        Partida partida=partidaRepository.findById(idPartida).get();
        partida.setJogadores(partida.getJogadores()-1);
        partida=partidaRepository.save(partida);
        PartidaJogador partidaJogador=partidaJogadorRepository.findByPartidaAndJogador(partida,jogador);
        partidaJogadorRepository.delete(partidaJogador);
        return partida;
    }
    public List<PartidaDTO> findPartidasBySituacao(String userName){
        Jogador jogador=new Jogador();
        jogador.setNome(userName);
        jogador=getJogador(jogador);
        List<Partida> partidas=partidaJogadorRepository.findByJogadorAndSituacao(jogador);
        List<PartidaDTO> dtos=new ArrayList<>();
        for (Partida partida : partidas) {
            dtos.add(createPartidaDTO(partida));
        }
        return dtos;
    }
    public PartidaDTO findPartidaAberta(String userName){
        Jogador jogador=new Jogador();
        jogador.setNome(userName);
        jogador=getJogador(jogador);
        Partida partida=partidaJogadorRepository.findByJogadorAberta(jogador);
        if(partida==null)return new PartidaDTO();
        return createPartidaDTO(partida);
    }
    public PartidaDTO createPartidaDTO(Partida partida){
        PartidaDTO dto=new PartidaDTO();
        dto.setId(partida.getId());
        dto.setSituacaoPartida(partida.getSituacao());
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
        dto.setCartas(getCards(partida));
        dto.setSituacaoPartida(partida.getSituacao());
        //dto.setHora(adicionaSegundos(partida.getHora(),segundosRodada*partida.getRodada()));
        return dto;
    }
    public List<CardDTO> getCards(Partida p){
        List<CardDTO> cards=new ArrayList<>();
        String[] cartas=p.getCartasRodadaAtual().split(",");
        for (String carta : cartas) {
            CardDTO card=new CardDTO();
            card.setValue(carta);
            cards.add(card);
        }
        return cards;
    }
    private String createNumbersCards(List<Integer> numeros){
        Collections.shuffle(numeros);
        return numeros.stream().map(Object::toString).collect(Collectors.joining(","));

    }
    public void salvarCartaEscolhida(CartaEscolhidaDTO card){
        System.out.println("SALVANDO CARTA: "+card.getValue());
        PartidaJogador partidaJogador=partidaJogadorRepository.findByPartidaIdAndJogadorNome(card.getIdPartida(),card.getJogador());
        partidaJogador.setPontos(partidaJogador.getPontos()+Integer.parseInt(card.getValue()));
        partidaJogadorRepository.save(partidaJogador);
        Partida p=partidaJogador.getPartida();
        p.setQuantidadeJogadas(p.getQuantidadeJogadas()+1);

        int qttJogadas=partidaJogadorRepository.countByPartidaId(p.getId());
        if(p.getQuantidadeJogadas()==qttJogadas){
            if(p.getRodada()==quantidadeRodadas){
                p.setSituacao(ESituacaoPartida.FINALIZADA);
            }else{
                p.setRodada(p.getRodada()+1);
                p.setQuantidadeJogadas(0);
                p.setCartasRodadaAtual(createNumbersCards(Arrays.asList(5,15,-20,30,25)));
            }

        }

        partidaRepository.save(p);

    }
    public Date adicionaMinutos(Date data, int minutos){
        return new Date(data.getTime() + minutos * 60000);
    }
    public Date adicionaSegundos(Date data, int segundos){
        return new Date(data.getTime() + segundos * 1000);
    }
}
