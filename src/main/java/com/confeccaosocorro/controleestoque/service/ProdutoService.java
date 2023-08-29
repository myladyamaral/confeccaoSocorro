package com.confeccaosocorro.controleestoque.service;

import java.util.List;

import com.confeccaosocorro.controleestoque.model.Produto;
/**
 * Service produto para interações com banco de dados
 * 
 * @author Mylady
 **/
public interface ProdutoService {
	
	public List<Produto> listarTodosProdutos();
	
	
	/**
	 * 001 - cadastrar um produto
	 * @param produto
	 * @return Produto
	 */
	public Produto salvarProduto(Produto produto);
	
	/**
	 * 002 - recuperar um produto específico
	 * @param id
	 * @return Produto
	 */
	public Produto obterProdutoPorId(Integer id);
	
	/**
	 * 004 - excluir um produto específico
	 * @param id
	 */
	public void deletarProduto(Integer id);
	
	/**
	 * 005 - pesquisar todos os produtos ou pesquisar os
	 *produtos pelo nome, exibindo a quantidade e situação do estoque
	 * @param FiltroNome
	 * @return lista de produtos que atendem ao criterio
	 * de busca
	 */
	public List<Produto> listarProdutosPorNome(String FiltroNome);


}
