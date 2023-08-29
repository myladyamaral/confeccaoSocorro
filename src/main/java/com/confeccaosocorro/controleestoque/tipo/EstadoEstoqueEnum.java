package com.confeccaosocorro.controleestoque.tipo;

import java.util.ArrayList;
import java.util.List;

public enum EstadoEstoqueEnum {
	ESTOQUE_PERIGOSO(1,"Estoque perigoso"),
	ESTOQUE_CONFORTAVEL(2, "Estoque confortável"),
	SEM_ESTOQUE(3, "Sem estoque");
	
	private Integer id;
	private String descricao;
	
	EstadoEstoqueEnum(int id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}
	
	EstadoEstoqueEnum() {
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<EstadoEstoqueEnum> getEstadoEstoque(){
		List<EstadoEstoqueEnum> tipos = new ArrayList<>();
		for (EstadoEstoqueEnum tipo : values()) {
			tipos.add(tipo);
		}
		return tipos;
	}
}
