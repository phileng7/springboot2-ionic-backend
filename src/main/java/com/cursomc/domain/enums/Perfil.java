package com.cursomc.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		//varrer todos os tipos para encontrar o buscado
		for (Perfil tc : Perfil.values()) {
			if (id.equals(tc.getCod())) {
				return tc;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
