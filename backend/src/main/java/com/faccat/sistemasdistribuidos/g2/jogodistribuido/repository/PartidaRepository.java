package com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByEncerrada(boolean encerrada);
}
