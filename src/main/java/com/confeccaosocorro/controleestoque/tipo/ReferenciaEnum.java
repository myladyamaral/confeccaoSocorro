package com.confeccaosocorro.controleestoque.tipo;

import java.util.ArrayList;
import java.util.List;

public enum ReferenciaEnum {
	DIA(1,"DIA"),
	MES(2, "MES"),
	ANO(3, "ANO");
	
	private Integer id;
	private String descricao;
	
	ReferenciaEnum(int id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}
	
	ReferenciaEnum() {
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<ReferenciaEnum> getReferencias(){
		List<ReferenciaEnum> tipos = new ArrayList<>();
		for (ReferenciaEnum tipo : values()) {
			tipos.add(tipo);
		}
		return tipos;
	}
}
