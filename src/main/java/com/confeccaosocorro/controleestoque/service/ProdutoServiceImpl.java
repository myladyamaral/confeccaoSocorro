package com.confeccaosocorro.controleestoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.confeccaosocorro.controleestoque.model.Produto;
import com.confeccaosocorro.controleestoque.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class ProdutoServiceImpl implements ProdutoService{
	
	@Autowired
    private  ProdutoRepository produtoRepository;
	@PersistenceContext
	private EntityManager entityManager;

    
    @Override
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }
    @Override
    public Produto obterProdutoPorId(Integer id) {
        return produtoRepository.getReferenceById(id);
    }
    @Override
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.saveAndFlush(produto);
    }
    @Override
    public void deletarProduto(Integer id) {
        produtoRepository.deleteById(id);
    }
	@Override
	public List<Produto> listarProdutosPorNome(String filtroNome) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();		
		CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
		Root<Produto> root = cq.from(Produto.class);
		Predicate nomePredicate = cb.conjunction();;
		
		if (!filtroNome.isEmpty()) {
			nomePredicate = cb.like(root.get("descricao"), "%"+filtroNome+"%");
		}
		
		cq.where(nomePredicate);
		cq.select(root)
	      .orderBy(cb.desc(root.get("id")));

		TypedQuery<Produto> query = entityManager.createQuery(cq);

		return query.getResultList();
	}

    
    
}

