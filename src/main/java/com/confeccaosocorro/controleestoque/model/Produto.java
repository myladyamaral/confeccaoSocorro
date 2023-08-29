package com.confeccaosocorro.controleestoque.model;

import java.util.Date;
import java.util.List;

import com.confeccaosocorro.controleestoque.tipo.EstadoEstoqueEnum;
import com.confeccaosocorro.controleestoque.tipo.TipoMovimentoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(schema = "controleestoque", name = "produto")
public class Produto {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O descricao do produto não pode estar em branco")
	private String descricao;
	
	
	@NotNull(message = "O valor do produto não pode estar em branco")
	private Double valor;
	
	@Column(name = "min_estoque")
	@NotNull(message = "O minimo de produtos em estoque não pode estar em branco e deve ser maior que 0")
	private Integer estoqueMinimo;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@OneToMany(mappedBy = "produto",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Movimento> movimentos;
	
	@Transient
	private Integer entradas;
	
	@Transient
	private Integer saidas;
	
	@Transient
	private Integer totalEstoque;
	
	@Transient
	private String estadoEstoque;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	public Integer getEntradas() {
		if(movimentos.isEmpty()) {
			return 0;
		}
		entradas =  movimentos.stream()
				.filter(m->m.getTipoMovimento().equals(TipoMovimentoEnum.ENTRADA.getId()))
				.mapToInt(Movimento::getQuantidade)
				.sum();
		return entradas;
	}

	public void setEntradas(Integer entradas) {
		this.entradas = entradas;
	}

	public Integer getSaidas() {
		if(movimentos.isEmpty()) {
			return 0;
		}
		saidas = movimentos.stream().filter(m->m.getTipoMovimento().equals(TipoMovimentoEnum.SAIDA.getId()))
				.mapToInt(Movimento::getQuantidade)
				.sum();;
		return saidas;
	}

	public void setSaidas(Integer saidas) {
		this.saidas = saidas;
	}

	public Integer getTotalEstoque() {
		totalEstoque = getEntradas()-getSaidas();
		return totalEstoque;
	}

	public void setTotalEstoque(Integer totalEstoque) {
		this.totalEstoque = totalEstoque;
	}

	public String getEstadoEstoque() {
		if(getTotalEstoque() == 0) {
			estadoEstoque = EstadoEstoqueEnum.SEM_ESTOQUE.getDescricao();
		}
		else if(getTotalEstoque()<=getEstoqueMinimo()) {
			estadoEstoque = EstadoEstoqueEnum.ESTOQUE_PERIGOSO.getDescricao();
		}
		else {
			estadoEstoque = EstadoEstoqueEnum.ESTOQUE_CONFORTAVEL.getDescricao();
		}
		return estadoEstoque;
	}

	public void setEstadoEstoque(String estadoEstoque) {
		this.estadoEstoque = estadoEstoque;
	}
}
