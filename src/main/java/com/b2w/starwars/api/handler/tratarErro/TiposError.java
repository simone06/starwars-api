package com.b2w.starwars.api.handler.tratarErro;

import lombok.Getter;

@Getter
public enum TiposError {

	DADOS_INVALIDOS("Dados inválidos"),	
	ERRO_DE_SISTEMA("Erro de sistema"),
	PARAMETRO_INVALIDO("Parâmetro inválido"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado");	
	
	private String title;
	
	TiposError(String title) {
		this.title = title;
	}
	
}
