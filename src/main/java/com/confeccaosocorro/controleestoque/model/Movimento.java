
package com.confeccaosocorro.controleestoque.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(schema = "controleestoque", name = "movimento")
public class Movimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tipo_movimento")
	private Integer tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	private Integer quantidade;
	private Boolean estorno;
	
	
	@Column(name = "id_movimento_estornado")
	private Integer idMovimentoEstornado;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "data_movimento")
	private Date dataMovimento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(Integer tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Boolean getEstorno() {
		return estorno;
	}

	public void setEstorno(Boolean estorno) {
		this.estorno = estorno;
	}

	public Integer getIdMovimentoEstornado() {
		return idMovimentoEstornado;
	}

	public void setIdMovimentoEstornado(Integer idMovimentoEstornado) {
		this.idMovimentoEstornado = idMovimentoEstornado;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
}
