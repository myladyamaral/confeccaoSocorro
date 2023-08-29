package com.confeccaosocorro.controleestoque.service;

import java.util.List;

import com.confeccaosocorro.controleestoque.model.Movimento;
/**
 * Service movimento para interações com banco de dados
 * 
 * @author Mylady
 **/
public interface MovimentoService {
	
	public List<Movimento> listarTodosMovimentos();
	
	/**
	 * 008 – pesquisar as entradas de um ou vários produtos
	 *por intervalo de datas
	 *009 - pesquisar as saídas de um ou vários produtos por
	 *intervalo de datas
	 * @param produto
	 * @param tipoMovimento
	 * @param dataEntrada
	 * @param dataSaida
	 * @return
	 */
	public List<Movimento> buscarMovimentosComFiltros(String produto, String tipoMovimento,
			String dataEntrada,String dataSaida);	

	public Movimento obterMovimentoPorId(Integer id);
	/**
	 * 006 – cadastrar a entrada de um produto no estoque
	 * 007 – cadastrar a saida de um produto no estoque
	 * @param movimento
	 * @return
	 */
	public Movimento salvarMovimento(Movimento movimento);
	/**012 – consulta que retorna um comparativo entre
	 * entradas e saídas em um dia, mês ou ano.
	 * @param filtro
	 * @param referencia
	 * @return
	 */
	public List<Movimento> gerarRelatorioMovimentos(String filtro, Integer referencia);


}
