package com.confeccaosocorro.controleestoque.tipo;

import java.util.ArrayList;
import java.util.List;

public enum TipoMovimentoEnum {
	ENTRADA(1,"Entrada"),
	SAIDA(2,"Saída");

	
	private Integer id;
	private String descricao;
	
	TipoMovimentoEnum(int id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}

	
	TipoMovimentoEnum() {
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<TipoMovimentoEnum> getTiposMovimento(){
		List<TipoMovimentoEnum> tipos = new ArrayList<>();
		for (TipoMovimentoEnum tipo : values()) {
			tipos.add(tipo);
		}
		return tipos;
	}
}
