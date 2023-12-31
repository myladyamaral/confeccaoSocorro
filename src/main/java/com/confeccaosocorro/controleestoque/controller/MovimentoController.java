package com.confeccaosocorro.controleestoque.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.confeccaosocorro.controleestoque.model.Entrada;
import com.confeccaosocorro.controleestoque.model.Movimento;
import com.confeccaosocorro.controleestoque.model.Produto;
import com.confeccaosocorro.controleestoque.model.Saida;
import com.confeccaosocorro.controleestoque.service.MovimentoService;
import com.confeccaosocorro.controleestoque.service.ProdutoService;
import com.confeccaosocorro.controleestoque.tipo.ReferenciaEnum;
import com.confeccaosocorro.controleestoque.tipo.TipoMovimentoEnum;
/**
 * Controller movimento para interações entre
 * back e front
 * @author Mylady
 **/


@Controller
@RequestMapping("/movimento")
public class MovimentoController {
	
	@Autowired
	private  MovimentoService movimentoService;
	@Autowired
	private  ProdutoService produtoService;
	
	Integer tipoMovimento;
		
	/**
	 * 006 – cadastrar a entrada de um produto no estoque
	 * @param entrada
	 * @return
	 */
	@GetMapping("/entrada")
	public ModelAndView adicionarEntrada() {
		ModelAndView model = new ModelAndView("movimento/entrada");
		model.addObject("movimento", new Entrada());
		
		List<Produto> listaProdutos = produtoService.listarTodosProdutos();
		model.addObject("produtos", listaProdutos);

		tipoMovimento = TipoMovimentoEnum.ENTRADA.getId();
		return model;
	}
	/**
	 * 007 – cadastrar a saida de um produto no estoque
	 * @param saida
	 * @return
	 */
	@GetMapping("/saida")
	public ModelAndView adicionarSaida() {
		ModelAndView model = new ModelAndView("movimento/saida");
		model.addObject("movimento", new Saida());
		
		List<Produto> listaProdutos = produtoService.listarTodosProdutos();
		listaProdutos = listaProdutos.stream().filter(p->p.getTotalEstoque()>0).toList();
		model.addObject("produtos", listaProdutos);
		
		tipoMovimento = TipoMovimentoEnum.SAIDA.getId();
		return model;
	}
	@PostMapping("/cadastrarEntrada")
	public ModelAndView salvarEntrada(@ModelAttribute("movimento")Entrada movimento) {
		movimento.setDataMovimento(new Date());
		movimento.setEstorno(false);
		movimentoService.salvarEntrada(movimento);
		
		return listarMovimentos();
	}
	@PostMapping("/cadastrarSaida")
	public ModelAndView salvarMovimento(@ModelAttribute("movimento")Saida movimento) {
		if(tipoMovimento.equals(TipoMovimentoEnum.SAIDA.getId())&&
				movimento.getQuantidade()>movimento.getProduto().getTotalEstoque()) {
			 ModelAndView model = new ModelAndView("redirect:/movimento/saida");
			 model.addObject("mensagem", "Não é possível fazer uma saida de produto maior que o estoque");
			return model;
		}
		movimento.setDataMovimento(new Date());
		movimento.setEstorno(false);
		
		movimentoService.salvarSaida(movimento);
		
		return listarMovimentos();
	}
	
