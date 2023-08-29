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

import com.confeccaosocorro.controleestoque.model.Produto;
import com.confeccaosocorro.controleestoque.service.ProdutoService;

/**
 * Controller produto para interações entre back e front
 * 
 * @author Mylady
 **/

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/cadastrar")
	public ModelAndView exibirInclusaoProduto(Produto produto) {
		ModelAndView model = new ModelAndView("/produto/cadastrar");
		model.addObject("produto", produto);
		return model;
	}

	@PostMapping("/cadastrar")
	public ModelAndView salvarProduto(@ModelAttribute("produto") Produto produto) {
		produto.setDataCadastro(new Date());
		produtoService.salvarProduto(produto);
		return new ModelAndView("redirect:/produto/listar");
	}
	
	@GetMapping("/editar")
	public ModelAndView exibirAlteracaoProduto(Produto produto) {
		ModelAndView model = new ModelAndView("/produto/editar");
		model.addObject("produto", produto);
		return model;
	}
	
	/**003 - alterar os dados de um produto 
	 * @param id
	 * @return
	 */
	@GetMapping("/editar/{id}")
	public ModelAndView alterarProduto(@PathVariable Integer id) {

		Produto produto = produtoService.obterProdutoPorId(id);
		return exibirAlteracaoProduto(produto);
	}

	@GetMapping("/listar")
	public ModelAndView listarProdutos() {

		ModelAndView model = new ModelAndView("produto/listar");
		List<Produto> listaProdutos = produtoService.listarTodosProdutos();
		model.addObject("produtos", listaProdutos);

		return model;
	}

	@GetMapping("/buscar")
	public ModelAndView buscarProdutos(@RequestParam String filtroNome) {

		ModelAndView model = new ModelAndView("produto/listar");
		List<Produto> listaProdutos = produtoService.listarProdutosPorNome(filtroNome);
		model.addObject("produtos", listaProdutos);
		if (listaProdutos.isEmpty()) {
			model.addObject("mensagem", "Não há resultado para essa busca");
		}
		return model;
	}

	@GetMapping("/deletarProduto/{id}")
	public ModelAndView deletarProduto(@PathVariable Integer id) {
		produtoService.deletarProduto(id);
		return new ModelAndView("redirect:/produto/listar");
	}

	@GetMapping("/detalhar/{id}")
	public ModelAndView detalharProduto(@PathVariable Integer id) {

		Produto produto = produtoService.obterProdutoPorId(id);

		ModelAndView model = new ModelAndView("/produto/detalhar");
		model.addObject("produto", produto);

		return model;
	}

}