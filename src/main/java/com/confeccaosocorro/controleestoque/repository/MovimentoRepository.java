package com.confeccaosocorro.controleestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confeccaosocorro.controleestoque.model.Movimento;

public interface MovimentoRepository extends JpaRepository<Movimento, Integer> {

}
