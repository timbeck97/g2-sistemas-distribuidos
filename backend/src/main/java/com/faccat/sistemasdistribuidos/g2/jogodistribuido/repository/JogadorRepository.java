package com.faccat.sistemasdistribuidos.g2.jogodistribuido.repository;

import com.faccat.sistemasdistribuidos.g2.jogodistribuido.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    Jogador findByNome(String nome);
}
