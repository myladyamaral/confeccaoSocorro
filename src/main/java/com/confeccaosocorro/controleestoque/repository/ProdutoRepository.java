package com.confeccaosocorro.controleestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confeccaosocorro.controleestoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
