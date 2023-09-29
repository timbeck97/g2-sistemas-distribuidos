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
    @Autowired
    private GameService gameService;
    @Scheduled(fixedDelay = 1000)
    public void teste(){
        List<Partida> partidasEmAberto=partidaRepository.findPartidasAndamento();
        for (Partida partida : partidasEmAberto) {

            //System.out.println("RODADA: "+partida.getRodada());
            if(trocouRodada(partida)){
                if(partida.getRodada()==5){
                    partida.setSituacao(ESituacaoPartida.FINALIZADA);
                    partidaRepository.save(partida);
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

    private boolean trocouRodada(Partida p){
        Date horaProxRodada=gameService.adicionaSegundos(p.getHora(),p.getRodada()*segundosRodada);
        return horaProxRodada.getTime()<=new Date().getTime();

    }
}
