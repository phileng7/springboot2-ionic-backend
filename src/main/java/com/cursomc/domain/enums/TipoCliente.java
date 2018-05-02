package com.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		//varrer todos os tipos para encontrar o buscado
		for (TipoCliente tc : TipoCliente.values()) {
			if (id.equals(tc.getCod())) {
				return tc;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
