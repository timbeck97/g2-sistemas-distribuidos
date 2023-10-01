package com.faccat.sistemasdistribuidos.g2.jogodistribuido.schedule;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.PartidaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.dto.RodadaDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository.PartidaRepository;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameSchedule {

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;
    
    @Autowired
    private PartidaRepository partidaRepository;

    @Value("${game.segundos.rodada}")
    Integer segundosRodada;
    @Value("${game.quantidade.rodadas}")
    Integer quantidadeRodadas;
    @Autowired
    private GameService gameService;
    @Scheduled(fixedDelay = 1000)
    public void logicaGame(){

        List<Partida> partidasEmAberto=partidaRepository.findPartidasAndamento(ESituacaoPartida.ANDAMENTO);
        for (Partida partida : partidasEmAberto) {

            //System.out.println("RODADA: "+partida.getRodada());
            if(trocouRodada(partida)){
                if(partida.getRodada()==quantidadeRodadas){
                    partida.setSituacao(ESituacaoPartida.FINALIZADA);
                    partida=partidaRepository.save(partida);
                    RodadaDTO dto=gameService.getRodadaDTO(partida);
                    simpleMessagingTemplate.convertAndSend("/game/partida/"+partida.getId(),dto);
                    continue;
                }
                System.out.println("TROCOU RODADA");
                partida.setRodada(partida.getRodada()+1);
                partida=partidaRepository.save(partida);
                RodadaDTO dto=gameService.getRodadaDTO(partida);
                simpleMessagingTemplate.convertAndSend("/game/partida/"+partida.getId(),dto);
            }
        }
        //
    }
    @Scheduled(fixedDelay = 1000)
    public void verificaInicio(){

        List<Partida> partidasEmAberto=partidaRepository.findPartidasAndamento(ESituacaoPartida.AGUARDANDO);
        for (Partida partida : partidasEmAberto) {
            if(partida.getHora().getTime()<=new Date().getTime()){
                partida.setSituacao(ESituacaoPartida.ANDAMENTO);
                partidaRepository.save(partida);
            }

        }

    }

    private boolean trocouRodada(Partida p){
        Date horaProxRodada=gameService.adicionaSegundos(p.getHora(),p.getRodada()*segundosRodada);
        return horaProxRodada.getTime()<=new Date().getTime();

    }
}
