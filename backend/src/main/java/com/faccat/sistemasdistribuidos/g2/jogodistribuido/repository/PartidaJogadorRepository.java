package com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.PartidaJogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaJogadorRepository extends JpaRepository<PartidaJogador, Long> {

    @Query("select pj.jogador from PartidaJogador pj where pj.partida.id=:partidaId")
    List<Jogador> findByPartidaId(@Param("partidaId") long partidaId);
}