    @GetMapping("/listar")
	public ModelAndView listarMovimentos() {
    	ModelAndView model = new ModelAndView("movimento/listar");
    	
    	List<Movimento> listaMovimentos = movimentoService.listarTodosMovimentos();
   		model.addObject("movimentos", listaMovimentos);
   		
		List<TipoMovimentoEnum> tiposMovimento = TipoMovimentoEnum.getTiposMovimento();
		model.addObject("tiposMovimento", tiposMovimento);
		
		return model;
	}
    @GetMapping("/buscar")
   	public ModelAndView buscarMovimentos(@RequestParam String filtroProduto,@RequestParam String filtroTipo, 
   			@RequestParam String filtroEntrada,@RequestParam String filtroSaida ) {
    	
    	ModelAndView model = new ModelAndView("movimento/listar");
   		
    	List<Movimento> listaMovimentos = movimentoService.buscarMovimentosComFiltros(filtroProduto,filtroTipo, filtroEntrada, filtroSaida);
   		model.addObject("movimentos", listaMovimentos);
   		List<TipoMovimentoEnum> tiposMovimento = TipoMovimentoEnum.getTiposMovimento();
		model.addObject("tiposMovimento", tiposMovimento);
		
		if(listaMovimentos.isEmpty()) {
			model.addObject("mensagem","Não há resultado para essa busca");
		}
   		return model;
   	}
    
    @GetMapping("/relatorio")
	public ModelAndView mostrarRelatorio() {
    	ModelAndView model = new ModelAndView("movimento/relatorio");
    	List<ReferenciaEnum> referencias = ReferenciaEnum.getReferencias();
		model.addObject("referencias",referencias);

		return model;
	}
    /**
     * 012 – consulta que retorna um comparativo entre
	 * entradas e saídas em um dia, mês ou ano.
     * @param filtroReferencia
     * @param filtro
     * @return
     */
    @GetMapping("/gerarRelatorio")
   	public ModelAndView gerarRelatorio(@RequestParam Integer filtroReferencia,@RequestParam String filtro) {
    	ModelAndView model = new ModelAndView("movimento/relatorio");
    	List<ReferenciaEnum> referencias = ReferenciaEnum.getReferencias();
		model.addObject("referencias",referencias);
		
    	List<Movimento> listaMovimentos = movimentoService.gerarRelatorioMovimentos(filtro, filtroReferencia);
   		model.addObject("movimentos", listaMovimentos);
   		Integer entradas = listaMovimentos.stream()
				.filter(m->m.getTipoMovimento().equals(TipoMovimentoEnum.ENTRADA.getId()))
				.mapToInt(Movimento::getQuantidade)
				.sum();
   		Integer saidas = listaMovimentos.stream()
   				.filter(m->m.getTipoMovimento().equals(TipoMovimentoEnum.SAIDA.getId()))
   				.mapToInt(Movimento::getQuantidade)
   				.sum();
   		Integer total = entradas-saidas;
   		model.addObject("entradas", entradas);
   		model.addObject("saidas", saidas);	
   		model.addObject("total", total);
   		model.addObject("cabecalho",ReferenciaEnum.getDescricaoPorId(filtroReferencia)+
   				" de referencia: "+filtro);

   		return model;
   	}
    /**
     * 010 – estornar a entrada de um produto
     * 011 – estornar a saída de um produto
     * @param id
     * @return
     */
    @GetMapping("/estornar/{id}")
   	public ModelAndView estornarMovimento(@PathVariable Integer id) {
   		
   		Movimento movimento = movimentoService.obterMovimentoPorId(id);		
		
		
   		if(movimento.getTipoMovimento().equals(TipoMovimentoEnum.ENTRADA.getId())) {
   			Saida estorno = new Saida();
   			estorno.setProduto(movimento.getProduto());
   			estorno.setQuantidade(movimento.getQuantidade());
   			estorno.setIdMovimentoEstornado(movimento.getId());
   			estorno.setDataMovimento(new Date());
   			estorno.setEstorno(false);

   			movimentoService.salvarSaida(estorno);
   		}
   		else {
   			Entrada estorno = new Entrada();
   			estorno.setProduto(movimento.getProduto());
   			estorno.setQuantidade(movimento.getQuantidade());
   			estorno.setIdMovimentoEstornado(movimento.getId());
   			estorno.setDataMovimento(new Date());
   			estorno.setEstorno(false);
   			
   			movimentoService.salvarEntrada(estorno);
   		}
   		
   		movimento.setEstorno(true);
   		movimentoService.salvarMovimento(movimento);
   		
   		return listarMovimentos();
   	}
   
}