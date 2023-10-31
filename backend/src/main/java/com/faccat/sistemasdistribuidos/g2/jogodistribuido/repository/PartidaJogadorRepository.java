package com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.enums.ESituacaoPartida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.JogadorDTO;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.PartidaJogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaJogadorRepository extends JpaRepository<PartidaJogador, Long> {

    @Query("select new com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.JogadorDTO(pj) from PartidaJogador pj where pj.partida.id=:partidaId")
    List<JogadorDTO> findByPartidaId(@Param("partidaId") long partidaId);

    PartidaJogador findByPartidaIdAndJogadorNome(long partidaId, String jogadorNome);

    int countByPartidaId(long partidaId);

    @Query("select distinct(pj.partida) from PartidaJogador pj where (pj.partida.situacao='ANDAMENTO' and pj.jogador=:jogador) or pj.partida.situacao='AGUARDANDO'")
    List<Partida> findByJogadorAndSituacao(@Param("jogador") Jogador jogador);

    PartidaJogador findByPartidaAndJogador(Partida partida, Jogador jogador);
}
